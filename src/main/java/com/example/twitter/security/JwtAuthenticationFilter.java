package com.example.twitter.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

//jwt token taşıyıp taşımadığının veya geçerli olup olmadığının kontrolünü sağlayan filtre
@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        //tüm headerları loglama (postmanda sıkıntı yaşandı)
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.debug("Header - {}: {}", headerName, request.getHeader(headerName));
        }

        final String authHeader = request.getHeader("Authorization");
        log.debug("Auth header: {}", authHeader);

        //auth header yoksa veta bearer ile başlamıyorsa devam
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("geçerli auth header bulunamadı. Header şuydu: {}", authHeader);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);

            final String username = jwtService.extractUsername(jwt); //jwt içinden username i çeker

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // sec. context içinde user yoksa
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); //
                log.debug("user detayları yüklendi: {}", userDetails.getUsername());

                if (jwtService.isTokenValid(jwt, userDetails)) { //token geçerli mi. kullanıcıyı sec. context'e ekle
                    log.debug("token bu kullanıcı için geçerli: {}", userDetails.getUsername());
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    log.debug("token bu kullanıcı için geçerli değil: {}", userDetails.getUsername());
                }
            }
        } catch (Exception e) {
            log.error("jwt token error", e);
        }

        filterChain.doFilter(request, response); //zinciri devam ettir
    }
} 