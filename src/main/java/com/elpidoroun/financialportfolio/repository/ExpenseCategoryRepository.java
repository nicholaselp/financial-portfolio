package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    @Query(value = "SELECT ec FROM ExpenseCategory ec WHERE ec.categoryName = ?1 AND ec.status = com.elpidoroun.financialportfolio.model.Status.ACTIVE")
    Optional<ExpenseCategory> findByCategoryName(String expenseCategory);

    @Override
    @NonNull
    @Query(value = "SELECT ec from ExpenseCategory ec WHERE ec.status = com.elpidoroun.financialportfolio.model.Status.ACTIVE")
    List<ExpenseCategory> findAll();

    @Override
    default boolean existsById(@NonNull Long id){
        return existsById(id, false);
    }

    @Query(value = "SELECT CASE WHEN COUNT(ec) > 0 THEN TRUE ELSE FALSE END FROM ExpenseCategory ec WHERE ec.id = ?1 AND (?2 = true OR ec.status != com.elpidoroun.financialportfolio.model.Status.DELETED)")
    boolean existsById(@NonNull Long id, boolean includeDeleted);

    @Modifying
    @Query(value = "UPDATE ExpenseCategory SET status = com.elpidoroun.financialportfolio.model.Status.DELETED WHERE id = ?1")
    void deleteCategoryById(@NonNull Long id);
}