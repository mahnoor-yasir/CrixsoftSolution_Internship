package com.elm.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuditEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private LocalDateTime timestamp;
    private String actor;
    private String action;
    private String details;

    public AuditEntry() {}

    public AuditEntry(String id, String actor, String action, String details) {
        this.id = id;
        this.timestamp = LocalDateTime.now();
        this.actor = actor;
        this.action = action;
        this.details = details;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}