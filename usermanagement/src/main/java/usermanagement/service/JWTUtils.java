package usermanagement.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.catalina.User;
import org.hibernate.annotations.Comment;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.JwtSpec;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import jakarta.security.auth.message.callback.SecretKeyCallback;

@Component
public class JWTUtils {
	/*
	 * 
	 * private SecretKey key; private static final long EXPIRATION_TIME = 86400000;
	 * // 24 hours expiration
	 * 
	 * 
	 * public JWTUtils() { String secretString =
	 * "84356789369697643275974432697R694976R738467TR678T34865R6834R8763T4783786376645387456738657836778548735687R3";
	 * byte[] keyBytes=
	 * Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
	 * 
	 * this.key = new SecretKeySpec(keyBytes, "HmacSHA256"); }
	 * 
	 * 
	 * 
	 * 
	 * public String generateToken(UserDetails userDetails) {
	 * 
	 * return Jwts.builder().subject(userDetails.getUsername()) .issuedAt(new
	 * Date(System.currentTimeMillis())) .expiration(new
	 * Date(System.currentTimeMillis()+ EXPIRATION_TIME)) .signWith(key) .compact();
	 * 
	 * 
	 * }
	 * 
	 * public String generateRefreshToken(HashMap<String, Object>claims,UserDetails
	 * userDetails) {
	 * 
	 * return Jwts.builder() .claims(claims) .subject(userDetails.getUsername())
	 * .issuedAt(new Date(System.currentTimeMillis())) .expiration(new
	 * Date(System.currentTimeMillis()+ EXPIRATION_TIME)) .signWith(key) .compact();
	 * 
	 * 
	 * }
	 * 
	 * public String extractusername(String token) {
	 * 
	 * return extractClaims(token,Claims::getSubject); }
	 * 
	 * private <T> T extractClaims(String token, Function<Claims, T>
	 * claimsTFucntion) { return
	 * claimsTFucntion.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims
	 * (token).getPayload()); }
	 * 
	 * 
	 * public boolean isTokenValid(String token, UserDetails userDetails) {
	 * 
	 * final String username = extractusername(token); return
	 * (username.equals(userDetails.getUsername())&& !isTokenExpried(token)); }
	 * 
	 * public boolean isTokenExpried(String token) { return extractClaims(token,
	 * Claims::getExpiration).before(new Date()); }
	 * 
	 */
	 

	    private SecretKey Key;
	    private  static  final long EXPIRATION_TIME = 86400000;  //24 hours

	    public JWTUtils(){
	        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
	        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
	        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
	    }

	    public String generateToken(UserDetails userDetails){
	        return Jwts.builder()
	                .subject(userDetails.getUsername())
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(Key)
	                .compact();
	    }
	    public  String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
	        return Jwts.builder()
	                .claims(claims)
	                .subject(userDetails.getUsername())
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(Key)
	                .compact();
	    }

	    public  String extractUsername(String token){
	        return  extractClaims(token, Claims::getSubject);
	    }

	    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
	        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
	    }

	    public  boolean isTokenValid(String token, UserDetails userDetails){
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

	    public  boolean isTokenExpired(String token){
	        return extractClaims(token, Claims::getExpiration).before(new Date());
	    }	
	
}