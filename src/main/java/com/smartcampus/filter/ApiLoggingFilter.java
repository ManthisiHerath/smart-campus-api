/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.filter;

/**
 *
 * @author ASUS
 */
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class ApiLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(ApiLoggingFilter.class.getName());

    // Logs every incoming request
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("Incoming Request: [" 
                + requestContext.getMethod() 
                + "] " 
                + requestContext.getUriInfo().getRequestUri());
    }

    // Logs every outgoing response
    @Override
    public void filter(ContainerRequestContext requestContext, 
                       ContainerResponseContext responseContext) throws IOException {
        LOGGER.info("Outgoing Response: Status [" 
                + responseContext.getStatus() 
                + "] for [" 
                + requestContext.getMethod() 
                + "] " 
                + requestContext.getUriInfo().getRequestUri());
    }
}
