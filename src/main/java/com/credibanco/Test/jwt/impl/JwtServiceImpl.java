package com.credibanco.Test.jwt.impl;

import com.credibanco.Test.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.credibanco.Test.util.Constant.SECRET_KEY;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Override
    public String getToken(UserDetails user) {
        return this.getToken(new HashMap<>(), user);
    }

    @Override
    public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && (!isTokenExpired(token)));
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token) {
        return (Claims) Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parse(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
