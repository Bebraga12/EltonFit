package br.com.EltonFit.controller;

import br.com.EltonFit.dto.member.MemberQuickRequestDTO;
import br.com.EltonFit.dto.member.MemberRequestDTO;
import br.com.EltonFit.dto.member.MemberResponseDTO;
import br.com.EltonFit.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<MemberResponseDTO> findAll() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public MemberResponseDTO findById(@PathVariable UUID id) {
        return memberService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDTO create(@Valid @RequestBody MemberRequestDTO dto) {
        return memberService.create(dto);
    }

    @PostMapping("/quick")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDTO createWithoutLogin(@Valid @RequestBody MemberQuickRequestDTO dto) {
        return memberService.createWithoutLogin(dto);
    }

    @PutMapping("/{id}")
    public MemberResponseDTO update(@PathVariable UUID id, @Valid @RequestBody MemberRequestDTO dto) {
        return memberService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        memberService.delete(id);
    }
}
