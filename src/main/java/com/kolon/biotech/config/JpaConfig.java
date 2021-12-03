package com.kolon.biotech.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> {

            try{
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                UserDetails userDetails = (UserDetails)principal;
                String currentUser = userDetails.getUsername();

                if(currentUser != null){
                    return Optional.of(currentUser);
                }else{
                    return Optional.of("Anonymous");
                }
            }catch(Exception e){
                return Optional.of("Anonymous");
            }

        };
    }
}

