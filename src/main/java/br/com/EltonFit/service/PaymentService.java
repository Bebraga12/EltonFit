package br.com.EltonFit.service;

import br.com.EltonFit.dto.payment.PaymentRequestDTO;
import br.com.EltonFit.dto.payment.PaymentResponseDTO;
import br.com.EltonFit.entity.Invoice;
import br.com.EltonFit.entity.Payment;
import br.com.EltonFit.entity.enums.InvoiceStatus;
import br.com.EltonFit.exception.ResourceNotFoundException;
import br.com.EltonFit.repository.InvoiceRepository;
import br.com.EltonFit.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> findAll() {
        return paymentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponseDTO findById(UUID id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public PaymentResponseDTO create(PaymentRequestDTO dto) {
        Invoice invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + dto.getInvoiceId()));

        Payment payment = Payment.builder()
                .invoice(invoice)
                .paymentDate(dto.getPaymentDate())
                .amount(dto.getAmount())
                .method(dto.getMethod())
                .build();

        invoice.setStatus(InvoiceStatus.PAID);
        invoiceRepository.save(invoice);

        return toResponse(paymentRepository.save(payment));
    }

    @Transactional
    public PaymentResponseDTO update(UUID id, PaymentRequestDTO dto) {
        Payment payment = getEntity(id);
        Invoice invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + dto.getInvoiceId()));

        payment.setInvoice(invoice);
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setAmount(dto.getAmount());
        payment.setMethod(dto.getMethod());

        return toResponse(paymentRepository.save(payment));
    }

    @Transactional
    public void delete(UUID id) {
        paymentRepository.delete(getEntity(id));
    }

    private Payment getEntity(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + id));
    }

    private PaymentResponseDTO toResponse(Payment entity) {
        return PaymentResponseDTO.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoice().getId())
                .paymentDate(entity.getPaymentDate())
                .amount(entity.getAmount())
                .method(entity.getMethod())
                .build();
    }
}
