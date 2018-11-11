package pers.li.custom.packages.utils;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * create by lishengbo on 2018-04-23 10:17
 *DOM4J方式解析XML：
 */
@SuppressWarnings("all")
public class analysisDOM4J {
    static int count=0;
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        parserDOM4J();
//        createXMLDOM4J();
    }


    private static void createXMLDOM4J() {
        //创建document对象，代表整个xml文档
        Document document = DocumentHelper.createDocument();
        //创建根节点
        Element rss = document.addElement("rss");
        //添加根节点属性
        rss.addAttribute("version","2.0");
        //生成子节点及节点内容
        Element channel = rss.addElement("channel");
        Element title = channel.addElement("title");
        title.setText("<国内最新新闻 />");
        //设置生成xml格式
        OutputFormat format=OutputFormat.createPrettyPrint();
        format.setEncoding("GBK");
        //创建File
        File file = new File("src/xml/createDOM4J.xml");
        //生成文件
        XMLWriter writer= null;
        try {
            writer = new XMLWriter(new FileOutputStream(file),format);
            //处理转义字符，默认为true，<>等特殊字符默认转义
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    private static void parserDOM4J() {
        SAXReader reader = new SAXReader();
        try {
            String absolutePath = new File("").getAbsolutePath();
            System.out.println(absolutePath);
            Document document = reader.read(new File("spring-ioc-custom\\src\\main\\resources\\spring-context.xml"));
            //获取根节点bookstore
            Element rootElement = document.getRootElement();
            //获取迭代器
            Iterator it = rootElement.elementIterator();
            while(it.hasNext()){
                count++;
                System.out.println("==================遍历第几本书："+count);
                //遍历book节点
                Element book = (Element) it.next();
                QName qName = book.getQName();
                System.out.println(qName.getName());
                //获取book属性名属性值
                List<Attribute> attributes = book.attributes();
                for (Attribute attr:attributes
                     ) {
                    System.out.println("属性名/属性值："+attr.getName()+"/"+attr.getValue());
                }
                Iterator iterator = book.elementIterator();
                while (iterator.hasNext()){
                    Element child = (Element) iterator.next();
                    System.out.println("节点名/节点值："+child.getName()+"/"+child.getStringValue());

                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public static String getPackage(String name) {
        SAXReader reader = new SAXReader();
        try {
            String absolutePath = new File("").getAbsolutePath();
            System.out.println(absolutePath);
            Document document = reader.read(new File(name));
            //获取根节点bookstore
            Element rootElement = document.getRootElement();
            //获取迭代器
            Iterator it = rootElement.elementIterator();
            while(it.hasNext()){
                count++;
                System.out.println("==================遍历第几本书："+count);
                //遍历book节点
                Element book = (Element) it.next();
                QName qName = book.getQName();
                System.out.println(qName.getName());
                //获取book属性名属性值
                List<Attribute> attributes = book.attributes();
                for (Attribute attr:attributes
                        ) {
                    System.out.println("属性名/属性值："+attr.getName()+"/"+attr.getValue());
                    return attr.getValue();
                }
                Iterator iterator = book.elementIterator();
                while (iterator.hasNext()){
                    Element child = (Element) iterator.next();
                    System.out.println("节点名/节点值："+child.getName()+"/"+child.getStringValue());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
