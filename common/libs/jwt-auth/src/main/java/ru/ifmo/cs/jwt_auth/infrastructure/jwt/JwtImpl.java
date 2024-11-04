package ru.ifmo.cs.jwt_auth.infrastructure.jwt;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import ru.ifmo.cs.jwt_auth.application.JwtGenerator;
import ru.ifmo.cs.jwt_auth.application.JwtResolver;
import ru.ifmo.cs.jwt_auth.application.JwtValidator;
import ru.ifmo.cs.misc.UserId;

@AllArgsConstructor
public class JwtImpl implements JwtGenerator, JwtResolver, JwtValidator {
    private final String secret;

    public boolean isValid(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String generateFor(UserId userId) {
        return Jwts.builder()
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 86400000))
                .setSubject(String.valueOf(userId.getUid())).signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public UserId resolveFor(String token) {
        String uid = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return UserId.of(uid);
    }
}
