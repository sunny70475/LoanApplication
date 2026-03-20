package org.assignment.loanapp.controller;

import jakarta.validation.Valid;
import org.assignment.loanapp.models.LoanApplication;
import org.assignment.loanapp.models.LoanResponse;
import org.assignment.loanapp.service.LoanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @PostMapping
    public LoanResponse apply(@Valid @RequestBody LoanApplication app) {
        return service.process(app);
    }
}