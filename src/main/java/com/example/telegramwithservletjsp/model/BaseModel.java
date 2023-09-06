package com.example.telegramwithservletjsp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseModel {
    {
        this.id = UUID.randomUUID();
        this.createdDate = LocalDateTime.now();
        this.updatedDate = this.createdDate;
    }
    protected UUID id;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;

}
