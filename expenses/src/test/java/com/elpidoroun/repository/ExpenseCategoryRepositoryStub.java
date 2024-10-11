package com.elpidoroun.repository;

import com.elpidoroun.model.ExpenseCategory;
import lombok.NonNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ExpenseCategoryRepositoryStub implements ExpenseCategoryRepository {

    private final List<ExpenseCategory> expenseCategories = new ArrayList<>();

    @Override
    public @NonNull ExpenseCategory save(@NonNull ExpenseCategory entity) {

        ExpenseCategory entityToSave;

        if (entity.getId() == null) {
            entityToSave = ExpenseCategory.builder()
                    .withId(generateUniqueId())
                    .withExpenseType(entity.getExpenseType())
                    .withCategoryName(entity.getCategoryName())
                    .withBillingInterval(entity.getBillingInterval())
                    .build();
        } else {
            entityToSave = entity;
        }

        boolean replaced = expenseCategories.stream()
                .filter(expenseCategory -> expenseCategory.getId().equals(entityToSave.getId()))
                .findFirst()
                .map(obj -> {
                    int index = expenseCategories.indexOf(obj);
                    expenseCategories.set(index, entityToSave);
                    return true;
                })
                .orElse(false);

        if (!replaced) {
            expenseCategories.add(entityToSave);
        }
        return entityToSave;
    }

    @Override
    public @NonNull Optional<ExpenseCategory> findById(@NonNull Long id) {
        return expenseCategories.stream()
                .filter(expenseCategory -> id.equals(expenseCategory.getId()))
                .findFirst();
    }

    @Override
    public Optional<ExpenseCategory> findByCategoryName(String categoryName) {
        return expenseCategories.stream()
                .filter(expenseCategory -> categoryName.equals(expenseCategory.getCategoryName()))
                .findFirst();
    }

    @Override
    public @NonNull List<ExpenseCategory> findAll() {
        return expenseCategories
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(@NonNull Long id) {
        return expenseCategories.stream()
                .anyMatch(expenseCategory -> id.equals(expenseCategory.getId()));
    }

    @Override
    public void deleteById(@NonNull Long id) {
        expenseCategories.removeIf(expenseCategory -> id.equals(expenseCategory.getId()));
    }

    public static long generateUniqueId() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }

    @Override
    public <S extends ExpenseCategory> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<ExpenseCategory> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(ExpenseCategory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ExpenseCategory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ExpenseCategory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ExpenseCategory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ExpenseCategory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ExpenseCategory getOne(Long aLong) {
        return null;
    }

    @Override
    public ExpenseCategory getById(Long aLong) {
        return null;
    }

    @Override
    public ExpenseCategory getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ExpenseCategory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ExpenseCategory> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ExpenseCategory> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ExpenseCategory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ExpenseCategory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ExpenseCategory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ExpenseCategory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<ExpenseCategory> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ExpenseCategory> findAll(Pageable pageable) {
        return null;
    }
}