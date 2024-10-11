package com.elpidoroun.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ImportRequestDao {

    void incrementFailedRows(Long importRequestId);
    void incrementSuccessRows(Long importRequestId);
}
