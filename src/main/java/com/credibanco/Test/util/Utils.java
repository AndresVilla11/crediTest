package com.credibanco.Test.util;

import com.credibanco.Test.jwt.JwtService;
import com.credibanco.Test.repository.CardRepository;
import com.credibanco.Test.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.credibanco.Test.util.Constant.PREFIX;

@Service
@RequiredArgsConstructor
public class Utils {

    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final JwtService jwtService;

    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(PREFIX)) {
            return getUserNameFromToken(authHeader);
        }
        return null;
    }

    private String getUserNameFromToken(String authHeader) {
        return jwtService.getUserNameFromToken(authHeader.substring(7));
    }

    public Long findNewCardNumber(String productId) {
        return IntStream.range(0, 10)
                .mapToLong(number -> createRandomNumber(productId))
                .filter(number -> cardRepository.findByNumberCard(number).isEmpty())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Contact admin"));
    }

    public Long findNewTransactionId() {
        return IntStream.range(0, 10)
                .mapToLong(number -> createRandomNumber(""))
                .filter(number -> transactionRepository.findByTransactionId(String.valueOf(number)).isEmpty())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Contact admin"));
    }

    private Long createRandomNumber(String productId) {
        String randomNumber = IntStream.range(0, 10)
                .mapToLong(number -> (long) (Math.random() * 10))
                .mapToObj(Long::toString)
                .collect(Collectors.joining());
        return Long.valueOf(productId + randomNumber);
    }

}
