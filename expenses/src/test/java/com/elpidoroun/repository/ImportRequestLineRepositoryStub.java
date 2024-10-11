package com.elpidoroun.repository;

import com.elpidoroun.model.ImportRequestLine;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.elpidoroun.repository.ExpenseRepositoryStub.generateUniqueId;

public class ImportRequestLineRepositoryStub implements ImportRequestLineRepository{

    private final List<ImportRequestLine> importRequestLineList = new ArrayList<>();

    @Override
    public ImportRequestLine save(ImportRequestLine entity) {
        var toSave = ImportRequestLine.builder()
                .withId(generateUniqueId())
                .withData(entity.getData())
                .withError(entity.getError().orElse(null))
                .withImportRequest(entity.getImportRequest())
                .withExpense(entity.getExpense().orElse(null))
                .withImportRequestStatus(entity.getStatus())
                .build();

        importRequestLineList.add(toSave);
        return toSave;
    }

    @Override
    public Optional<ImportRequestLine> findById(Long id) {
        return importRequestLineList.stream()
                .filter(importRequestLine -> importRequestLine.getId().isPresent() && importRequestLine.getId().get().equals(id))
                .findFirst();
    }

    @Override
    public List<ImportRequestLine> findAll() {
        return importRequestLineList;
    }
    @Override
    public void flush() {

    }

    @Override
    public <S extends ImportRequestLine> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ImportRequestLine> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ImportRequestLine> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ImportRequestLine getOne(Long aLong) {
        return null;
    }

    @Override
    public ImportRequestLine getById(Long aLong) {
        return null;
    }

    @Override
    public ImportRequestLine getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ImportRequestLine> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ImportRequestLine> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ImportRequestLine> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ImportRequestLine> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ImportRequestLine> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ImportRequestLine> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ImportRequestLine, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ImportRequestLine> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<ImportRequestLine> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(ImportRequestLine entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ImportRequestLine> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ImportRequestLine> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ImportRequestLine> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<ImportRequestLine> findByImportRequestId(Long importRequestId) {
        return null;
    }
}