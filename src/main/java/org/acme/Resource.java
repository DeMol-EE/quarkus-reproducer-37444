package org.acme;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("hello")
@RequestScoped
public class Resource {

    @GET
    public String greet() {
        return "Hello";
    }

}
