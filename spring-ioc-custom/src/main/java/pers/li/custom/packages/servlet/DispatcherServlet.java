package pers.li.custom.packages.servlet;

import pers.li.custom.packages.annotation.Repository;
import pers.li.custom.packages.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:luofeng
 * @createTime : 2018/11/12 10:52
 */
public class DispatcherServlet extends HttpServlet {

    /**
     * 保存controller对象实例的集合，类名称首字母小写
     */
    Map<String,Object> controllerMaps=new ConcurrentHashMap<>(64);

    public DispatcherServlet() {
        System.err.println("DispatcherServlet-->instance");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String readpath = config.getInitParameter("contextConfigLocation");
        String path=resolverPath(readpath);
        System.out.println(path);
        //获取资源文件夹下所有文件名
        List<String> lsit=getResources(path);
        initMvc(path);

    }

    /**
     * 解析该路径下的所有文件
     * @param path
     * @return
     */
    private List<String> getResources(String path) {
        String[] paths=path.split("/");

        URL resource = this.getClass().getClassLoader().getResource(path);
       /* File file = new File(resource.getFile());
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File childFile) {
                if(childFile.isDirectory()){
                    getScanAnnotationClass(aPackage+"."+childFile.getName());
                }else{
                    //获取文件名称
                    String name = childFile.getName();
                    //判断class文件
                    if(name.endsWith(".class")){
                        //构建一个类路径
                        String classPath=aPackage+"."+name.replace(".class","");
                        //创建路径对应的class对象
                        try {
                            Class<?> aClass = this.getClass().getClassLoader().loadClass(classPath);
                            //判断存储
                            if(aClass.isAnnotationPresent(Repository.class)
                                    || aClass.isAnnotationPresent(Service.class)){
                                classCache.add(aClass);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
                return false;
            }
        });*/
        return null;
    }

    /**
     * 核心方法：实例化所有被管理的controller
     * 找到controller
     * @param path
     */
    private void initMvc(String path) {
        //扫描所有@controller类并实例化
        instancesControllers(path);
        //找到请求路径与方法映射处理
        initHandlermappings();
    }

    private void instancesControllers(String path) {

    }

    private void initHandlermappings() {

    }

    /**
     * classpath 替换
     * @param readpath
     * @return
     */
    private String resolverPath(String readpath) {
        if(readpath.startsWith("classpath:")){
            return readpath.replace("classpath:","");
        }
        return readpath;
    }
}
