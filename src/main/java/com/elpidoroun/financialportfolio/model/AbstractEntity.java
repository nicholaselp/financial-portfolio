package com.elpidoroun.financialportfolio.model;

import jakarta.persistence.Column;

import java.time.OffsetDateTime;

public abstract class AbstractEntity {

    @Column(name = "created_at")
    protected OffsetDateTime createdAt;

    public OffsetDateTime getCreatedAt(){ return createdAt; }

}
