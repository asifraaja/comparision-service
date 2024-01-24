package com.comparekaro.dto;

import lombok.Data;

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
public class FeatureDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    @Column(name = "overspeed_warning")
    private String overspeedWarning;
    @Column(name = "airbags")
    private int airbags;
    @Column(name = "seat_belt_warning")
    private String seatBeltWarning;
    @Column(name = "hill_hold_control")
    private String hillHoldControl;
    @Column(name = "air_conditioner")
    private String airConditioner;
    @Column(name = "heater")
    private String heater;
    @Column(name = "bluetooth")
    private String bluetooth;
    @Column(name = "internet")
    private String internet;
    @Column(name = "extra_features")
    private String[] extraFeatures;
}
