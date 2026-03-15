package br.com.EltonFit.controller;

import br.com.EltonFit.dto.membership.MembershipRequestDTO;
import br.com.EltonFit.dto.membership.MembershipResponseDTO;
import br.com.EltonFit.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {
    @Autowired
    private MembershipService membershipService;

    @GetMapping
    public List<MembershipResponseDTO> findAll() {
        return membershipService.findAll();
    }

    @GetMapping("/me")
    public List<MembershipResponseDTO> findMine(Principal principal) {
        return membershipService.findByMemberEmail(principal.getName());
    }

    @PutMapping("/me/plan/{planId}")
    public MembershipResponseDTO changeMyPlan(Principal principal, @PathVariable UUID planId) {
        return membershipService.changeMyPlan(principal.getName(), planId);
    }

    @PutMapping("/me/cancel")
    public MembershipResponseDTO cancelMyMembership(Principal principal) {
        return membershipService.cancelMyMembership(principal.getName());
    }

    @GetMapping("/{id}")
    public MembershipResponseDTO findById(@PathVariable UUID id) {
        return membershipService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MembershipResponseDTO create(@Valid @RequestBody MembershipRequestDTO dto) {
        return membershipService.create(dto);
    }

    @PutMapping("/{id}")
    public MembershipResponseDTO update(@PathVariable UUID id, @Valid @RequestBody MembershipRequestDTO dto) {
        return membershipService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        membershipService.delete(id);
    }
}
