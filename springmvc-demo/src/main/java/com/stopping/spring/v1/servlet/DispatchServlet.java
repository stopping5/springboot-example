package com.stopping.spring.v1.servlet;

import com.stopping.spring.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description DispatchServlet 请求转发分发器
 * @Author stopping
 * @date: 2021/3/13 21:54
 */
@Slf4j
@Controller
public class DispatchServlet extends HttpServlet {
    /**
     * 配置文件
     */
    private Properties properties = new Properties();
    /**
     * 保存扫描路径类名
     */
    private List<String> classNames = new ArrayList<>();
    /**
     * IOC 容器
     */
    private ConcurrentMap<String, Object> ioc = new ConcurrentHashMap<>();
    /**
     * HandlerMapping URL with Method relation
     */
    private ConcurrentMap<String, Method> handlerMapping = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6、运行阶段、调用dispatchServlet
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 exection,detail:" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * running time :
     */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //绝对路径
        String requestUrl = req.getRequestURI();
        //上下文路径
        String contextPath = req.getContextPath();
        requestUrl.replaceAll(contextPath, "").replaceAll("/+", "/");
        //请求在handleMapping中有映射方法
        if (!handlerMapping.containsKey(requestUrl)) {
            resp.getWriter().write("404 Not Found");
        }
        Method method = this.handlerMapping.get(requestUrl);
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        //处理方法反射参数问题
        //方法参数类型集合
        Class<?>[] paramTypes = method.getParameterTypes();
        //参数值集合
        Object[] paramsValue = new Object[paramTypes.length];
        //将request的参数保存到集合中
        Map<String, String[]> params = req.getParameterMap();
        for (int i = 0; i < paramTypes.length; i++) {
            Class paramType = paramTypes[i];
            if (paramType == HttpServletRequest.class) {
                paramsValue[i] = req;
                continue;
            } else if (paramType == HttpServletResponse.class) {
                paramsValue[i] = resp;
                continue;
            } else if (paramType == String.class) {
                if (!paramType.isAnnotationPresent(SPRequestParam.class)) {
                    continue;
                }
                //如果是RequestParam注解的参数
                SPRequestParam requestParam = (SPRequestParam) paramType.getAnnotation(SPRequestParam.class);
                if (params.containsKey(requestParam.value())) {
                    for (Map.Entry<String, String[]> entry : params.entrySet()) {
                        String value = Arrays.toString(entry.getValue())
                                .replaceAll("\\[|\\]", "").replaceAll("\\s", ",");
                        paramsValue[i] = value;
                    }
                }
            }
        }
        method.invoke(ioc.get(beanName), paramsValue);
        log.info("doDispatch execute over...");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、加载配置文件
        doLoadConfig("application.properties");
        //2、扫描相关的类
        doScanner(properties.getProperty("scannerPackage"));
        //3、初始化扫描到的类并放入IOC容器中
        doInstance();
        //4、依赖注入
        doAutoWired();
        //5、初始化HandlerMapping，url和method绑定
        initHandlerMapping();
        System.out.println("Stopping Spring framework init success!");
    }

    /**
     * 绑定URL和Method一对一关系
     */
    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getClass();
            if (!clazz.isAnnotationPresent(SPController.class)) {
                continue;
            }
            String baseUrl = "";
            //类是否有RequestMapping注解
            if (clazz.isAnnotationPresent(SPRequestMapping.class)) {
                SPRequestMapping requestMapping = clazz.getAnnotation(SPRequestMapping.class);
                baseUrl = requestMapping.value();
            }
            //类可以没有RequestMapping注解，但是方法依然可以加requestMapping注解
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(SPRequestMapping.class)) {
                    continue;
                }
                //method 被 rqm 注解
                String methodUrl = method.getAnnotation(SPRequestMapping.class).value();
                String url = ("/" + baseUrl + "/" + methodUrl).replaceAll("/+", "/");
                log.info("handler Mapping :{}-{}", url, methodUrl);
                //保存到容器中
                handlerMapping.put(methodUrl, method);
            }
        }
    }

    /**
     * 依赖注入
     */
    private void doAutoWired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //获取IOC容器中的bean的字段是否有需要注入的
            for (Field f : entry.getValue().getClass().getDeclaredFields()) {
                //是否有Autowired注解
                if (!f.isAnnotationPresent(SPAutowired.class)) {
                    continue;
                }
                SPAutowired autowired = f.getAnnotation(SPAutowired.class);
                //获取注解类名、通过类名作为KEY获取IOC容器的object实现依赖注入
                String beanName = autowired.value().trim();
                if ("".equals(beanName)) {
                    beanName = toLowerFirstCase(f.getType().getName());
                }
                //暴力访问
                f.setAccessible(true);
                //通过反射机制为属性赋值
                try {
                    f.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    log.error("依赖注入失败...");
                }
            }
        }
    }

    /**
     * 将扫描的文件装入IOC容器
     */
    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : classNames) {
                //获取类
                Class<?> clazz = Class.forName(className);
                //判断类是否需要进行装入IOC容器，目前只针对Controller,Service
                if (clazz.isAnnotationPresent(SPController.class)) {
                    Object instance = clazz.newInstance();
                    //加入IOC容器Spring默认为类名首字母小写
                    log.info("controller name :{}", toLowerFirstCase(clazz.getName()));
                    ioc.put(toLowerFirstCase(clazz.getName()), instance);
                } else if (clazz.isAnnotationPresent(SPService.class)) {
                    //service 如果自定义名称优先作为IOC容器的key
                    SPService service = this.getClass().getAnnotation(SPService.class);
                    String beanName = service.value();
                    if ("".equals(beanName.trim())) {
                        beanName = toLowerFirstCase(clazz.getName());
                    }
                    Object instance = clazz.newInstance();
                    log.info("service name :{}", beanName);
                    ioc.put(beanName, instance);
                    //接口根据类型自动赋值
                    for (Class<?> i : clazz.getInterfaces()) {
                        if (ioc.containsKey(toLowerFirstCase(i.getName()))) {
                            throw new Exception("The Bean \"" + i.getName() + "\"is exist");
                        }
                        log.info("interface name :{}", i.getName());
                        ioc.put(i.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("doInstance error");
        }
    }

    private String toLowerFirstCase(String name) {
        char[] className = name.toCharArray();
        className[0] += 32;
        return String.valueOf(className);
    }

    /**
     * 扫描配置文件下的相关的类
     */
    private void doScanner(String scanPackage) {
        log.info("----------------------doScanner start---------------------");
        //scanPackage = com.stopping.spring -> com/stopping/spring/..
        URL url = this.getClass().getClassLoader().
                getResource("/" + scanPackage.replaceAll(".", "/"));
        //读取目录下的文件
        log.info("url path:{}", url.getFile());
        File f = new File(url.getFile());
        for (File file : f.listFiles()) {
            if (f.isDirectory()) {
                doScanner(scanPackage + "." + f.getName());
            } else {
                if (!f.getName().endsWith(".class")) {
                    continue;
                } else {
                    //获取类路径名 eg:com/stopping/spring/Application
                    String className = scanPackage + "." + f.getName().replace(".class", "");
                    log.info("doscaner classPath:{}", className);
                    classNames.add(className);
                }
            }
        }
        log.info("----------------------doScanner over---------------------");
    }

    /**
     * 通过路径读取上下文配置信息
     */
    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("读取配置文件异常");
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("close inputStream error");
                }
            }
        }
    }
}
