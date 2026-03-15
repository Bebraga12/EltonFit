package br.com.EltonFit.dto.membership;

import br.com.EltonFit.entity.enums.MembershipStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MembershipRequestDTO {
    @NotNull
    private UUID memberId;

    @NotNull
    private UUID planId;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private MembershipStatus status;
}
