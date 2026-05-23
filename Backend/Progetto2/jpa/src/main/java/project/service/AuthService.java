package project.service;

import project.config.TokenStore;
import project.entities.Cart;
import project.entities.User;
import project.enums.Role;
import project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.config.auth.*;
import project.exception.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final TokenStore tokenStore;
    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request){

        validateName(request.getFirstName());
        validateName(request.getLastName());
        validateEmail(request.getEmail().toLowerCase().trim());
        validatePassword(request.getPassword());
        Cart cart=new Cart();
        User user=User.builder()
                .name(request.getFirstName().trim())
                .surname((request.getLastName()).trim())
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        cart.setUser(user);
        cart.setGrandTotal(BigDecimal.valueOf(0));
        user.setCart(cart);
        userRepository.save(user);
        String jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .build();
    }

    private void validateEmail(String email) {

        if(email == null || email.isBlank()) {

            throw new InvalidEmailException(
                    "Email obbligatoria"
            );
        }

        String regex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

        if(!email.matches(regex)) {

            throw new InvalidEmailException(
                    "Formato email non valido"
            );
        }


        if(userRepository.existsByEmail(email)) {

            throw new InvalidEmailException(
                    "Email già registrata"
            );
        }
    }

    private void validatePassword(String password) {

        if(password == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password obbligatoria"
            );
        }

        // Controllo se ci sono 2 numeri e poi 8 caratteri
        String regex = "^(?=(?:.*\\d){2,}).{8,}$";

        if(!password.matches(regex)) {

            throw new InvalidPasswordException();
        }

    }

    private void validateName(String name) {

        if(name == null) {

            throw new NameErrorException();
        }

        String regex = "^[A-Za-zÀ-ÿ' ]+$";

        if(!name.matches(regex)) {

            throw new NameErrorException();
        }
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail().toLowerCase().trim(),
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {

            throw new InvalidCredentialException();
        }

        var user = userRepository.findByEmail(
                request.getEmail().toLowerCase().trim()).orElseThrow(() -> new InvalidCredentialException());

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .build();
    }



    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim()).orElse(null);
        if (user == null) {
            return;
        }

        String token = UUID.randomUUID().toString();

        tokenStore.save(token, user.getEmail());

        emailService.send(
                user.getEmail(),
                "Reset Password",
                "Il tuo token è:\n" + token
        );
    }

    public void resetPassword(ResetPasswordRequest request) {

        TokenData data = tokenStore.get(request.getToken());

        if (data == null || !data.getToken().equals(request.getToken())) {
            throw new InvalidTokenException();
        }

        if (data.getExpiry().isBefore(LocalDateTime.now())) {
            tokenStore.remove(request.getToken());
            throw new InvalidTokenException();
        }

        User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow();

        validatePassword(request.getNewPassword());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        tokenStore.remove(request.getToken());
    }

}
