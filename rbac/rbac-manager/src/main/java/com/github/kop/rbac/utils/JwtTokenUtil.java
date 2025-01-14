package com.github.kop.rbac.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil {

  public final long jwtTokenValidity;
  private final String secret;

  public JwtTokenUtil(long expireSec, String secret) {
    jwtTokenValidity = expireSec;
    this.secret = secret;
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  // generate token for user
  public String generateToken(Long userId,Map<String, Object> claims ) {
    if (claims == null) {
      claims = new HashMap<>();
    }
    return doGenerateToken(claims, userId.toString());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  public String getUserId(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }
  public String getCompanyId(String token) {
    final Claims claims = getAllClaimsFromToken(token);
    return (String)claims.get("companyId");
  }


  public String getRole(String token) {
    final Claims claims = getAllClaimsFromToken(token);
    return (String)claims.get("role");
  }
}
