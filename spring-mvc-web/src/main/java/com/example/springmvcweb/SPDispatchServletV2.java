package com.example.springmvcweb;

import com.example.springmvcweb.Annotation.*;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description SPDispatchServlet V2
 * 该版本新增Handler的处理，把方法和url的映射在编译阶段处理
 * @Author stopping
 * @date: 2021/3/14 14:45
 */

public class SPDispatchServletV2 extends HttpServlet {
    Logger log = LoggerFactory.getLogger(SPDispatchServletV2.class);
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
    private List<Handler> handlerMapping = new ArrayList<Handler>();

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
        //当前请求匹配关系
        Handler handler = getHandler(req);
        if (handler == null) {
            resp.getWriter().write("404 NOT FOUND");
            return;
        }
        //匹配参数
        Class<?>[] paramTypes = handler.getParamTypes();

        Object[] paramValues = new Object[paramTypes.length];

        Map<String, String[]> params = req.getParameterMap();
        for (Map.Entry<String, String[]> parm : params.entrySet()) {
            String value = Arrays.toString(parm.getValue()).replaceAll("\\[|\\]", "")
                    .replaceAll("\\s", ",");
            if (!handler.paramIndexMapping.containsKey(parm.getKey())) {
                continue;
            }
            int index = handler.paramIndexMapping.get(parm.getKey());
            paramValues[index] = value;
        }
        //方法反射
        if (handler.paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = handler.paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = req;
        }

        if (handler.paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = handler.paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = resp;
        }

        Object returnValue = handler.method.invoke(handler.controller, paramValues);
        if (returnValue == null || returnValue instanceof Void) {
            return;
        }
        resp.getWriter().write(returnValue.toString());
    }

    private Handler getHandler(HttpServletRequest req) {
        if (handlerMapping.isEmpty()) {
            return null;
        }
        String url = req.getRequestURI();
        String contextUrl = req.getContextPath();
        String requestUrl = url.replaceAll(contextUrl, "")
                .replaceAll("/+", "/");
        for (Handler handler : this.handlerMapping) {
            Matcher m = handler.getPattern().matcher(requestUrl);
            if (!m.matches()) {
                continue;
            }
            return handler;
        }
        return null;
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
                Pattern pattern = Pattern.compile(requestUrl);
                this.handlerMapping.add(new Handler(pattern, entry.getValue(), method));
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

    //保存一个url和一个Method的关系
    public class Handler {
        //必须把url放到HandlerMapping才好理解吧
        private Pattern pattern;  //正则
        private Method method;
        private Object controller;
        //参数类型
        private Class<?>[] paramTypes;
        //形参列表 参数的名字作为key,参数的顺序，位置作为值
        private Map<String, Integer> paramIndexMapping;

        public Pattern getPattern() {
            return pattern;
        }

        public Method getMethod() {
            return method;
        }

        public Object getController() {
            return controller;
        }

        public Class<?>[] getParamTypes() {
            return paramTypes;
        }

        public Handler(Pattern pattern, Object controller, Method method) {
            this.pattern = pattern;
            this.method = method;
            this.controller = controller;

            paramTypes = method.getParameterTypes();

            paramIndexMapping = new HashMap<String, Integer>();
            putParamIndexMapping(method);
        }

        private void putParamIndexMapping(Method method) {

            //提取方法中加了注解的参数
            //把方法上的注解拿到，得到的是一个二维数组
            //因为一个参数可以有多个注解，而一个方法又有多个参数
            /*Annotation[][] pa = method.getParameterAnnotations();
            for (int i = 0; i < pa.length; i++) {
                for (Annotation a : pa[i]) {
                    if (a instanceof SPRequestParam) {
                        String paramName = ((SPRequestParam) a).value();
                        if (!"".equals(paramName.trim())) {
                            paramIndexMapping.put(paramName, i);
                        }
                    }
                }
            }

            //提取方法中的request和response参数
            Class<?>[] paramsTypes = method.getParameterTypes();
            for (int i = 0; i < paramsTypes.length; i++) {
                Class<?> type = paramsTypes[i];
                if (type == HttpServletRequest.class ||
                        type == HttpServletResponse.class) {
                    paramIndexMapping.put(type.getName(), i);
                }
            }*/
            Parameter[] paras = method.getParameters();
            for (int i = 0; i < paras.length; i++) {
                Parameter parameter = paras[i];
                if (parameter.isAnnotationPresent(SPRequestParam.class)) {
                    SPRequestParam requestParam = parameter.getAnnotation(SPRequestParam.class);
                    paramIndexMapping.put(requestParam.value(), i);
                    continue;
                }
                if (parameter.getType() == HttpServletRequest.class ||
                        parameter.getType() == HttpServletResponse.class) {
                    paramIndexMapping.put(parameter.getType().getName(), i);
                    continue;
                }
            }
        }
    }
}
