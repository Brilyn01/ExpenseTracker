package application.expensetracker.Repository;

import application.expensetracker.Dto.MonthlySummary;
import application.expensetracker.entity.Expense;
import application.expensetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    Page<Expense> findByUser(User user, Pageable pageable);
    Expense findByIdAndUser(Long id, User user);


    @Query("SELECT new application.expensetracker.Dto.MonthlySummary(" +
            "MONTH(e.date), FUNCTION('MONTHNAME', e.date), SUM(e.amount)) " +
            "FROM Expense e WHERE YEAR(e.date) = :year AND e.user = :user " +
            "GROUP BY MONTH(e.date) ORDER BY MONTH(e.date)")
    List<MonthlySummary> getMonthlySummary(@Param("year") int year, @Param("user") User user);


    List<Expense> findByUser(User user);
}