package br.com.EltonFit.dto.payment;

import br.com.EltonFit.entity.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PaymentRequestDTO {
    @NotNull
    private UUID invoiceId;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotNull
    private PaymentMethod method;
}
