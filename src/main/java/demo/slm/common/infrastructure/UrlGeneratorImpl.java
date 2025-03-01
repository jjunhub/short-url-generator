package demo.slm.common.infrastructure;

import demo.slm.common.service.UrlGenerator;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UrlGeneratorImpl implements UrlGenerator {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String generateShortUrlId(String originalUrl) {

        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String randomValue = UUID.randomUUID().toString();
        byte[] hashedBytes = sha.digest((randomValue).getBytes());
        BigInteger num = new BigInteger(1, hashedBytes);

        StringBuilder shortUrlId = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            shortUrlId.append(BASE62.charAt(num.mod(BigInteger.valueOf(62)).intValue()));
            num = num.divide(BigInteger.valueOf(62));
        }
        return shortUrlId.toString();
    }
}
