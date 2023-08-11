package com.chatop.chatopbackend.configuration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chatop.chatopbackend.entity.User;
import com.chatop.chatopbackend.repository.UserRepository;
import com.chatop.chatopbackend.service.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepo;

    private final JwtProvider jwtProvider;

    public JwtFilter(UserRepository userRepo, JwtProvider jwtProvider) {
        this.userRepo = userRepo;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        final String header = req.getHeader("Authorization");
        if (isEmpty(header) || !header.startsWith("Bearer")) {
            chain.doFilter(req, res);
            return;
        }

        String bearer = header.split(" ")[1];
        DecodedJWT decodedJWT;
        try {
            decodedJWT = this.jwtProvider.decodeJwt(bearer);
        } catch (JWTVerificationException e) {
            chain.doFilter(req, res);
            return;
        }

        Long id = this.jwtProvider.getSubject(decodedJWT);
        User ud = userRepo.findById(id).orElse(null);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(ud, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }


}
