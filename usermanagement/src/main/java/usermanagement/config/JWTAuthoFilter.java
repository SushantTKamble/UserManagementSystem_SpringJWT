package usermanagement.config;

import org.springframework.web.filter.OncePerRequestFilter;
 
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import usermanagement.service.JWTUtils;
import usermanagement.service.OurUserDetailsService;

@Component
public class JWTAuthoFilter extends OncePerRequestFilter {
	/*
	 * 
	 * @Autowired private JWTUtils jwtUtils;
	 * 
	 * 
	 * @Autowired private OurUserDetailsService ourUserDetailsService;
	 * 
	 * @Override protected void doFilterInternal(HttpServletRequest request,
	 * HttpServletResponse response, FilterChain filterChain) throws
	 * ServletException, IOException { // TODO Auto-generated method stub
	 * 
	 * final String authoHeader = request.getHeader("Authorization"); final String
	 * jwtToken; final String userEmail;
	 * 
	 * if(authoHeader == null || authoHeader.isBlank()) {
	 * 
	 * filterChain.doFilter(request,response); return; }
	 * 
	 * jwtToken = authoHeader.substring(7); userEmail =
	 * jwtUtils.extractusername(jwtToken);
	 * 
	 * 
	 * if(userEmail != null &&
	 * SecurityContextHolder.getContext().getAuthentication()==null) {
	 * 
	 * UserDetails userDetails =
	 * ourUserDetailsService.loadUserByUsername(userEmail);
	 * 
	 * if(jwtUtils.isTokenValid(jwtToken,userDetails)) { SecurityContext
	 * securityContext = SecurityContextHolder.createEmptyContext();
	 * 
	 * UsernamePasswordAuthenticationToken token = new
	 * UsernamePasswordAuthenticationToken(
	 * userDetails,null,userDetails.getAuthorities() ); token.setDetails(new
	 * WebAuthenticationDetailsSource().buildDetails(request));
	 * securityContext.setAuthentication(token);
	 * SecurityContextHolder.setContext(securityContext);
	 * 
	 * } } filterChain.doFilter(request, response);
	 * 
	 * 
	 * 
	 * }
	 */
	


    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private OurUserDetailsService ourUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwtToken);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = ourUserDetailsService.loadUserByUsername(userEmail);

            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}

