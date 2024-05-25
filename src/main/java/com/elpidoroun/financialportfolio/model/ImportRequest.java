package com.elpidoroun.financialportfolio.model;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "import_request")
public class ImportRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_number_of_rows")
    private Long totalNumberOfRows;

    @Column(name = "number_of_success_rows")
    private Long totalNumberOfSuccessRows;

    @Column(name = "number_of_failed_rows")
    private Long numberOfFailedRows;

    @OneToMany(mappedBy = "importRequest",
    cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<ImportRequestLine> importRequestLineList = new ArrayList<>();

    private ImportRequest(){};

    private ImportRequest(Long totalNumberOfRows){
        this.totalNumberOfRows = requireNonNull(totalNumberOfRows, "totalNumberOfRows is missing");
        this.numberOfFailedRows = 0L;
        this.totalNumberOfSuccessRows = 0L;
    }

    private ImportRequest(Long id, Long totalNumberOfRows, Long totalNumberOfSuccessRows, Long numberOfFailedRows){
        this.id = id;
        this.totalNumberOfRows = isNull(totalNumberOfRows) ? 0 : totalNumberOfRows;
        this.totalNumberOfSuccessRows = isNull(totalNumberOfSuccessRows) ? 0 : totalNumberOfSuccessRows;
        this.numberOfFailedRows = isNull(numberOfFailedRows) ? 0 : numberOfFailedRows;
    }

    public Long getId(){ return id; }
    public Long getTotalNumberOfRows(){ return totalNumberOfRows; }
    public Long getTotalNumberOfSuccessRows(){ return totalNumberOfSuccessRows; }
    public Long getNumberOfFailedRows(){ return numberOfFailedRows; }

    public ImportRequestStatus calculateStatus(){
        var sumOfProcessedRows = totalNumberOfSuccessRows + numberOfFailedRows;
        if(totalNumberOfRows != sumOfProcessedRows){
            throw new ValidationException("ImportRequest has not finished processing");
        }

        if(numberOfFailedRows.equals(totalNumberOfRows)){
            return ImportRequestStatus.FAILED;
        } else if(totalNumberOfSuccessRows.equals(totalNumberOfRows)){
            return ImportRequestStatus.SUCCESS;
        } else {
            return ImportRequestStatus.PARTIAL_SUCCESS;
        }
    }

    public static ImportRequest createInitialImportRequest(Long totalNumberOfRows){
        return new ImportRequest(totalNumberOfRows);
    }

    public Builder clone() {
        return ImportRequest.builder()
                .withId(this.getId())
                .withTotalNumberOfRows(this.getTotalNumberOfRows())
                .withTotalNumberOfSuccessRows(this.getTotalNumberOfSuccessRows())
                .withTotalNumberOfFailedRows(this.getNumberOfFailedRows());
    }

    public static Builder builder(){ return new Builder(); }

    public static class Builder {
        private Long id;
        private Long totalNumberOfRows;
        private Long totalNumberOfSuccessRows;
        private Long numberOfFailedRows;

        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withTotalNumberOfRows(Long totalNumberOfRows){
            this.totalNumberOfRows = totalNumberOfRows;
            return this;
        }

        public Builder withTotalNumberOfSuccessRows(Long totalNumberOfSuccessRows){
            this.totalNumberOfSuccessRows = totalNumberOfSuccessRows;
            return this;
        }

        public Builder withTotalNumberOfFailedRows(Long totalNumberOfFailedRows){
            this.numberOfFailedRows = totalNumberOfFailedRows;
            return this;
        }

        public ImportRequest build(){
            return new ImportRequest(id, totalNumberOfRows, totalNumberOfSuccessRows, numberOfFailedRows);
        }
    }
}