package ar.edu.ubp.das.restaurante1rest.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
public class ApiKeyFilter  extends OncePerRequestFilter{
    private final String apiKey;

    public ApiKeyFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String path = req.getRequestURI();
        String method = req.getMethod();

        boolean protect =
                ("POST".equals(method) && path.matches(".*/api/v1/branches/\\d+/reservations$")) ||
                ("DELETE".equals(method) && path.matches(".*/api/v1/reservations/\\d+$")) ||
                ("POST".equals(method) && path.matches(".*/api/v1/promotions/click$")) ||
                ("GET".equals(method) && path.matches(".*/api/v1/promotions$"));

        if (!protect) {
            chain.doFilter(req, res);
            return;
        }

        String sent = req.getHeader("X-Api-Key");
        if (apiKey != null && !apiKey.isBlank() && apiKey.equals(sent)) {
            chain.doFilter(req, res);
        } else {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"unauthorized\"}");
        }
    }
}
