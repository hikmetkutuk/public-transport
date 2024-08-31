package com.station.model;

import com.shared.model.BaseEntity;
import jakarta.persistence.Entity;
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
@Table(name = "stations")
public class Station extends BaseEntity {
    private double longitude;
    private double latitude;
    private double elevation;
    private int number;
    private String name;
}
