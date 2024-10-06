package com.elpidoroun.repository;

import com.elpidoroun.model.Expense;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Override
    @Query(value = "SELECT e from Expense e WHERE e.id = ?1 and e.status != com.elpidoroun.model.Status.DELETED")
    Optional<Expense> findById(Long id);
    
    @Query(value = "SELECT e FROM Expense e WHERE e.expenseName = ?1 AND e.status = 'active'")
    List<Expense> findByExpenseName(String expense);

    @Override
    @NonNull
    @Query(value = "SELECT e from Expense e WHERE e.status != com.elpidoroun.model.Status.DELETED")
    List<Expense> findAll();

    @Override
    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Expense e WHERE e.id = ?1 AND e.status != com.elpidoroun.model.Status.DELETED")
    boolean existsById(@NonNull Long id);

    @Override
    @Modifying
    @Query(value = "UPDATE Expense SET status = com.elpidoroun.model.Status.DELETED WHERE id = ?1")
    void deleteById(@NonNull Long id);

    @Query(value = "SELECT e FROM Expense e WHERE e.expenseCategory.id = ?1 AND e.status != com.elpidoroun.model.Status.DELETED")
    List<Expense> findByExpenseCategoryId(Long expenseCategoryId);
}
