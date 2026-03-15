package br.com.EltonFit.controller;

import br.com.EltonFit.dto.invoice.InvoiceRequestDTO;
import br.com.EltonFit.dto.invoice.InvoiceResponseDTO;
import br.com.EltonFit.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public List<InvoiceResponseDTO> findAll() {
        return invoiceService.findAll();
    }

    @GetMapping("/me")
    public List<InvoiceResponseDTO> findMine(Principal principal) {
        return invoiceService.findByMemberEmail(principal.getName());
    }

    @GetMapping("/{id}")
    public InvoiceResponseDTO findById(@PathVariable UUID id) {
        return invoiceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceResponseDTO create(@Valid @RequestBody InvoiceRequestDTO dto) {
        return invoiceService.create(dto);
    }

    @PutMapping("/{id}")
    public InvoiceResponseDTO update(@PathVariable UUID id, @Valid @RequestBody InvoiceRequestDTO dto) {
        return invoiceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        invoiceService.delete(id);
    }
}
