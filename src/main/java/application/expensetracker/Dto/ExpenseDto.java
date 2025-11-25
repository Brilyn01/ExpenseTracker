package application.expensetracker.Dto;



import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseDto {

    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private String notes;
    private String categoryName;

    public ExpenseDto() {}

    public ExpenseDto(Long id, BigDecimal amount, LocalDate date, String notes, String categoryName) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
        this.categoryName = categoryName;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}