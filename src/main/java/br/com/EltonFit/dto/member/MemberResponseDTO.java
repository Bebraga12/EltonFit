package br.com.EltonFit.dto.member;

import br.com.EltonFit.entity.enums.MemberRole;
import br.com.EltonFit.entity.enums.MemberStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class MemberResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private MemberStatus status;
    private MemberRole role;
}
