/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

/**
 *
 * @author ASUS
 */
public class RoomNotEmptyException extends RuntimeException {
    
    private final String roomId;
    
    public RoomNotEmptyException(String roomId) {
        super("Room " + roomId + " still has sensors assigned to it");
        this.roomId = roomId;
    }
    
    public String getRoomId() {
        return roomId;
    }
}
