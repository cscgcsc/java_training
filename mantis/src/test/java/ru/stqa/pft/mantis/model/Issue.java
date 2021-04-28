package ru.stqa.pft.mantis.model;

import java.math.BigInteger;

public class Issue {
    private BigInteger id;
    private String summary;
    private String description;
    private BigInteger status;
    private String statusName;

    public Issue() {
    }

    public BigInteger getId() {
        return id;
    }

    public Issue setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public Issue setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issue setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigInteger getStatus() {
        return status;
    }

    public Issue setStatus(BigInteger status) {
        this.status = status;
        return this;
    }
    public String getStatusName() {
        return statusName;
    }

    public Issue setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

}
