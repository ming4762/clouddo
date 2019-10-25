package com.clouddo.auth.server.util


import com.cloudd.commons.auth.util.RsaKeyHelper
import com.clouddo.auth.server.model.JWTUser
import com.clouddo.commons.common.constatns.CommonConstants
import com.clouddo.commons.common.util.StringHelper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.joda.time.DateTime

/**
 * Created by ace on 2017/9/10.
 */
object JWTHelper {
    private val rsaKeyHelper = RsaKeyHelper()
    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param priKeyPath
     * @param timeout
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun generateToken(jwtInfo: JWTUser, priKeyPath: String, timeout: Long): String {
        val expire = timeout.toInt()
        return Jwts.builder()
                .setSubject(jwtInfo.username)
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.userId)
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.name)
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath))
                .compact()
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun parserToken(token: String, pubKeyPath: String): Jws<Claims> {
        return Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath)).parseClaimsJws(token)
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getInfoFromToken(token: String, pubKeyPath: String): JWTUser {
        val claimsJws = parserToken(token, pubKeyPath)
        val body = claimsJws.body
        return JWTUser(body.subject, StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_USER_ID]), StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_NAME]))
    }

}
