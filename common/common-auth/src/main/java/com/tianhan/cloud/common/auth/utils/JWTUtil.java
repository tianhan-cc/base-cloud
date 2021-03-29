package com.tianhan.cloud.common.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tianhan.cloud.common.core.SystemConstant;
import com.tianhan.cloud.common.core.exceptions.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author NieAnTai
 * @Date 2021/3/27 11:42 下午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description Jwt 工具类
 **/
public class JWTUtil {
    private static final String SECRET = "~!3.K&3!#3I^{o3p}%[$<?)(P]d{.d^3h!RF@~Mfhed4h2v?ke(";

    private static final String ISSUER = "com.tianhan.cloud";

    static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);


    /**
     * 生成 JWT 凭证
     *
     * @param subject  username
     * @param platform 登录来源
     * @return JWT
     */
    public static String getAccessToken(String subject, String platform) {
        return getAccessToken(subject, platform, new HashMap<>());
    }

    /**
     * 生成 JWT 凭证
     *
     * @param subject  username
     * @param platform 登录来源
     * @param params   携带参数
     * @return JWT
     */
    public static String getAccessToken(String subject, String platform, Map<String, String> params) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withSubject(subject)
                    .withClaim("UUID", UUID.randomUUID().toString().toUpperCase())
                    .withClaim("PLATFORM", platform);
            params.keySet().forEach(k -> builder.withClaim(k, params.get(k)));
            return builder.sign(algorithm);
        } catch (Exception e) {
            logger.debug("获取token失败", e);
            throw JwtException.buildCreateError();
        }
    }

    public static DecodedJWT decodedJwt(String accessToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            return verifier.verify(accessToken);
        } catch (Exception e) {
            logger.debug("access_token不合法", e);
            throw JwtException.buildValidateError();
        }
    }

    public static JWTUtil.Infos decodedConvertInfos(String accessToken) {
        DecodedJWT decoded = decodedJwt(accessToken);
        String platform = decoded.getClaim(SystemConstant.TOKEN_PLATFORM).asString();
        String userKey = platform.equals(SystemConstant.LOGIN_SOURCE) ? SystemConstant.USER_KEY : SystemConstant.APP_USER_KEY;
        String tokenKey = platform.equals(SystemConstant.LOGIN_SOURCE) ? SystemConstant.APP_TOKEN_KEY : SystemConstant.APP_TOKEN_KEY;
        return new JWTUtil.Infos(decoded.getSubject(), platform, userKey, tokenKey, decoded.getClaims());
    }

    public static class Infos {
        private final String username;
        private final String platform;
        private final String userKey;
        private final String tokenKey;
        private final Map<String, Claim> claim;

        public Infos(String username, String platform, String userKey, String tokenKey, Map<String, Claim> claim) {
            this.username = username;
            this.platform = platform;
            this.userKey = userKey;
            this.tokenKey = tokenKey;
            this.claim = claim;
        }

        public String getUsername() {
            return username;
        }

        public String getPlatform() {
            return platform;
        }

        public String getUserKey() {
            return userKey;
        }

        public String getTokenKey() {
            return tokenKey;
        }

        public Claim getClaim(String var) {
            return claim.get(var);
        }
    }
}
