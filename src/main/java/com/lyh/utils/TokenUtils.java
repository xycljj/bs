package com.lyh.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lyh
 * @ClassName TokenUtils
 * @createTime 2021/12/28 08:46:00
 *
 * 使用token验证用户是否登录
 */
@Slf4j
public class TokenUtils {
    //设置过期时间
    private static final long EXPIRE_DATE=30*60*1000*2;
    //token秘钥
    private static final String TOKEN_SECRET = "hzddzh";

    public static String token (Long userId){

        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("userId",userId).withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return token;
    }

    public static boolean verify(String token){
        /**
         * @desc   验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (TokenExpiredException e){
            log.info(e.getMessage());
            return false;
        }
    }
    
    /**
     * @Author lyh
     * @Description //TODO 获取token中的登录者信息
     * @Param 
     * @return 
     * @Date 2021/12/29
     **/
    public static Long getIdFromToken(String token){
        try{
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        }catch (JWTDecodeException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        String token = token(1l);
        System.out.println(token);
        boolean b = verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsImV4cCI6MTY0MDY2MTI1MCwidXNlcm5hbWUiOiJoemRkIn0.zOru6-JX_cc0SmSKwS5fZUZko1a2WcCKyfriw_TxcGI");
        System.out.println(b);
    }
}

