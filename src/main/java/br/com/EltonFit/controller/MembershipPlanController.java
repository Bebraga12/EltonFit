package br.com.EltonFit.controller;

import br.com.EltonFit.dto.membershipplan.MembershipPlanRequestDTO;
import br.com.EltonFit.dto.membershipplan.MembershipPlanResponseDTO;
import br.com.EltonFit.service.MembershipPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@RestController
@RequestMapping("/api/membership-plans")
public class MembershipPlanController {
    @Autowired
    private MembershipPlanService membershipPlanService;

    @GetMapping
    public List<MembershipPlanResponseDTO> findAll() {
        return membershipPlanService.findAll();
    }

    @GetMapping("/{id}")
    public MembershipPlanResponseDTO findById(@PathVariable UUID id) {
        return membershipPlanService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MembershipPlanResponseDTO create(@Valid @RequestBody MembershipPlanRequestDTO dto) {
        return membershipPlanService.create(dto);
    }

    @PutMapping("/{id}")
    public MembershipPlanResponseDTO update(@PathVariable UUID id, @Valid @RequestBody MembershipPlanRequestDTO dto) {
        return membershipPlanService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        membershipPlanService.delete(id);
    }
}
