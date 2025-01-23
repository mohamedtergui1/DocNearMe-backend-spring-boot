package ma.tr.docnearme.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateTokenRefresh(UserDetails userDetails);

    String generateTokenRefresh(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractEmail(String token);

    boolean isRefreshToken(String token);

    boolean isAccessToken(String token);

    long getExpirationTime();
}
