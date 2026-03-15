package br.com.EltonFit.dto.invoice;

import br.com.EltonFit.entity.enums.InvoiceStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class InvoiceResponseDTO {
    private UUID id;
    private UUID membershipId;
    private LocalDate dueDate;
    private BigDecimal amount;
    private InvoiceStatus status;
    private UUID paymentId;
}
