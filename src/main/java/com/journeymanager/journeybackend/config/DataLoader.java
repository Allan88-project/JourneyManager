package com.journeymanager.journeybackend.config;

import com.journeymanager.journeybackend.entity.Tenant;
import com.journeymanager.journeybackend.model.user.User;
import com.journeymanager.journeybackend.repository.TenantRepository;
import com.journeymanager.journeybackend.repository.UserRepository;
import com.journeymanager.journeybackend.security.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            TenantRepository tenantRepository,
            UserRepository userRepository
    ) {

        return args -> {

            if (tenantRepository.count() == 0) {

                Tenant tenant1 = new Tenant();
                tenant1.setName("Tenant One");
                tenant1.setSubdomain("tenant1");

                tenantRepository.save(tenant1);

                User admin = new User(
                        "admin@tenant1.com",
                        "password",
                        UserRole.ADMIN,
                        tenant1
                );

                User user = new User(
                        "user@tenant1.com",
                        "password",
                        UserRole.USER,
                        tenant1
                );

                userRepository.save(admin);
                userRepository.save(user);
            }
        };
    }
}
