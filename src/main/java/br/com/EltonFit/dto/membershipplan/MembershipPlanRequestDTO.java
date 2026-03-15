package br.com.EltonFit.dto.membershipplan;

import br.com.EltonFit.entity.enums.BillingCycle;
import br.com.EltonFit.entity.enums.MembershipPlanType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MembershipPlanRequestDTO {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    private BillingCycle billingCycle;

    @NotNull
    private Boolean active;

    @NotNull
    private MembershipPlanType planType;
}
