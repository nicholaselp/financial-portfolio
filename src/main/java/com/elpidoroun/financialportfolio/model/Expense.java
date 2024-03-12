package com.elpidoroun.financialportfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String expenseName;

    public Expense(){}

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }

    public void setExpenseName(String expenseName){ this.expenseName = expenseName; }
    public String getExpenseName(){ return expenseName; }
}
