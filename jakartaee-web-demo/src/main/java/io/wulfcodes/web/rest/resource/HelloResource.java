package io.wulfcodes.web.rest.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World from JAX-RS!";
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloResponse helloJson() {
        return new HelloResponse("Hello JSON", 200);
    }

    public static class HelloResponse {
        public String message;
        public int status;

        public HelloResponse() {
        }

        public HelloResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }
}
