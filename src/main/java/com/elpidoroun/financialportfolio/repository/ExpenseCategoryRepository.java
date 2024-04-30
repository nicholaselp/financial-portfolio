package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    @CachePut(value = EXPENSE_CATEGORY_CACHE, key = "#result.id")
    @Override
    <S extends ExpenseCategory> S save(S entity);

    @Override
    @Query(value = "SELECT ec from ExpenseCategory ec WHERE ec.id = ?1 and ec.status != com.elpidoroun.financialportfolio.model.Status.DELETED")
    @NonNull
    Optional<ExpenseCategory> findById(@NonNull Long id);

    @Query(value = "SELECT ec FROM ExpenseCategory ec WHERE ec.categoryName = ?1 AND ec.status != com.elpidoroun.financialportfolio.model.Status.DELETED")
    Optional<ExpenseCategory> findByCategoryName(String expenseCategory);

    @Override
    @NonNull
    @Query(value = "SELECT ec from ExpenseCategory ec WHERE ec.status != com.elpidoroun.financialportfolio.model.Status.DELETED")
    List<ExpenseCategory> findAll();

    @Override
    @Query(value = "SELECT CASE WHEN COUNT(ec) > 0 THEN TRUE ELSE FALSE END FROM ExpenseCategory ec WHERE ec.id = ?1 AND ec.status != com.elpidoroun.financialportfolio.model.Status.DELETED")
    boolean existsById(@NonNull Long id);

    @Override
    @Modifying
    @CacheEvict(value = EXPENSE_CATEGORY_CACHE, key = "#id")
    @Query(value = "UPDATE ExpenseCategory SET status = com.elpidoroun.financialportfolio.model.Status.DELETED WHERE id = ?1")
    void deleteById(@NonNull Long id);
}