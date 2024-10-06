package com.elpidoroun.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "import_request_line")
public class ImportRequestLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private ImportRequestStatus status;

    @Column(name = "data")
    private String data;

    @Column(name = "error")
    private String error;

    @ManyToOne()
    @JoinColumn(name = "import_request_id")
    private ImportRequest importRequest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expense_id", referencedColumnName = "id")
    private Expense expense;

    public ImportRequestLine(){}

    private ImportRequestLine(Long id, ImportRequestStatus status, String data,
                              String error, ImportRequest importRequest, Expense expense){
        this.id = id;
        this.data = requireNonNull(data, "Data is missing");
        this.error = error;
        this.status = isNull(status) ? ImportRequestStatus.PENDING : status;
        this.expense = expense;
        this.importRequest = importRequest;
    }



    public static ImportRequestLine createNewEntry(String data, ImportRequest importRequest){
        return new ImportRequestLine(null, ImportRequestStatus.PENDING, data, null, importRequest, null);
    }

    public ImportRequestLine withError(String error){
        return new ImportRequestLine(this.id, ImportRequestStatus.FAILED, this.data, error, this.importRequest, null);
    }

    public ImportRequestLine successWithExpense(Expense expense){
        return new ImportRequestLine(this.id, ImportRequestStatus.SUCCESS, this.data, null, this.importRequest, expense);
    }

    public Optional<Long> getId() { return Optional.ofNullable(id); }

    public ImportRequestStatus getStatus() { return status; }

    public String getData() { return data; }

    public Optional<String> getError() { return Optional.ofNullable(error); }
    public ImportRequest getImportRequest(){ return importRequest; }

    public Optional<Expense> getExpense(){ return Optional.ofNullable(expense); }

    public static Builder builder(){ return new Builder(); }


    public static class Builder {
        private Long id;
        private ImportRequestStatus status;
        private String data;
        private String error;
        private ImportRequest importRequest;
        private Expense expense;

        private Builder(){}

        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withImportRequestStatus(ImportRequestStatus importRequestStatus){
            this.status = importRequestStatus;
            return this;
        }

        public Builder withData(String data){
            this.data = data;
            return this;
        }

        public Builder withError(String error){
            this.error = error;
            return this;
        }

        public Builder withImportRequest(ImportRequest importRequest){
            this.importRequest = importRequest;
            return this;
        }

        public Builder withExpense(Expense expense){
            this.expense = expense;
            return this;
        }

        public ImportRequestLine build(){
            return new ImportRequestLine(id, status, data, error, importRequest, expense);
        }
    }
}