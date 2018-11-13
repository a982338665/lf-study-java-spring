package pers.li.custom.packages.servlet;

import pers.li.custom.packages.annotation.Controller;
import pers.li.custom.packages.annotation.RequestMapping;
import pers.li.custom.packages.mapping.HandlerMapping;
import pers.li.custom.packages.utils.StringUtils;
import pers.li.custom.packages.utils.analysisDOM4J;
import pers.li.custom.web.TestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author:luofeng
 * @createTime : 2018/11/12 10:52
 */
@SuppressWarnings("all")
public class DispatcherServlet extends HttpServlet {

    /**
     * 保存controller对象实例的集合，类名称首字母小写
     */
    Map<String, Object> controllerMaps = new ConcurrentHashMap<>(64);

    /**
     * 定义集合存储所有方法，路径，和controller实例集合
     */
    List<HandlerMapping> handlerMappings = new CopyOnWriteArrayList<>();

    public DispatcherServlet() {
        System.err.println("DispatcherServlet-->instance");
    }

    /**
     * 仅初始化一次
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String readpath = config.getInitParameter("contextConfigLocation");
        String path = resolverPath(readpath);
        //暂时指定不使用通配符
        path = "spring-context.xml";
        initMvc(path);
        for (HandlerMapping handlerMapping:handlerMappings
             ) {
            System.out.println(handlerMapping.toString());
        }
    }

    /**
     * 解析该路径下的所有文件
     * @param path
     * @return
     */
//    private List<String> getResources(String path) {
//        String[] paths=path.split("/");
//        String spring1="spring-context.xml";
//        String spring2="spring-mvc.xml";
//
//        return null;
//    }

    /**
     * 核心方法：实例化所有被管理的controller
     * 找到controller
     *
     * @param path
     */
    private void initMvc(String path) {
        //扫描所有@controller类并实例化
        instancesControllers(path);
        //找到请求路径与方法映射处理
        initHandlermappings();
    }

    private void instancesControllers(String path) {
        //获取包路径
        URL resource = this.getClass().getClassLoader().getResource(path);
        File file = new File(resource.getFile());
        System.out.println(file.getAbsolutePath());
        String aPackage = analysisDOM4J.getPackage(file.getAbsolutePath());
        getScanAnnotationClass(aPackage);
    }

    private void initHandlermappings() {
        if(controllerMaps.size()>0){
            for (Map.Entry<String,Object> entry:controllerMaps.entrySet()
                    ) {
                //获取controller实例
                Object instance = entry.getValue();
                //获取实例所在类对象
                Class<?> c1 = instance.getClass();
                //解析类
                String baseUri="";
                if(c1.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping annotation = c1.getAnnotation(RequestMapping.class);
                    //拿到根路径
                    baseUri =dealUri (annotation.value());
                }
                //获取类中所有定义的方法
                Method[] declaredMethods = c1.getDeclaredMethods();
                for (Method method:declaredMethods){
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                        //拿到方法标注路径
                        String methodUri =  dealUri(annotation.value()) ;
                        //组合pattren
                        String pattern=baseUri+methodUri;
                        HandlerMapping handlerMapping = new HandlerMapping();
                        handlerMapping.setController(instance);
                        handlerMapping.setMethod(method);
                        handlerMapping.setPattern(pattern);
                        handlerMappings.add(handlerMapping);
                    }
                }
            }
        }
    }

    private String dealUri(String baseUri) {
        if(!baseUri.startsWith("/")){
            baseUri="/"+baseUri;
            return baseUri;
        }
        return baseUri;
    }

    /**
     * classpath 替换
     *
     * @param readpath
     * @return
     */
    private String resolverPath(String readpath) {
        if (readpath.startsWith("classpath:")) {
            return readpath.replace("classpath:", "");
        }
        return readpath;
    }

    /**
     * 找到controller注解，将他的类名首字母小写
     * 将实例化对象存储在map集合
     * @param aPackage
     */
    private void getScanAnnotationClass(String aPackage) {
        //获取class文件路径
//        URL resource1 = this.getClass().getClassLoader().getResource("pers/li");
//        System.err.println(resource1.getPath());
        String sss = aPackage.replace(".", "/");
//        System.err.println(sss+"-------->");
        URL resource = this.getClass().getClassLoader().getResource(sss);
        File file = new File(resource.getFile());
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File childFile) {
                if (childFile.isDirectory()) {
                    getScanAnnotationClass(aPackage + "." + childFile.getName());
                } else {
                    //获取文件名称
                    String name = childFile.getName();
                    //判断class文件
                    if (name.endsWith(".class")) {
                        //构建一个类路径
                        String classPath = aPackage + "." + name.replace(".class", "");
                        //创建路径对应的class对象
                        try {
                            Class<?> aClass = this.getClass().getClassLoader().loadClass(classPath);
                            //判断存储
                            if (aClass.isAnnotationPresent(Controller.class)
                                    ) {
                                //别名默认为类名的首字母小写
                                String simpleName = aClass.getSimpleName();
                                String ali= StringUtils.toLowerCaseFirstOne(simpleName);
                                controllerMaps.put(ali,aClass.newInstance());
//                              classCache.add(aClass);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }
        });

    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String context = req.getContextPath();
        String path = url.replace(context, "");
        Method method = (Method) controllerMaps.get(path);
        for (HandlerMapping hand:handlerMappings
             ) {
            String pattern = hand.getPattern();
            if(pattern.equals(path)){
                Method method1 = hand.getMethod();
                TestController controller = (TestController) hand.getController();
                try {
                    method.invoke(controller, new Object[] { req, resp, null });
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
//        HandlerMapping controller = (HandlerMapping) handlerMappings.get(path.split("/")[1]);
    }
}