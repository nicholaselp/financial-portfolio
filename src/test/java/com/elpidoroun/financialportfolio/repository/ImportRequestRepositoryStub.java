package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.ImportRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.elpidoroun.financialportfolio.repository.ExpenseRepositoryStub.generateUniqueId;
import static java.util.Objects.isNull;

public class ImportRequestRepositoryStub implements ImportRequestRepository{

    private final List<ImportRequest> importRequestList = new ArrayList<>();

    @Override
    public ImportRequest save(ImportRequest entity) {
        ImportRequest toSave;

        if(isNull(entity.getId())){
            toSave = ImportRequest.builder()
                    .withId(generateUniqueId())
                    .withTotalNumberOfFailedRows(entity.getNumberOfFailedRows())
                    .withTotalNumberOfRows(entity.getTotalNumberOfRows())
                    .withTotalNumberOfSuccessRows(entity.getTotalNumberOfSuccessRows())
                    .build();
        } else {
            toSave = entity;
        }

        boolean replaced = importRequestList.stream()
                .filter(importRequest -> importRequest.getId().equals(toSave.getId()))
                .findFirst()
                .map(obj -> {
                    int index = importRequestList.indexOf(obj);
                    importRequestList.set(index, toSave);
                    return true;
                }).orElse(false);

        if(!replaced){
            importRequestList.add(toSave);
        }

        return toSave;
    }

    @Override
    public Optional<ImportRequest> findById(Long id) {
        return importRequestList.stream()
                .filter(importRequest -> importRequest.getId().equals(id))
                .findFirst();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ImportRequest> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ImportRequest> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ImportRequest> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ImportRequest getOne(Long aLong) {
        return null;
    }

    @Override
    public ImportRequest getById(Long aLong) {
        return null;
    }

    @Override
    public ImportRequest getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ImportRequest> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ImportRequest> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ImportRequest> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ImportRequest> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ImportRequest> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ImportRequest> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ImportRequest, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ImportRequest> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<ImportRequest> findAll() {
        return null;
    }

    @Override
    public List<ImportRequest> findAllById(Iterable<Long> longs) {
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
    public void delete(ImportRequest entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ImportRequest> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ImportRequest> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ImportRequest> findAll(Pageable pageable) {
        return null;
    }
}
