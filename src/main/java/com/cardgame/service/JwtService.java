package com.cardgame.service;


import com.cardgame.entity.User;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "I4krgP23R8GgbLnT2wRaPrYDm5Sk8INS70XWDPQF67JHymRjUULcdA6G42+wU4q/drb6jT2JeIqHP3SD4nsvEMInhbReoYXvxE3zF0TLXe9HBurpLj0gBYaHxa6Ste8ejgx+fzBRbnEixuBVPfmu8GGtjh0WhaOzHw2JMoex7ieaztyKdPgywHz3f09VcILWRRjqom616lSUhsiK5OafxwpK/j4utDhmmmzZSayUe9AL6PqdGDg+AS0slkBjzUUfKd2p573EQ1UJqO9tScFk4l6UR3xWhjX55MQbg366DGZQDPA2plmeGfgsYkSBrfdT5sFnevQ7/7kV4a0VSIbFDNa4TQdSi2TElsceOA14X0g=";

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public int getExpirationSeconds(String token) {
        return (int)((extractClaim(token, Claims::getExpiration).getTime() -  new Date().getTime()) / 1000);
    }



    public static String generateToken(User user) {
        return Jwts
                .builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24)) // 1 nap
                .signWith(getSigningKey())
                .compact();
    }

    public static String generateRefreshToken(User user) {
        return Jwts
                .builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date( System.currentTimeMillis()+ 1000L * 60L * 60L * 24L * 60L)) // 60 nap
                .signWith(getSigningKey())
                .compact();
    }



    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, User user) {
        final String id = extractUserId(token);
        return (id.equals(String.valueOf(user.getId())) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
