package com.lr.common.constant;

/**
 * 百度翻译API
 *
 * 文档地址：https://api.fanyi.baidu.com/doc/21
 *
 * @author shijie.xu
 * @since 2020年02月29日
 */
public class BaiDuTranslateConstant {
    /**
     * APP ID
     */
    public static String APPID = "20200228000390140";

    /**
     * 密钥
     */
    public static String KEY = "WagnPENfg9QBRCNz1may";

    /**
     * 随机数
     */
    public static String SALT = "1435660288";

    /**
     * HTTP地址
     */
    public static String HTTP_URL = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    /**
     * HTTPS地址
     */
    public static String HTTPS_URL = "https://fanyi-api.baidu.com/api/trans/vip/translate";

    public static String ERROR_CODE = "-1";
    /**
     * eg:
     *
     * 成功 {"from":"zh","to":"en","trans_result":[{"src":"Java\u5b57\u8282\u7801\u52a8\u6001\u8ffd\u8e2a\u6280\u672f",
     * "dst":"Java bytecode dynamic tracing technology"}]}
     *
     * 失败 {"error_code":"52003","error_msg":"UNAUTHORIZED USER"}
     *
     * @param result 接口的返回值
     * @return
     */
    public static String getRes(String result){
        String head = "\"dst\":\"";
        String end = "\"}]}";
        int headI = result.lastIndexOf(head);
        int endI = result.lastIndexOf(end);
        if(headI >=0 && endI >= 0){
           return result.substring(headI+head.length(),endI);
        }else {
            return ERROR_CODE;
        }

    }

}
