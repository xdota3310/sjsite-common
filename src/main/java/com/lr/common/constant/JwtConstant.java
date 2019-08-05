package com.lr.common.constant;

/**
 * jwt常量
 *
 * @author shijie.xu
 * @since 2019年02月15日
 */
public class JwtConstant {
    public final static String CONTEXT_TOKEN = "Authorization";
    public final static String CONTEXT_USERNAME = "contextUsername";
    public final static String CONTEXT_USER_ID = "contextUserId";
    public final static String JWT_PRIVATE_KEY = "0123456789";
    public final static String RENEWAL_TIME = "renewalTime";
    public final static String REDIS_USER_INFO_PREFIX = "userInfo_";
    public final static String TOKEN = "sjsite_token";
    public final static int EXPIRE = 2 * 60 * 60 * 1000;
}
