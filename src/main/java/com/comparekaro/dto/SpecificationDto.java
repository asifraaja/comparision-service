package com.comparekaro.dto;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "car_features")
@Data
public class SpecificationDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    @Column(name = "engine")
    private String engine;
    @Column(name = "engine_type")
    private String engineType;
    @Column(name = "fuel_type")
    private String fuelType;
    @Column(name = "doors")
    private int doors;
    @Column(name = "seating_capacity")
    private int seatingCapacity;
    @Column(name = "fuel_tank_capacity")
    private int fuelTankCapacity;
    @Column(name = "front_brake_type")
    private String frontBrakeType;
    @Column(name = "rear_brake_type")
    private String rearBrakeType;
    @Column(name = "extra_specs")
    private String[] others;
    @Column(name = "mileage")
    private int mileage;
}
