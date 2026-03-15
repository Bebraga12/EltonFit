package br.com.EltonFit.dto.payment;

import br.com.EltonFit.entity.enums.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PaymentResponseDTO {
    private UUID id;
    private UUID invoiceId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private PaymentMethod method;
}
