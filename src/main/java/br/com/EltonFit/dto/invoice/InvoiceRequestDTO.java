package br.com.EltonFit.dto.invoice;

import br.com.EltonFit.entity.enums.InvoiceStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class InvoiceRequestDTO {
    @NotNull
    private UUID membershipId;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotNull
    private InvoiceStatus status;
}
