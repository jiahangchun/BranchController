package com.jiahangchun;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.test.json.JsonbTester;

import java.io.*;
import java.util.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/26 下午3:34
 **/
public class TimeOut {

    private static String fileName = "/Users/hzmk/Desktop/timeout";

    private static String resultFileName = "/Users/hzmk/Desktop/result";

    public static HashMap<String, Integer> countMap = new HashMap<>();

    public static void main(String[] args) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = "";
            String str1 = "";
            File file = new File(fileName);
            for (String aa : file.list()) {
                fis = new FileInputStream(fileName+"/"+aa);// FileInputStream
                // 从文件系统中的某个文件中获取字节
                isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
                br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
                while ((str = br.readLine()) != null) {
                    try {

                        int ecIndex = str.indexOf("ec");
                        int wdzgIndex = str.indexOf("wdzg");
                        int index = ecIndex;
                        if (index == -1) {
                            index = wdzgIndex;
                        }

                        if(index == -1 || index >= str.length()){
                            continue;
                        }

                        String a = str.substring(index);
                        Integer count = countMap.get(a);
                        if (null == count) {
                            countMap.put(a, 1);
                        } else {
                            count++;
                            countMap.put(a, count);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("error msg= " + str);
                    }
                }

                List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(countMap.entrySet());
                Collections.sort(entryList, new MapValueComparator());
                //写文件
                File result = new File(resultFileName);
                if (result.exists()) {
                    result.delete();
                }
                result.createNewFile();
                FileWriter fw = new FileWriter(result);
                //写入中文字符时会出现乱码
                BufferedWriter bw = new BufferedWriter(fw);
                for (Map.Entry<String, Integer> entry : entryList) {
                    StringBuilder key = new StringBuilder().append(entry.getKey());
                    if (key.length() < 50) {
                        int l = 50 - key.length();
                        for (int i = 0; i < l; i++) {
                            key.append(" ");
                        }
                    }
                    bw.write("路径= " + key.toString() + "; 频率=" + entry.getValue() + "\t\n");
                }
                bw.close();
                fw.close();

            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
