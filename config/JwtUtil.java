//package com.kingsmen.kingsreachdev.config;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.crypto.SecretKey;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//@Component
//public class JwtUtil {
//
//  //  private String secret = "KingsReach_dev_hrms_kingsmen";
//
//    private final long expiration = 1000 * 60 * 10 ; 
////
////    private static final String SECRET = "KingsReach_dev_hrms_kingsmen"; 
////    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes()); 
////    public static SecretKey getSignKey() {
////        return SECRET_KEY;
////    }
//    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); 
//
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) 
//                .compact();
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    public String extractUsername(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractAllClaims(token).getExpiration().before(new Date());
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}
//
//

package com.kingsmen.kingsreachdev.config;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private String secretKey = "" ; // 256-bit key
    
    public JwtUtil() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
			SecretKey key = keyGenerator.generateKey();
			secretKey =	Base64.getEncoder().encodeToString(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	// Generate a new token for the user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
        		.claims()
        		.add(claims)
        		.subject(userDetails.getUsername())
        		.issuedAt(new Date(System.currentTimeMillis()))
        		.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        		.and()
        		.signWith(getKey())
        		.compact();
//                .setClaims(claims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(getKey()) // Always sign with the same key
//                .compact();
    }

    // Extract claims from the token to validate its content
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey()) // Use the same key to validate the token
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Extract the username from the token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Validate the token against the user details
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
