package ma.tr.docnearme.service.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import ma.tr.docnearme.domain.entity.User;
import ma.tr.docnearme.dto.auth.*;
import ma.tr.docnearme.dto.user.UserDtoResponse;
import ma.tr.docnearme.exception.NotAuthException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.mapper.AuthMapper;
import ma.tr.docnearme.repository.UserRepository;
import ma.tr.docnearme.service.email.EmailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;



    public UserDtoResponse signup(RegisterRequest input) {
        User user = authMapper.toEntity(input);
        if(userRepository.existsByEmail(user.getEmail()) ){
            throw new ProcessNotCompletedException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);
        return authMapper.toDto(userRepository.save(user));
    }

    public UserDtoResponse authenticate(LoginRequest input) {
        User user = userRepository.findByEmail(input.email())
                .orElseThrow(() -> new NotAuthException("the credential dont match our data"));

        if (!user.isEnabled()) {
            throw new NotAuthException("Account not verified. Please verify your account.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return authMapper.toDto(user);
    }

    public void verifyUser(VerifyUserDto input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.email());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getVerificationCodeExpiresAt() == null ||
                    LocalDateTime.now().isAfter(user.getVerificationCodeExpiresAt())) {
                throw new NotAuthException("Verification code has expired");
            }

            if (user.getVerificationCode().equals(input.verificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpiresAt(null);
                userRepository.save(user);
            } else {
                throw new ProcessNotCompletedException("Invalid verification code");
            }
        } else {
            throw new NotAuthException("User not found");
        }
    }

    public void   resendVerificationCode(sendVerificationCodeRequest sendVerificationCodeRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(sendVerificationCodeRequest.email());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new ProcessNotCompletedException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new NotAuthException("User not found");
        }
    }

    @Override
    public UserDtoResponse authUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authMapper.toDto(user);
    }

    private void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our eBankify!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
