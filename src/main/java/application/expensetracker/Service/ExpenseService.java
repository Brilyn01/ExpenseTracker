package application.expensetracker.Service;

import application.expensetracker.Dto.ExpenseDto;
import application.expensetracker.Dto.ExpenseRequest;
import application.expensetracker.Dto.MonthlySummary;
import application.expensetracker.Dto.Update;
import application.expensetracker.entity.Category;
import application.expensetracker.entity.Expense;
import application.expensetracker.entity.User;
import application.expensetracker.Repository.CategoryRepository;
import application.expensetracker.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ExpenseDto createExpense(ExpenseRequest request, User user) {
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
        expense.setNotes(request.getNotes());
        expense.setUser(user);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        expense.setCategory(category);
        Expense saved = expenseRepository.save(expense);
        return convertToDto(saved);
    }

    public List<ExpenseDto> getAllExpenses(User user, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("date").descending());
        return expenseRepository.findByUser(user, pageRequest)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ExpenseDto updateExpense(Long id, Update request, User user) {
        Expense expense = expenseRepository.findByIdAndUser(id, user);


        expense.setAmount(request.getAmount());
        expense.setDate(request.getDate());
        expense.setNotes(request.getNotes());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NoSuchElementException("Category not found"));
            expense.setCategory(category);
        }

        Expense updated = expenseRepository.save(expense);
        return convertToDto(updated);
    }

    public void deleteExpense(Long id, User user) {
        Expense expense = expenseRepository.findByIdAndUser(id, user);

        expenseRepository.delete(expense);
    }

    public List<MonthlySummary> getMonthlySummary(int year, User user) {
        return expenseRepository.getMonthlySummary(year, user);
    }

    public Map<Integer, BigDecimal> getYearlySummary(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getDate().getYear(),
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));
    }

    private ExpenseDto convertToDto(Expense expense) {
        ExpenseDto dto = new ExpenseDto();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setNotes(expense.getNotes());
        dto.setCategoryName(expense.getCategory() != null ? expense.getCategory().getName() : "");
        return dto;
    }
}