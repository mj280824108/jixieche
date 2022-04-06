package com.weiwei.jixieche.jwt;

/**
 * @ClassName JwtUtil
 * @Description TODO
 * @Author houji
 * @Date 2019/5/22 16:39
 * @Version 1.0.1
 **/

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {


	public static String ISSUER = "weiweijixie";
	public static long TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

	/**
	 * 由字符串生成加密key
	 *
	 * @return
	 */
	public SecretKey generalKey() {
		String stringKey = JwtConstant.JWT_SECRET;

		// 本地的密码解码
		byte[] encodedKey = Base64.decodeBase64(stringKey);

		// 根据给定的字节数组使用AES加密算法构造一个密钥
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

		return key;
	}

	/**
	 * 创建jwt
	 *
	 * @param id        JWT的唯一标识
	 * @param issuer    jwt的签发时间
	 * @param subject   JWT的主体，即它的所有人
	 * @param ttlMillis 过期时间
	 * @return
	 * @throws Exception
	 */
	public String createJWT(String id, String issuer, String subject, long ttlMillis) throws Exception {

		// 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// 生成JWT的时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
		Map<String, Object> claims = new HashMap<>();
		claims.put("uid", "weiweijixie");
		claims.put("user_name", "weiweijixie");
		claims.put("nick_name", "jixieche");

		// 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
		// 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
		SecretKey key = generalKey();

		// 下面就是在为payload添加各种标准声明和私有声明了
		JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
				.setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
				.setId(id)                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setIssuedAt(now)           // iat: jwt的签发时间
				.setIssuer(issuer)          // issuer：jwt签发人
				.setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
				.signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥

		// 设置过期时间
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	/**
	 * 解密jwt
	 *
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public Claims parseJWT(String jwt) {
			SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
			Claims claims = Jwts.parser()  //得到DefaultJwtParser
					.setSigningKey(key)                 //设置签名的秘钥
					.parseClaimsJws(jwt).getBody();     //设置需要解析的jwt
			return claims;
	}

	/**
	 * 获取token中用户信息
	 *
	 * @param jwt
	 * @return
	 */
	public User getSubject(String jwt) {
		String subject = parseJWT(jwt).getSubject();
		System.out.println(subject);
		User user = new Gson().fromJson(subject, User.class);
		//System.out.println(user.getUserId());
		return user;
	}

	public String getSubujectStr(String subject) {
		//String subject = parseJWT(jwt).getSubject();
		//User user = new Gson().fromJson(subject,User.class);
		//System.out.println(user.getUserId());
		/*String jsonObject = new JSONObject(subject);*/
		//return jsonObject;
		return "";
	}


	public static void main(String[] args) {

		User user = new User();
		user.setUserId(1);
		user.setRealName("张三");
		String subject = new Gson().toJson(user);
		try {
			JwtUtil util = new JwtUtil();
			//String jwt = util.createJWT(JwtConstant.JWT_ID, JwtConstant.ISSUER, subject, JwtConstant.JWT_TTL);
			String jwt="eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJ3ZWl3ZWlqaXhpZSIsInN1YiI6IntcInVzZXJJZFwiOjk5LFwicGhvbmVcIjpcIjE4Njk5OTkxMTExXCIsXCJyb2xlSWRcIjo0LFwidGhpcmRJZFwiOlwiODY4NjE0YmQ4MWNkZWQwNmYxOWI0MTkyODZkMzc3MmFcIn0iLCJ1c2VyX25hbWUiOiJ3ZWl3ZWlqaXhpZSIsIm5pY2tfbmFtZSI6ImppeGllY2hlIiwiaXNzIjoid3VoYW5nb25neW91emhpamlhXzIwMTkiLCJleHAiOjE1Njk2NDA2OTEsImlhdCI6MTU2ODE2OTQ2MiwianRpIjoiNzQzZjRhYTFjZWQ4Njc2ZTViN2NlIn0.C7cdyO63T5byjLcvmDkVsKXpQWzXtcSzT7Pl6j5m_Fo";
			//System.out.println("JWT：" + jwt);
			util.getSubject(jwt);

		} catch (Exception e) {
			System.out.println("jwt出错了");
			e.printStackTrace();
		}

	}
}
