/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resources;

/**
 *
 * @author ASUS
 */
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response discover() {
        Map<String, Object> response = new LinkedHashMap<>();
        
        // API versioning info
        response.put("api", "Smart Campus API");
        response.put("version", "1.0");
        response.put("status", "running");
        
        // Admin contact
        Map<String, String> contact = new LinkedHashMap<>();
        contact.put("name", "Smart Campus Admin");
        contact.put("email", "admin@smartcampus.ac.uk");
        response.put("contact", contact);
        
        // Resource links (HATEOAS)
        Map<String, String> links = new LinkedHashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        response.put("resources", links);
        
        return Response.ok(response).build();
    }
}
