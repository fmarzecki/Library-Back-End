package SpringSecurity.SpringSecurityJWT.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${secret.key}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // przekazujemy do claimsResolver referencje do metody ktora chcemy uzyc na obiekcie Claims
    // wywolujemy te metode uzywacjac claimsResolver.apply(claims) czyli claims.getSubject()
    // tym samym funkcja sama ustala zwracany typ na podstawie typu metody ktory przesylamy (getSubject() - zwraca String)

    // Returns specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Generates token without extra Claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generates token with extra claims
    public String generateToken(
        // Map will contain the extra claims that we want to add
        Map<String, Object> extraClaims,
        UserDetails userDetails) {

        return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
        
    }

    
    // Token validation, we will check if @token belongs to @userDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extracts expiration date from token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*  
        Extracts all claims from JWT
        if signature is not valid, it throws SignatureException
    */
    private Claims extractAllClaims(String token) {
        return Jwts.
        parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    /*  
        Verifies that sender of JWT is the one who claims it to be
        Ensures that token wasnt changed along the way
        The getSignInKey() method returns this private key, which can be used to sign data using the HMAC-SHA algorithm.
    */
    private Key getSignInKey() {
       byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes);
    }


    /* 
        The secret key is used to decode the token and verify its signature.
        When a token is created, it includes a signature based on the secret key, header, and payload.
        When the server receives the token in a subsequent request, it can extract the signature andcompare it to a
        new signature created from the secret key, header, and payload. If the two
        signatures match, then the token is considered valid and the server can grant access to the requested resource.
    */

}
