package com.example.leavemngmtsystm;

import com.example.leavemngmtsystm.models.UserInfo;
import com.example.leavemngmtsystm.repository.UserInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LeaveMngmtSystmApplication {


    public static void main(String[] args) {
        SpringApplication.run(LeaveMngmtSystmApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(BCryptPasswordEncoder passwordEncoder, UserInfoRepository userInfoRepository) {
        return args -> userInfoRepository.save(UserInfo.builder()
                .email("example@gmail.com")
                .active(Boolean.TRUE)
                .firstName("naveen")
                .lastName("Kumar")
                .password(passwordEncoder.encode("example"))
                .role("MANAGER")
                .build());
    }

}
