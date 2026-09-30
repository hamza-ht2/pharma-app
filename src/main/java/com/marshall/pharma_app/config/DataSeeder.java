package com.marshall.pharma_app.config;

import com.marshall.pharma_app.models.User;
import com.marshall.pharma_app.models.enums.Role;
import com.marshall.pharma_app.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.existsByEmail("admin@pharma.ma")){
            return;
        }
        User admin = User.builder()
                .fullName("admin user")
                .email("admin@pharma.ma")
                .phone("0645728393")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .active(true)
                .build();
        userRepository.save(admin);
        System.out.println("admin created");
    }
}
