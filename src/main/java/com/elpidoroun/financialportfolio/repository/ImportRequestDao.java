package com.elpidoroun.financialportfolio.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ImportRequestDao {

    void incrementFailedRows(Long importRequestId);
    void incrementSuccessRows(Long importRequestId);
}
