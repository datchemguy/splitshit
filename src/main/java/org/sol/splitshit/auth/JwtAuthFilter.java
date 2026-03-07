package org.sol.splitshit.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.sol.splitshit.repos.UserRepo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
class JwtAuthFilter extends OncePerRequestFilter {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final SecurityContext securityContext;

    public JwtAuthFilter(UserRepo userRepo,
                         JwtService jwtService,
                         SecurityContext securityContext) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.securityContext = securityContext;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if(auth != null && auth.startsWith("Bearer ")) {
            String username = jwtService.extractSubject(auth.substring(7));
            userRepo.findById(username).ifPresentOrElse(
                    user -> securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword(), user.getAuthorities())),
                    () -> {}
            );
        }
        filterChain.doFilter(request, response);
    }
}
