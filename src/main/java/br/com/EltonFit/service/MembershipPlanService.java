package br.com.EltonFit.service;

import br.com.EltonFit.dto.membershipplan.MembershipPlanRequestDTO;
import br.com.EltonFit.dto.membershipplan.MembershipPlanResponseDTO;
import br.com.EltonFit.entity.MembershipPlan;
import br.com.EltonFit.exception.ResourceNotFoundException;
import br.com.EltonFit.repository.MembershipPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@Service
public class MembershipPlanService {
    @Autowired
    private MembershipPlanRepository membershipPlanRepository;

    @Transactional(readOnly = true)
    public List<MembershipPlanResponseDTO> findAll() {
        return membershipPlanRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public MembershipPlanResponseDTO findById(UUID id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public MembershipPlanResponseDTO create(MembershipPlanRequestDTO dto) {
        MembershipPlan plan = MembershipPlan.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .billingCycle(dto.getBillingCycle())
                .active(dto.getActive())
                .planType(dto.getPlanType())
                .build();
        return toResponse(membershipPlanRepository.save(plan));
    }

    @Transactional
    public MembershipPlanResponseDTO update(UUID id, MembershipPlanRequestDTO dto) {
        MembershipPlan plan = getEntity(id);
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setPrice(dto.getPrice());
        plan.setBillingCycle(dto.getBillingCycle());
        plan.setActive(dto.getActive());
        plan.setPlanType(dto.getPlanType());
        return toResponse(membershipPlanRepository.save(plan));
    }

    @Transactional
    public void delete(UUID id) {
        membershipPlanRepository.delete(getEntity(id));
    }

    private MembershipPlan getEntity(UUID id) {
        return membershipPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership plan not found: " + id));
    }

    private MembershipPlanResponseDTO toResponse(MembershipPlan entity) {
        return MembershipPlanResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .billingCycle(entity.getBillingCycle())
                .active(entity.getActive())
                .planType(entity.getPlanType())
                .build();
    }
}
