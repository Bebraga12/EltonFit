package br.com.EltonFit.repository;

import br.com.EltonFit.entity.Membership;
import br.com.EltonFit.entity.enums.MembershipPlanType;
import br.com.EltonFit.entity.enums.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {
    List<Membership> findByMemberId(UUID memberId);
    List<Membership> findByStatus(MembershipStatus status);
    long countByStatus(MembershipStatus status);
    boolean existsByMemberIdAndStatusAndPlanPlanType(UUID memberId, MembershipStatus status, MembershipPlanType planType);
    boolean existsByMemberIdAndStatusAndPlanPlanTypeAndIdNot(UUID memberId, MembershipStatus status, MembershipPlanType planType, UUID id);
}
