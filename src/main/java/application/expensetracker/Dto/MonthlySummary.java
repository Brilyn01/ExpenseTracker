package application.expensetracker.Dto;



import java.math.BigDecimal;

public class MonthlySummary {

    private int month;
    private String monthName;
    private BigDecimal total;


    public MonthlySummary() {
    }


    public MonthlySummary(int month, String monthName, BigDecimal total) {
        this.month = month;
        this.monthName = monthName;
        this.total = total;
    }


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}