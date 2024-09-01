package com.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "vehicles")
public class Vehicle extends BaseEntity {
    private String plate;
    private String model;
    private String color;
    private String status;
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private Route route;
}
