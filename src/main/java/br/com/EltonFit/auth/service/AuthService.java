package br.com.EltonFit.auth.service;

import br.com.EltonFit.auth.dto.LoginRequestDTO;
import br.com.EltonFit.auth.dto.LoginResponseDTO;
import br.com.EltonFit.entity.Member;
import br.com.EltonFit.entity.enums.MemberRole;
import br.com.EltonFit.repository.MemberRepository;
import br.com.EltonFit.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MemberRepository memberRepository;

    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String token = jwtService.generateToken(request.getEmail());
        MemberRole role = member.getRole() != null ? member.getRole() : MemberRole.USER;
        return new LoginResponseDTO(token, "Bearer", member.getEmail(), role);
    }
}
