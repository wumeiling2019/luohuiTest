package com.wml.utils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件类的工具
 */
public class FileUtils {

    private static final String ENCODING = "UTF-8";//编码方式

    /**
     * 获取文件的行（获取json中的内容）
     *
     * @param filePath 文件路径
     * @return String  文件里的内容
     */
    public static String getContentByLine(String filePath) {
        StringBuffer lines = new StringBuffer();
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            String configPath = getFileTruePath(filePath);
            configPath = configPath.replaceAll("%20", " ");// 处理文件路径中空格问题
            File file = new File(configPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                read = new InputStreamReader(new FileInputStream(file), ENCODING);
                bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt == null || lineTxt.length() == 0) {
                        continue;
                    }
                    lines.append(lineTxt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(read,bufferedReader);
        }
        return lines.toString();
    }

    /**
     *  获取文件的地址
     * @param filePath  文件路径
     * @return  String 文件路径
     */
    public static final String getFileTruePath(String filePath) {
        URL url = FileUtils.class.getClassLoader().getResource(filePath);
        if(null == url) {
            throw new RuntimeException("地址输入错误啦傻逼");
        }
      //  System.out.println("真实地址：" + url.getPath());
        return url.getPath();
    }

    /**
     * 读取每一行的文本返回List<Map<String, String>>（获取键值对的list）
     * @param filePath  文件路径
     * @return  List<Map<String, String>>  键值对的list
     */
    public static final List<Map<String, String>> getListTxtContent(String filePath) {
        List<String> contents = new ArrayList<String>();
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            String configPath = getFileTruePath(filePath);
            configPath = configPath.replaceAll("%20", " ");// 处理文件路径中空格问题
            File file = new File(configPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                read = new InputStreamReader(new FileInputStream(file), ENCODING);
                bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt == null || lineTxt.length() == 0) {
                        continue;
                    }
                    contents.add(lineTxt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(read,bufferedReader);
        }
        List<Map<String ,String >> result = new ArrayList<Map<String, String>>();
        for (String strContent : contents){
            Map<String,String> mapStr = new HashMap<String, String>();
            String[] strList = strContent.split("=");
            String str = strContent.substring(strList[0].length()+1,strContent.length());
            mapStr.put(strList[0],str);
            result.add(mapStr);
        }

        return result;
    }


    /**
     * 根据文件的路径和 文件里对应里的key获取对应的值
     * @param filePath 文件路径
     * @param key  key
     * @return 获取key对应的值
     */
    public static final String getListTxtContent(String filePath, String key) {
        List<Map<String, String>> contents = getListTxtContent(filePath);
        String result = "";
        if (!contents.isEmpty()){
            for(Map<String, String> content : contents){
                if(content.get(key) != null){
                    result = content.get(key);
                }
            }
        }
        return result;
    }


    /**
     * 关闭资源
     * @param read
     * @param bufferedReader
     */
    private static void closeResources(InputStreamReader read,BufferedReader bufferedReader) {
        try {
            if (read != null) {
                read.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
