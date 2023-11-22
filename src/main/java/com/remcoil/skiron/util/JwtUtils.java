package com.remcoil.skiron.util;

import com.remcoil.skiron.database.entity.Employee;
import com.remcoil.skiron.model.employee.EmployeeDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private final static long MS_IN_DAY = 8_640_000L;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private long lifetime;


    public String generateToken(EmployeeDetails employeeDetails) {
        Date now = new Date();

        return Jwts.builder()
                .issuedAt(now)
                .claim("id", employeeDetails.id())
                .claim("phone", employeeDetails.phone())
                .claim("role", employeeDetails.role())
                .expiration(new Date(now.getTime() + lifetime * MS_IN_DAY))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .compact();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPhone(String token) {
        return getAllClaims(token).get("phone", String.class);
    }

    public Employee.Role getRole(String token) {
        return Employee.Role.valueOf(getAllClaims(token).get("role", String.class));
    }
}
