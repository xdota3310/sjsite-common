package com.lr.common.utils;


import com.lr.common.constant.BaiDuTranslateConstant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * 用于发送POST或者GET请求
 *
 * @author shijie.xu
 * @since 2019年03月05日
 */
public class HttpUtil {

    public static String sendGet(String url, String param) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;

        try {
            String urlString = url + "?" + param;
            URL realUrl = new URL(urlString);
            URLConnection urlConnection = realUrl.openConnection();

            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if(in != null) {
                in.close();
            }
        }
        return result.toString();
    }


    public static String sendPost(String url, String param) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            URL realUrl = new URL(url);
            URLConnection urlConnection = realUrl.openConnection();

            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            out = new PrintWriter(urlConnection.getOutputStream());
            out.print(param);
            out.flush();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if(out != null) {
                out.close();
            }
            if(in != null) {
                in.close();
            }
        }
        return result.toString();
    }

    private static String demo() {
        String q = "Java字节码动态追踪技术";
        String appid = "20200228000390140";
//        String appid = "2015063000000001";
        String salt = "65461354";
        String key = "WagnPENfg9QBRCNz1may";
        String sign = MD5Util.md5(appid + q + salt + key);

        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";
        String par = "q=" + q + "&from=zh&to=en&appid=" + appid + "&salt=" + salt + "&sign=" + sign;

//        return sendGet(url, par);
        return "";
    }

    public static void main(String[] args) {
        String res = demo();
        System.out.println(res);
        System.out.println(BaiDuTranslateConstant.getRes(res));

    }
}
