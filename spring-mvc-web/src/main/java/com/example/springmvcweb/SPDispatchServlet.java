package com.example.springmvcweb;

import com.example.springmvcweb.Annotation.*;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import sun.invoke.empty.Empty;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description SPDispatchServlet
 * @Author stopping
 * @date: 2021/3/14 14:45
 */

public class SPDispatchServlet extends HttpServlet {
    Logger log = LoggerFactory.getLogger(SPDispatchServlet.class);
    /**
     * 配置文件信息
     */
    private Properties properties = new Properties();
    /**
     * 保存类名
     */
    private List<String> classPackage = new ArrayList<>();
    /**
     * IOC容器
     */
    private ConcurrentMap<String, Object> ioc = new ConcurrentHashMap<>();
    /**
     * URL-Method 映射器
     */
    private ConcurrentMap<String, Method> handlerMapping = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Detail : " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * 分发器
     * 将请求分发到对应的方法上
     * request->url->HandlerMapping->method->execute
     */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
        resp.setContentType("text/html;charset=utf-8");
        String requestUrl = req.getRequestURI();
        String contextPath = req.getContextPath();
        requestUrl.replaceAll(contextPath, "").replaceAll("/+", "/");
        if (!this.handlerMapping.containsKey(requestUrl)) {
            resp.getWriter().write("404,The Request Not Found");
            return;
        }
        //匹配HandlerMapping
        Method method = handlerMapping.get(requestUrl);
        String beanName = toLowFirstCase(method.getDeclaringClass().getSimpleName());
        //参数封装
        Map<String, String[]> requestParam = req.getParameterMap();
        //方法形参
        //Class<?> [] parameterTypes = method.getParameterTypes();
        Parameter[] parameters = method.getParameters();
        //存储参数
        Object[] paramValues = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class parameterType = parameter.getType();
            if (parameterType == HttpServletRequest.class) {
                paramValues[i] = req;
            } else if (parameterType == HttpServletResponse.class) {
                paramValues[i] = resp;
            } else if (parameterType == String.class) {
                SPRequestParam spRequestParam = parameter.getAnnotation(SPRequestParam.class);
                if (requestParam.containsKey(spRequestParam.value())) {
                    paramValues[i] = requestParam.get(spRequestParam.value())[0];
                }
            }
        }
        method.invoke(ioc.get(beanName), paramValues);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、读取配置文件信息
        //doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //2、扫描类信息
        doScanner("com.example.springmvcweb");
        //3、IOC
        doInstance();
        //4、DI
        doAutowired();
        //5、MVC HandlerMapping
        doHandlerMapping();
    }

    /**
     * MVC
     * URL与Method对应绑定
     */
    private void doHandlerMapping() {
        log.debug("handler mapping start ...");
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(SPController.class)) {
                continue;
            }
            //保存写在类上面的@SPRequestMapping("/demo")
            String baseUrl = "";
            //类-> 根路径
            if (clazz.isAnnotationPresent(SPRequestMapping.class)) {
                SPRequestMapping requestMapping = clazz.getAnnotation(SPRequestMapping.class);
                baseUrl = requestMapping.value();
            }
            //类中的方法 -> 子路径
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(SPRequestMapping.class)) {
                    continue;
                }
                SPRequestMapping requestMapping = method.getAnnotation(SPRequestMapping.class);
                String requestUrl = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                handlerMapping.put(requestUrl, method);
                log.debug("Mapped : {},{}", requestUrl, method);
            }
        }
    }

    /**
     * 依赖注入
     * 扫描IOC容器中Autowired注解的属性，将其注入Method
     */
    private void doAutowired() {
        if (ioc == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //Declared 所有的，特定的 字段，包括private/protected/default
            //正常来说，普通的OOP编程只能拿到public的属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(SPAutowired.class)) {
                    continue;
                }
                SPAutowired autowired = field.getAnnotation(SPAutowired.class);
                String beanName = autowired.value().trim();
                if ("".equals(beanName)) {
                    //类名
                    beanName = toLowFirstCase(field.getType().getSimpleName());
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    log.debug("field setAccessible fail");
                }
            }
        }
    }

    /**
     * IOC 控制反转
     * 将类实例化为Bean保存在IOC容器中，其中KEY为类名小写
     */
    private void doInstance() {
        if (classPackage == null) {
            return;
        }
        //反射加载扫描过的类
        try {
            for (String className : classPackage) {
                Class<?> clazz = Class.forName(className);
                //controller注解的类
                if (clazz.isAnnotationPresent(SPController.class)) {
                    String beanName = toLowFirstCase(clazz.getSimpleName());
                    log.debug("controller instance className:{}", beanName);
                    ioc.put(beanName, clazz.newInstance());
                }
                //service 注解的类
                else if (clazz.isAnnotationPresent(SPService.class)) {
                    String beanName = toLowFirstCase(clazz.getSimpleName());
                    SPService service = clazz.getAnnotation(SPService.class);
                    if (!"".equals(service.value())) {
                        beanName = service.value();
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    for (Class<?> i : clazz.getInterfaces()) {
                        if (ioc.containsKey(toLowFirstCase(i.getName()))) {
                            throw new Exception("The Bean [" + i.getName() + "] is exist");
                        }
                        log.debug("service interface add ioc : {}", i.getName());
                        ioc.put(i.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("doInstance classLoad file:{}", e.getMessage());
        }
    }

    /**
     * 首字母小写
     */
    private String toLowFirstCase(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 扫描器
     * 将配置文件中需要扫描的package class文件保存在集合中
     */
    private void doScanner(String scanPackage) {
        //com.example.springmvcweb->com/example/springmvcweb
        URL url = this.getClass().getClassLoader().getResource(
                "/" + scanPackage.replaceAll("\\.", "/")
        );
        //根目录
        File classPath = new File(url.getFile());
        //子目录
        for (File file : classPath.listFiles()) {
            //目录->递归访问
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                //文件判断是否.class文件
                if (file.getName().endsWith(".class")) {
                    String className = scanPackage + "." + file.getName().replace(".class", "");
                    log.debug("file add classPackage:{}", className);
                    classPackage.add(className);
                }
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        log.debug("doLoadConfig,{}", contextConfigLocation);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
