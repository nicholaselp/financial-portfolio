package com.elpidoroun.repository;

import com.elpidoroun.model.ImportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRequestRepository extends JpaRepository<ImportRequest, Long> {
}