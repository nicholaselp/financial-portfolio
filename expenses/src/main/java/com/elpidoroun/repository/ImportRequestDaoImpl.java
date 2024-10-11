package com.elpidoroun.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ImportRequestDaoImpl implements ImportRequestDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void incrementFailedRows(Long importRequestId) {
        String query = "UPDATE import_request " +
                        "SET number_of_failed_rows = number_of_failed_rows + 1 " +
                        "WHERE id = ? ";
        jdbcTemplate.update(query, importRequestId);
    }

    @Override
    public void incrementSuccessRows(Long importRequestId) {
        String query = "UPDATE import_request " +
                "SET number_of_success_rows = number_of_success_rows + 1 " +
                "WHERE id = ? ";
        jdbcTemplate.update(query, importRequestId);
    }
}