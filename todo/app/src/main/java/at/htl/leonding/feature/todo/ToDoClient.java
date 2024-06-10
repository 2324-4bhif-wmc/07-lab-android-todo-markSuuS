package at.htl.leonding.feature.todo;

import at.htl.leonding.model.ToDo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/todos")
public interface ToDoClient {
    @GET
    ToDo[] all(@QueryParam("_start") int start, @QueryParam("_limit") int maxRecords);
}
