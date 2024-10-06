package com.elpidoroun.repository;

import com.elpidoroun.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ImportRequestDaoStub implements ImportRequestDao {

    @NonNull private final ImportRequestRepository repository;

    @Override
    public void incrementFailedRows(Long importRequestId) {
        var importRequest = repository.findById(importRequestId)
                .orElseThrow(() -> new EntityNotFoundException("ImportRequest with ID: " + importRequestId + " not found"));

        repository.save(importRequest.clone()
                .withTotalNumberOfFailedRows(importRequest.getNumberOfFailedRows() + 1)
                .build());
    }

    @Override
    public void incrementSuccessRows(Long importRequestId) {
        var importRequest = repository.findById(importRequestId)
                .orElseThrow(() -> new EntityNotFoundException("ImportRequest with ID: " + importRequestId + " not found"));

        repository.save(importRequest.clone()
                .withTotalNumberOfSuccessRows(importRequest.getTotalNumberOfSuccessRows() + 1)
                .build());
    }
}