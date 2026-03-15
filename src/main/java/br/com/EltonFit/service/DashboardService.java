package br.com.EltonFit.service;

import br.com.EltonFit.dto.dashboard.AdminDashboardSummaryDTO;
import br.com.EltonFit.entity.enums.InvoiceStatus;
import br.com.EltonFit.entity.enums.MemberStatus;
import br.com.EltonFit.entity.enums.MembershipStatus;
import br.com.EltonFit.repository.InvoiceRepository;
import br.com.EltonFit.repository.MemberRepository;
import br.com.EltonFit.repository.MembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DashboardService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Transactional(readOnly = true)
    public AdminDashboardSummaryDTO getAdminSummary() {
        long activeMembers = memberRepository.countByStatus(MemberStatus.ACTIVE);
        long activeMemberships = membershipRepository.countByStatus(MembershipStatus.ACTIVE);
        long pendingInvoices = invoiceRepository.countByStatus(InvoiceStatus.PENDING) +
                invoiceRepository.countByStatus(InvoiceStatus.OVERDUE);
        long paidInvoices = invoiceRepository.countByStatus(InvoiceStatus.PAID);
        long totalInvoices = invoiceRepository.count();

        double adimplencia = totalInvoices == 0 ? 0d : (paidInvoices * 100.0) / totalInvoices;

        return AdminDashboardSummaryDTO.builder()
                .activeMembers(activeMembers)
                .activeMemberships(activeMemberships)
                .pendingInvoices(pendingInvoices)
                .paidRevenue(invoiceRepository.sumAmountByStatus(InvoiceStatus.PAID))
                .totalInvoices(totalInvoices)
                .adimplencia(Math.round(adimplencia * 100.0) / 100.0)
                .build();
    }
}
