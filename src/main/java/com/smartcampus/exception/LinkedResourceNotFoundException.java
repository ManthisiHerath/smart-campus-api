/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

/**
 *
 * @author ASUS
 */
public class LinkedResourceNotFoundException extends RuntimeException {
    
    private final String resourceId;
    
    public LinkedResourceNotFoundException(String resourceId) {
        super("Linked resource not found: " + resourceId);
        this.resourceId = resourceId;
    }
    
    public String getResourceId() {
        return resourceId;
    }
}
