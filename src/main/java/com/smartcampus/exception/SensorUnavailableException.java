/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

/**
 *
 * @author ASUS
 */
public class SensorUnavailableException extends RuntimeException {
    
    private final String sensorId;
    
    public SensorUnavailableException(String sensorId) {
        super("Sensor " + sensorId + " is unavailable");
        this.sensorId = sensorId;
    }
    
    public String getSensorId() {
        return sensorId;
    }
}
