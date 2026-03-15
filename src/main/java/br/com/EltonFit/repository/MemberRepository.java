package br.com.EltonFit.repository;

import br.com.EltonFit.entity.Member;
import br.com.EltonFit.entity.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByStatus(MemberStatus status);
}
