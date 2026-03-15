package br.com.EltonFit.config;

import br.com.EltonFit.entity.Member;
import br.com.EltonFit.entity.enums.MemberRole;
import br.com.EltonFit.entity.enums.MemberStatus;
import br.com.EltonFit.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Configuration
public class AdminBootstrapConfig {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.email:adm@email.com}")
    private String adminEmail;

    @Value("${app.bootstrap.admin.password:admin123}")
    private String adminPassword;

    @Bean
    CommandLineRunner bootstrapAdmin() {
        return args -> {
            var existing = memberRepository.findByEmail(adminEmail);

            if (existing.isPresent()) {
                Member admin = existing.get();
                boolean changed = false;

                if (admin.getRole() != MemberRole.ADMIN) {
                    admin.setRole(MemberRole.ADMIN);
                    changed = true;
                }
                if (admin.getStatus() == null) {
                    admin.setStatus(MemberStatus.ACTIVE);
                    changed = true;
                }

                if (changed) {
                    memberRepository.save(admin);
                    log.warn("Usuário {} promovido/ajustado para ADMIN automaticamente.", adminEmail);
                }
                return;
            }

            Member admin = Member.builder()
                    .name("Administrador")
                    .email(adminEmail)
                    .phone("00000000000")
                    .password(passwordEncoder.encode(adminPassword))
                    .birthDate(LocalDate.of(1990, 1, 1))
                    .status(MemberStatus.ACTIVE)
                    .role(MemberRole.ADMIN)
                    .build();

            memberRepository.save(admin);
            log.warn("Admin inicial criado automaticamente: {}", adminEmail);
        };
    }
}
