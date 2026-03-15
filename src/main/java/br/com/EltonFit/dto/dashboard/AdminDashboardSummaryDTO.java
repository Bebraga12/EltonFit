package br.com.EltonFit.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class AdminDashboardSummaryDTO {
    private long activeMembers;
    private long activeMemberships;
    private long pendingInvoices;
    private BigDecimal paidRevenue;
    private long totalInvoices;
    private double adimplencia;
}
