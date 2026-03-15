package br.com.EltonFit.service;

import br.com.EltonFit.dto.invoice.InvoiceRequestDTO;
import br.com.EltonFit.dto.invoice.InvoiceResponseDTO;
import br.com.EltonFit.entity.Invoice;
import br.com.EltonFit.entity.Member;
import br.com.EltonFit.entity.Membership;
import br.com.EltonFit.exception.ResourceNotFoundException;
import br.com.EltonFit.repository.InvoiceRepository;
import br.com.EltonFit.repository.MemberRepository;
import br.com.EltonFit.repository.MembershipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> findAll() {
        return invoiceRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> findByMemberEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found for email: " + email));
        return invoiceRepository.findByMembershipMemberId(member.getId()).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public InvoiceResponseDTO findById(UUID id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public InvoiceResponseDTO create(InvoiceRequestDTO dto) {
        Membership membership = membershipRepository.findById(dto.getMembershipId())
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found: " + dto.getMembershipId()));

        Invoice invoice = Invoice.builder()
                .membership(membership)
                .dueDate(dto.getDueDate())
                .amount(dto.getAmount())
                .status(dto.getStatus())
                .build();

        return toResponse(invoiceRepository.save(invoice));
    }

    @Transactional
    public InvoiceResponseDTO update(UUID id, InvoiceRequestDTO dto) {
        Invoice invoice = getEntity(id);
        Membership membership = membershipRepository.findById(dto.getMembershipId())
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found: " + dto.getMembershipId()));

        invoice.setMembership(membership);
        invoice.setDueDate(dto.getDueDate());
        invoice.setAmount(dto.getAmount());
        invoice.setStatus(dto.getStatus());

        return toResponse(invoiceRepository.save(invoice));
    }

    @Transactional
    public void delete(UUID id) {
        invoiceRepository.delete(getEntity(id));
    }

    private Invoice getEntity(UUID id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + id));
    }

    private InvoiceResponseDTO toResponse(Invoice entity) {
        return InvoiceResponseDTO.builder()
                .id(entity.getId())
                .membershipId(entity.getMembership().getId())
                .dueDate(entity.getDueDate())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .paymentId(entity.getPayment() != null ? entity.getPayment().getId() : null)
                .build();
    }
}
