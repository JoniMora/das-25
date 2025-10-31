package ubp.das.ristorinobackend.security;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
    // token -> exp (epoch seconds)
    private final Map<String, Long> revoked = new ConcurrentHashMap<>();

    public void revoke(String token, long expiresAtEpochSeconds) {
        revoked.put(token, expiresAtEpochSeconds);
    }

    public boolean isRevoked(String token) {
        Long exp = revoked.get(token);
        if (exp == null) return false;
        // limpieza básica por expiración
        if (Instant.now().getEpochSecond() > exp) {
            revoked.remove(token);
            return false;
        }
        return true;
    }
}

