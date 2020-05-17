package com.ether.web3test.security.util;

import com.ether.web3test.service.wallet.Web3jWalletService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    private Web3jWalletService web3jWalletService;

    private String secret = "javatechie";
    private String PRIVATE_KEY = "privateKey";
    private String PUBLIC_KEY = "publicKey";
    private String ACCOUNT_ADDRESS = "accountAddress";

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public String extractPrivateKey(String jwt) {
        return extractClaim(jwt, e -> e.get(PRIVATE_KEY) != null ? String.valueOf(e.get(PRIVATE_KEY)) : null);
    }

    public String extractPublicKey(String jwt) {
        return extractClaim(jwt, e -> e.get(PUBLIC_KEY) != null ? String.valueOf(e.get(PUBLIC_KEY)) : null);
    }

    public String extractAccountAddress(String jwt) {
        return extractClaim(jwt, e -> e.get(ACCOUNT_ADDRESS) != null ? String.valueOf(e.get(ACCOUNT_ADDRESS)) : null);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(Credentials credentials) {
        Map<String, Object> claims = new HashMap<>();
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
        claims.put(PRIVATE_KEY, privateKey);
        claims.put(PUBLIC_KEY, publicKey);
        claims.put(ACCOUNT_ADDRESS, credentials.getAddress());
        return createToken(claims, privateKey);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token) {
        String accountAddress = this.extractAccountAddress(token);
        return (web3jWalletService.isValidWallet(accountAddress) && !isTokenExpired(token));
    }
}
