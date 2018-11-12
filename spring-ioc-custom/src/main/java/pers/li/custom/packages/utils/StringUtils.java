package pers.li.custom.packages.utils;

/**
 * @author:luofeng
 * @createTime : 2018/11/12 11:19
 */
public class StringUtils {

    public static String toLowerCaseFirstOne(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }
}
