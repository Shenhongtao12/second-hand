package com.sht.deal.utils;

import com.sht.deal.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Jwt工具类
 */
public class JwtUtils {

    private static final String SUBJECT = "shtDeal";

    private static final long EXPIRE = 1000*60*60*24*5;  //过期时间24小时

    //秘钥
    private static final String APPSECRET = "sht666";

    /**
     * 生成token的方法
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user) {
        String token = Jwts.builder()
                .setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("img", user.getImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET)
                .compact();

        return token;
    }

    public static Claims checkJWT(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){ }
        return null;
    }
}
