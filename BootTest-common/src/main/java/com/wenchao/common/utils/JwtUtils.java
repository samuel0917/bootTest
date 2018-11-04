package com.wenchao.common.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wenchao.common.bean.CheckResult;



public class JwtUtils {
	  
    private static final String EXP = "exp";  
  
    private static final String JWT_INFO = "info";  
	
	 /**
	 * token
	 */
	public static final int RESCODE_REFTOKEN_MSG = 1006;		//刷新TOKEN(有返回数据)
	public static final int RESCODE_REFTOKEN = 1007;			//刷新TOKEN
	
	public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
	public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
	public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过

	/**
	 * JWT
	 */
	public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			//密匙
	public static final long JWT_TTL_HOUR = 60*60*1000;	//token有效时间
	
	public static final long JWT_TTL_MONTH = 30*24*60*60*1000;		
	
	
	
	
	
	public static String createJWT(String id, String subject, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setId(id)                                      // JWT_ID
//                .setAudience("")                                // 接受者
//                .setClaims(null)                                // 自定义属性
                .setSubject(subject)                                 // 主题
                .setIssuer("货运中国网")                                  // 签发者
                .setIssuedAt(now)                        // 签发时间
//                .setNotBefore(new Date())                       // 失效时间
//                .setExpiration(long)                                // 过期时间
                .signWith(signatureAlgorithm, secretKey);           // 签名算法以及密匙
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			builder.setExpiration(expDate); // 过期时间
		}
		return builder.compact();
	}
	/**
	 * 验证JWT
	 * @param jwtStr
	 * @return
	 */
	public static CheckResult validateJWT(String jwtStr) {
		CheckResult checkResult = new CheckResult();
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setErrCode(JWT_ERRCODE_EXPIRE);
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setErrCode(JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setErrCode(JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		}
		return checkResult;
	}
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(JWT_SECERT);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}
	
	/**
	 * 
	 * 解析JWT字符串
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwt)
			.getBody();
	}
	
	public static<T> T unsign(String jwt, Class<T> classT){
		try {
			Claims claims=parseJWT(jwt);
			if(claims!=null){
				String jsonStr=(String) claims.get(JWT_INFO);
				System.out.println("unsign jsonStr..."+jsonStr);
				return JSONObject.parseObject(jsonStr, classT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static <T> String sign(T object, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		final Map<String, Object> claims = new HashMap<String, Object>(); 
		claims.put(JWT_INFO, JSON.toJSONString(object));  
        claims.put(EXP, System.currentTimeMillis() + ttlMillis);  
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
                .setClaims(claims)                                // 自定义属性
                .setIssuer("货运中国网")                                  // 签发者
                .setIssuedAt(now)                        // 签发时间
//                .setNotBefore(new Date())                       // 失效时间
//                .setExpiration(long)                                // 过期时间
                .signWith(signatureAlgorithm, secretKey);           // 签名算法以及密匙
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			builder.setExpiration(expDate); // 过期时间
		}
		return builder.compact();
	}
	
	
	public static void main(String[] args) throws Exception {
		//小明失效 10s
//		String sc = createJWT("1","小明", JWT_TTL_MONTH);
//		System.out.println(sc);
//		System.out.println(validateJWT(sc).getErrCode());
//		System.out.println(validateJWT(sc).getClaims().getId());
//		Thread.sleep(3000);
//		CheckResult result=validateJWT(sc);
//		System.out.println(JSON.toJSONString(result));
		
		String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoidXNlciIsImlzcyI6Iui0p-i_kOS4reWbvee9kSIsImlhdCI6MTUzNzUxNjYwNH0.aPTq2K9cfIrS5K0851o7BBK9BwdiuFVXvCQiePNjnxk";
		System.out.println(JSON.toJSONString(parseJWT(token)));
//		RedisLogin loginInfo=unsign(token, RedisLogin.class);
//		System.out.println(JSON.toJSONString(loginInfo));
		
	}
}
