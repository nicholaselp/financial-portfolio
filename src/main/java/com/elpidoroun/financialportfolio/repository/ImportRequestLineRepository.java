package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.ImportRequestLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportRequestLineRepository extends JpaRepository<ImportRequestLine, Long> {

    List<ImportRequestLine> findByImportRequestId(Long importRequestId);

}