package application.expensetracker.Controller;

import application.expensetracker.Dto.*;
import application.expensetracker.entity.User;
import application.expensetracker.Service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }


    @PostMapping
    public ResponseEntity<ExpenseDto> create(@Valid @RequestBody ExpenseRequest request) {
        User user = getCurrentUser();
        ExpenseDto created = expenseService.createExpense(request, user);
        return ResponseEntity.ok(created);
    }


    @GetMapping
    public ResponseEntity<List<ExpenseDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue =  "10") int size) {
        User user = getCurrentUser();
        List<ExpenseDto> list = expenseService.getAllExpenses(user, page, size);
        return ResponseEntity.ok(list);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody Update request) {
        User user = getCurrentUser();
        ExpenseDto updated = expenseService.updateExpense(id, request, user);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User user = getCurrentUser();
        expenseService.deleteExpense(id, user);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/summary/monthly")
    public ResponseEntity<List<MonthlySummary>> monthly(@RequestParam int year) {
        User user = getCurrentUser();
        List<MonthlySummary> summary = expenseService.getMonthlySummary(year, user);
        return ResponseEntity.ok(summary);
    }


    @GetMapping("/summary/yearly")
    public ResponseEntity<Map<Integer, BigDecimal>> yearly() {
        User user = getCurrentUser();
        Map<Integer, BigDecimal> summary = expenseService.getYearlySummary(user);
        return ResponseEntity.ok(summary);
    }
}