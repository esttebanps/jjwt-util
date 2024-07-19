package com.esttebanps;


import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class JwtUtil {

    private static final Logger LOGGER = Logger.getLogger(JwtUtil.class.getName());

     /**
     * Creates a JWT (JSON Web Token) for a given user with specified roles and expiration.
     * This method generates a token that includes the username as the subject,
     * roles as claims, and sets an expiration date based on the provided duration.
     *
     * @param username     The username of the user for whom the token is being created
     * @param roles        An array of roles assigned to the user
     * @param secretKey    The secret key used to sign the JWT
     * @param durationDays The number of days until the token expires
     * @return A string representation of the JWT
     */
    public static String createToken(String username, String[] roles, SecretKey secretKey, int durationDays) {
        // Map with roles from user
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        // Duration of the token in milliseconds
        int days_to_millis = durationDays * 24 * 60 * 60 * 1000;
        // Token expiration date
        Date expiration = new Date(System.currentTimeMillis() + days_to_millis);
        // Create JWT token with the claims and expiration date
        return Jwts.builder()
                .signWith(secretKey)
                .expiration(expiration)
                .subject(username)
                .claims(claims)
                .compact();
    }

    /**
     * Validates a JSON Web Token (JWT) using the provided secret key.
     * This method attempts to parse and verify the JWT using the given secret key.
     * If the token is valid and not expired, it returns true. Otherwise, it logs
     * the specific error and returns false.
     *
     * @param secretKey The SecretKey used to verify the JWT's signature
     * @param jwt       The JWT string to be validated
     * @return          Boolean value indicating whether the token is valid (true) or not (false)
     */
    public static Boolean isTokenValid (String jwt, SecretKey secretKey) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.severe("token expired");
        } catch (UnsupportedJwtException e) {
            LOGGER.severe("token unsupported");
        } catch (MalformedJwtException e) {
            LOGGER.severe("token malformed");
        } catch (IllegalArgumentException e) {
            LOGGER.severe("illegal args");
        }
        return false;
    }

    public static String getUsernameFromToken(String jwt, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload().getSubject();
    }

    public static List getRolesFromToken(String jwt, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload().get("roles", List.class);
    }

}