package com.lr.common.utils;


import java.io.BufferedReader;
import java.io.IOException;
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

    public static String sendGet(String url, String param) {
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
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
        return result.toString();
    }


    public static String sendPost(String url, String param) {
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
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
        return result.toString();
    }
}
