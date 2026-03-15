package br.com.EltonFit.service;

import br.com.EltonFit.dto.member.MemberQuickRequestDTO;
import br.com.EltonFit.dto.member.MemberRequestDTO;
import br.com.EltonFit.dto.member.MemberResponseDTO;
import br.com.EltonFit.entity.Member;
import br.com.EltonFit.entity.enums.MemberRole;
import br.com.EltonFit.exception.ResourceNotFoundException;
import br.com.EltonFit.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> findAll() {
        return memberRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO findById(UUID id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public MemberResponseDTO create(MemberRequestDTO dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(passwordEncoder.encode(dto.getPassword()))
                .birthDate(dto.getBirthDate())
                .status(dto.getStatus())
                .role(MemberRole.USER)
                .build();
        return toResponse(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDTO createWithoutLogin(MemberQuickRequestDTO dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .birthDate(dto.getBirthDate())
                .status(dto.getStatus())
                .role(MemberRole.USER)
                .build();
        return toResponse(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDTO update(UUID id, MemberRequestDTO dto) {
        Member member = getEntity(id);
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
        member.setBirthDate(dto.getBirthDate());
        member.setStatus(dto.getStatus());
        return toResponse(memberRepository.save(member));
    }

    @Transactional
    public void delete(UUID id) {
        memberRepository.delete(getEntity(id));
    }

    private Member getEntity(UUID id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + id));
    }

    private MemberResponseDTO toResponse(Member entity) {
        return MemberResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .birthDate(entity.getBirthDate())
                .status(entity.getStatus())
                .role(entity.getRole())
                .build();
    }
}
