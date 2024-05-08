package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.Expense;
import com.elpidoroun.financialportfolio.model.Status;
import lombok.NonNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ExpenseRepositoryStub implements ExpenseRepository {
    private final List<Expense> expenseList = new ArrayList<>();

    @Override
    public Expense save(Expense entity) {

        Expense entityToSave;

        if (entity.getId() == null) {
            entityToSave = Expense.builder(generateUniqueId())
                    .withExpenseName(entity.getExpenseName())
                    .withMonthlyAllocatedAmount(entity.getMonthlyAllocatedAmount())
                    .withYearlyAllocatedAmount(entity.getYearlyAllocatedAmount())
                    .withExpenseCategory(entity.getExpenseCategory())
                    .withCreatedAt(entity.getCreatedAt())
                    .withNote(entity.getNote().orElse(null))
                    .withStatus(entity.getStatus())
                    .build();
        } else {
            entityToSave = entity;
        }

        boolean replaced = expenseList.stream()
                .filter(expenseCategory -> expenseCategory.getId().equals(entityToSave.getId()))
                .findFirst()
                .map(obj -> {
                    int index = expenseList.indexOf(obj);
                    expenseList.set(index, entityToSave);
                    return true;
                })
                .orElse(false);

        if (!replaced) {
            expenseList.add(entityToSave);
        }
        return entityToSave;
    }

    @Override
    public Optional<Expense> findById(Long id) {
        return expenseList.stream()
                .filter(expense -> id.equals(expense.getId()))
                .filter(expense -> expense.getStatus() != Status.DELETED)
                .findFirst();
    }

    @Override
    public List<Expense> findByExpenseName(String expenseName) {
        return expenseList.stream()
                .filter(expense -> expense.getExpenseName().equals(expenseName))
                .filter(expense -> expense.getStatus() != Status.DELETED)
                .collect(Collectors.toList());
    }

    @Override
    public @NonNull List<Expense> findAll() {
        return expenseList.stream()
                .filter(expense -> expense.getStatus() != Status.DELETED)
                .collect(Collectors.toList());
    }

    public static long generateUniqueId() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }


    @Override
    public List<Expense> findByExpenseCategoryId(Long expenseCategoryId) {
        return expenseList.stream()
                .filter(expense -> nonNull(expense.getExpenseCategory())
                                                && expense.getExpenseCategory().getId().equals(expenseCategoryId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(@NonNull Long id) {
        return expenseList.stream()
                .filter(expense -> !expense.getStatus().equals(Status.DELETED))
                .anyMatch(expense -> id.equals(expense.getId()));
    }

    @Override
    public void deleteById(Long id) {
        expenseList.removeIf(expense -> expense.getId().equals(id) && expense.getStatus() != Status.DELETED);
    }

    @Override
    public <S extends Expense> List<S> saveAll(Iterable<S> entities) {
        return null;
    }



    @Override
    public List<Expense> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }



    @Override
    public void delete(Expense entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Expense> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Expense> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Expense> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Expense> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Expense getOne(Long aLong) {
        return null;
    }

    @Override
    public Expense getById(Long aLong) {
        return null;
    }

    @Override
    public Expense getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Expense> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Expense> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Expense> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Expense> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Expense> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Expense> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Expense, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Expense> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Expense> findAll(Pageable pageable) {
        return null;
    }
}
