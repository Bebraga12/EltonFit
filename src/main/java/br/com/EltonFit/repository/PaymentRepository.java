package br.com.EltonFit.repository;

import br.com.EltonFit.entity.Payment;
import br.com.EltonFit.entity.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByInvoiceId(UUID invoiceId);
    List<Payment> findByMethod(PaymentMethod method);
}
