package br.com.EltonFit.controller;

import br.com.EltonFit.dto.payment.PaymentRequestDTO;
import br.com.EltonFit.dto.payment.PaymentResponseDTO;
import br.com.EltonFit.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<PaymentResponseDTO> findAll() {
        return paymentService.findAll();
    }

    @GetMapping("/{id}")
    public PaymentResponseDTO findById(@PathVariable UUID id) {
        return paymentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDTO create(@Valid @RequestBody PaymentRequestDTO dto) {
        return paymentService.create(dto);
    }

    @PutMapping("/{id}")
    public PaymentResponseDTO update(@PathVariable UUID id, @Valid @RequestBody PaymentRequestDTO dto) {
        return paymentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        paymentService.delete(id);
    }
}
