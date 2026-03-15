package br.com.EltonFit.dto.membershipplan;

import br.com.EltonFit.entity.enums.BillingCycle;
import br.com.EltonFit.entity.enums.MembershipPlanType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class MembershipPlanResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private BillingCycle billingCycle;
    private Boolean active;
    private MembershipPlanType planType;
}
