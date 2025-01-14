package ru.ifmo.cs.jwt_token.infrastructure;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import ru.ifmo.cs.jwt_token.application.JwtGenerator;
import ru.ifmo.cs.jwt_token.application.JwtResolver;
import ru.ifmo.cs.jwt_token.application.JwtValidator;
import ru.ifmo.cs.misc.UserId;

@AllArgsConstructor
public class JwtImpl implements JwtGenerator, JwtResolver, JwtValidator {
    private final String secret;

    public boolean isValid(String authToken) {
        try {
            StringUtils.hasText(authToken);
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
