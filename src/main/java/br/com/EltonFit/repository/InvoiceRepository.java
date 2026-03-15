package br.com.EltonFit.repository;

import br.com.EltonFit.entity.Invoice;
import br.com.EltonFit.entity.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    List<Invoice> findByMembershipId(UUID membershipId);
    List<Invoice> findByMembershipMemberId(UUID memberId);
    List<Invoice> findByStatus(InvoiceStatus status);
    List<Invoice> findByDueDateBeforeAndStatus(LocalDate date, InvoiceStatus status);
    long countByStatus(InvoiceStatus status);

    @Query("select coalesce(sum(i.amount),0) from Invoice i where i.status = :status")
    BigDecimal sumAmountByStatus(InvoiceStatus status);
}
