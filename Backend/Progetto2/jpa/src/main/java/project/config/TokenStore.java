package project.config;

import project.config.auth.TokenData;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {

        private final Map<String, TokenData> tokens =
                new ConcurrentHashMap<>();

        public void save(String token, String email) {

            tokens.put(token, new TokenData(
                    email,
                    token,
                    LocalDateTime.now().plusMinutes(10)
            ));
        }

        public TokenData get(String token) {

            return tokens.get(token);
        }

        public void remove(String token) {

            tokens.remove(token);
        }
    }