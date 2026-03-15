package br.com.EltonFit.auth.dto;

import br.com.EltonFit.entity.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String tokenType;
    private String email;
    private MemberRole role;
}
