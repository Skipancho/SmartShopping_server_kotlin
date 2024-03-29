package com.example.smartshopping.domain.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

object JWTUtil {
    private const val ISSUER = "SmartShopping"
    private const val SUBJECT = "Auth"
    private const val EXPIRE_TIME = 60L * 60 * 2 * 1000 //2시간
    private const val REFRESH_EXPIRE_TIME = 60L * 60 * 24 * 30 * 1000 //30일

    private const val SECRET = "1q2w3e4r"
    private val algorithm: Algorithm = Algorithm.HMAC256(SECRET)

    private const val REFRESH_SECRET = "r4e3w2q1"
    private val refreshAlgorithm: Algorithm = Algorithm.HMAC256(REFRESH_SECRET)

    fun createToken(userId: String): String = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + EXPIRE_TIME))
        .withClaim(JWTClaims.USER_ID, userId)
        .sign(algorithm)

    fun createRefreshToken(userId: String): String = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + REFRESH_EXPIRE_TIME))
        .withClaim(JWTClaims.USER_ID, userId)
        .sign(refreshAlgorithm)

    fun verify(token: String): DecodedJWT =
        JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun verifyRefresh(token: String): DecodedJWT =
        JWT.require(refreshAlgorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun extractUserId(jwt: DecodedJWT): String =
        jwt.getClaim(JWTClaims.USER_ID).asString()

    object JWTClaims {
        const val USER_ID = "userId"
    }
}