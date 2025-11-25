package application.expensetracker.Dto;



import java.math.BigDecimal;
import java.time.LocalDate;

public class Update {

    private BigDecimal amount;
    private LocalDate date;
    private String notes;
    private Long categoryId;

    public Update() {}

    public Update(BigDecimal amount, LocalDate date, String notes, Long categoryId) {
        this.amount = amount;
        this.date = date;
        this.notes = notes;
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
