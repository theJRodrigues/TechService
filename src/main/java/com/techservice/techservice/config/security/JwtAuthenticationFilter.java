package com.techservice.techservice.config.security;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.domain.AccountRepository;
import com.techservice.techservice.modules.company.domain.CompanyRepository;
import com.techservice.techservice.shared.services.JWTService;
import com.techservice.techservice.shared.services.TokenPayload;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;

    public JwtAuthenticationFilter(JWTService service, AccountRepository repository, CompanyRepository companyRepository) {
        this.jwtService = service;
        this.accountRepository = repository;
        this.companyRepository = companyRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractTokenFromCookies(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                TokenPayload payload = jwtService.validateTokenAndGetClaims(token);

                Account account = accountRepository
                        .findByIdAndCompanyId(payload.id(), payload.companyID())
                        .orElseThrow(() -> new AccessDeniedException("Invalid token context"));

                if(!accountRepository.existsByIdAndCompanyIdAndIsActiveTrue(payload.id(), payload.companyID())){
                    throw new AccessDeniedException("Invalid token context");
                }

                if(!companyRepository.existsByIdAndIsActiveTrue(payload.companyID())){
                    throw new AccessDeniedException("Invalid token context");
                }

                AuthenticatedAccount principal = new AuthenticatedAccount(
                        payload.id(),
                        payload.companyID(),
                        payload.role()
                );

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + payload.role().name()))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (JwtException | IllegalArgumentException ignored) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
