package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    @Override
    <S extends ExpenseCategory> S save(S entity);

    @Override
    @NonNull
    Optional<ExpenseCategory> findById(@NonNull Long id);

    Optional<ExpenseCategory> findByCategoryName(String expenseCategory);

    @Override
    @NonNull
    List<ExpenseCategory> findAll();

    @Override
    boolean existsById(@NonNull Long id);
}