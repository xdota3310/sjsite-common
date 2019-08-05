package com.lr.common.utils;

import com.lr.common.constant.JwtConstant;
import com.lr.common.model.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * jwt工具类
 *
 * @author shijie.xu
 * @since 2019年02月15日
 */
public class JwtUtil {
    public static String generateToken(UserToken userToken) throws Exception {
        String token = Jwts.builder().setSubject(userToken.getUsername()).claim(JwtConstant.CONTEXT_USER_ID, userToken.getUserId()).claim(JwtConstant.RENEWAL_TIME, new Date(System.currentTimeMillis() + userToken.getExpireMinite() / 2)).setExpiration(new Date(System.currentTimeMillis() + userToken.getExpireMinite())).signWith(SignatureAlgorithm.HS256, JwtConstant.JWT_PRIVATE_KEY).compact();
        return token;
    }


    public static UserToken getInfoFromToken(String token) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(JwtConstant.JWT_PRIVATE_KEY).parseClaimsJws(token).getBody();
        UserToken adminUserToken = new UserToken();
        adminUserToken.setUsername(claims.getSubject());
        adminUserToken.setUserId(claims.get(JwtConstant.CONTEXT_USER_ID).toString());
        return adminUserToken;
    }
}
