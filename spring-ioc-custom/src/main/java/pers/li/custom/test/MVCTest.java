package pers.li.custom.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author:luofeng
 * @createTime : 2018/11/12 11:30
 */
public class MVCTest {

    public static void main(String[] args) {
        File file=new File("spring-ioc-custom/src/mian/resources");
        System.out.println("文件名称："+file.getName());
        System.out.println("获取相对路径的父路径：错误"+file.getParent());
        System.out.println("获取绝对路径："+file.getAbsolutePath());
        System.out.println("获取绝对路径："+file.getAbsoluteFile());
        System.out.println("获取上一级路径："+file.getAbsoluteFile().getParent());
        analyzeFiles(file);
        test2(new File(file.getAbsolutePath()));
    }
//    F:\github-work\spring\spring-ioc-custom\src\main\resources
//
// F:\github-work\spring\spring-ioc-custom\src\mian\resources
    private static List<String> analyzeFiles(File file) {
        List<String>  list=new ArrayList<>();
        file.listFiles(new FileFilter(){
            @Override
            public boolean accept(File childFile) {
                if(childFile.isDirectory()){
                    analyzeFiles(childFile);
                }else{
                    list.add(childFile.getName());
                    System.out.println(childFile.getName());
                }
                return false;
            }
        });

        return list;
    }

    public static void test2(File file){
        Collection<File> listFiles = FileUtils.listFiles(file, FileFilterUtils.suffixFileFilter("xml"), null);
        showFiles(listFiles);
    }

    private static void showFiles(Collection<File> listFiles) {
        if (listFiles==null) {
            return;
        }
        for (File file : listFiles) {
            System.out.println(file.getName());
        }
    }
}
