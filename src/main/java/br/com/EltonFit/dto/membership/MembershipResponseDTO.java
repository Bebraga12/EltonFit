package br.com.EltonFit.dto.membership;

import br.com.EltonFit.entity.enums.MembershipStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class MembershipResponseDTO {
    private UUID id;
    private UUID memberId;
    private UUID planId;
    private String planName;
    private BigDecimal planPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private MembershipStatus status;
}
