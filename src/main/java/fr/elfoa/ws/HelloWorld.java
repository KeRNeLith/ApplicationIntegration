package fr.elfoa.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello")
public class HelloWorld {

  private static final String TEMPLATE  = "{ \"Hello\" : \"%1s\"}";

  @GET
  @Produces("application/json")
  public String sayHello() {
    return String.format(TEMPLATE,"World");
  }

}
