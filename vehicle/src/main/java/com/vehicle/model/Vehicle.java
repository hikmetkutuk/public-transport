package com.vehicle.model;

import com.shared.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Vehicle extends BaseEntity {
    private String plate;
    private String model;
    private String color;
    private String status;
    private int capacity;
}
