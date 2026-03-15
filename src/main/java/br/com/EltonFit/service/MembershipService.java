package br.com.EltonFit.service;

import br.com.EltonFit.dto.membership.MembershipRequestDTO;
import br.com.EltonFit.dto.membership.MembershipResponseDTO;
import br.com.EltonFit.entity.Member;
import br.com.EltonFit.entity.Membership;
import br.com.EltonFit.entity.MembershipPlan;
import br.com.EltonFit.entity.enums.MembershipPlanType;
import br.com.EltonFit.entity.enums.MembershipStatus;
import br.com.EltonFit.exception.ResourceNotFoundException;
import br.com.EltonFit.repository.MemberRepository;
import br.com.EltonFit.repository.MembershipPlanRepository;
import br.com.EltonFit.repository.MembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MembershipService {
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MembershipPlanRepository membershipPlanRepository;

    @Transactional(readOnly = true)
    public List<MembershipResponseDTO> findAll() {
        return membershipRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<MembershipResponseDTO> findByMemberEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found for email: " + email));
        return membershipRepository.findByMemberId(member.getId()).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public MembershipResponseDTO findById(UUID id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public MembershipResponseDTO changeMyPlan(String email, UUID planId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found for email: " + email));
        MembershipPlan plan = membershipPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Membership plan not found: " + planId));

        Membership membership = membershipRepository.findByMemberId(member.getId()).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found for member: " + member.getId()));

        validateAcademiaPlanRule(member.getId(), plan, MembershipStatus.ACTIVE, membership.getId());

        membership.setPlan(plan);
        membership.setStatus(MembershipStatus.ACTIVE);
        membership.setEndDate(null);
        return toResponse(membershipRepository.save(membership));
    }

    @Transactional
    public MembershipResponseDTO cancelMyMembership(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found for email: " + email));

        Membership membership = membershipRepository.findByMemberId(member.getId()).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found for member: " + member.getId()));

        membership.setStatus(MembershipStatus.CANCELLED);
        membership.setEndDate(LocalDate.now());
        return toResponse(membershipRepository.save(membership));
    }

    @Transactional
    public MembershipResponseDTO create(MembershipRequestDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + dto.getMemberId()));
        MembershipPlan plan = membershipPlanRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Membership plan not found: " + dto.getPlanId()));

        validateAcademiaPlanRule(member.getId(), plan, dto.getStatus(), null);

        Membership membership = Membership.builder()
                .member(member)
                .plan(plan)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .build();

        return toResponse(membershipRepository.save(membership));
    }

    @Transactional
    public MembershipResponseDTO update(UUID id, MembershipRequestDTO dto) {
        Membership membership = getEntity(id);
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + dto.getMemberId()));
        MembershipPlan plan = membershipPlanRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Membership plan not found: " + dto.getPlanId()));

        validateAcademiaPlanRule(member.getId(), plan, dto.getStatus(), membership.getId());

        membership.setMember(member);
        membership.setPlan(plan);
        membership.setStartDate(dto.getStartDate());
        membership.setEndDate(dto.getEndDate());
        membership.setStatus(dto.getStatus());

        return toResponse(membershipRepository.save(membership));
    }

    @Transactional
    public void delete(UUID id) {
        membershipRepository.delete(getEntity(id));
    }

    private Membership getEntity(UUID id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found: " + id));
    }

    private void validateAcademiaPlanRule(UUID memberId, MembershipPlan plan, MembershipStatus status, UUID currentMembershipId) {
        if (plan.getPlanType() != MembershipPlanType.ACADEMIA || status != MembershipStatus.ACTIVE) {
            return;
        }

        boolean existsActiveAcademia = currentMembershipId == null
                ? membershipRepository.existsByMemberIdAndStatusAndPlanPlanType(memberId, MembershipStatus.ACTIVE, MembershipPlanType.ACADEMIA)
                : membershipRepository.existsByMemberIdAndStatusAndPlanPlanTypeAndIdNot(memberId, MembershipStatus.ACTIVE, MembershipPlanType.ACADEMIA, currentMembershipId);

        if (existsActiveAcademia) {
            throw new IllegalArgumentException("O aluno já possui um plano de academia ativo.");
        }
    }

    private MembershipResponseDTO toResponse(Membership entity) {
        return MembershipResponseDTO.builder()
                .id(entity.getId())
                .memberId(entity.getMember().getId())
                .planId(entity.getPlan().getId())
                .planName(entity.getPlan().getName())
                .planPrice(entity.getPlan().getPrice())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .build();
    }
}
