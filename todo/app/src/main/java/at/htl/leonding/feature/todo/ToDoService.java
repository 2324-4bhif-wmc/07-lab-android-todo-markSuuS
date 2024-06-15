package at.htl.leonding.feature.todo;

import org.eclipse.microprofile.config.Config;
import org.jboss.resteasy.spi.NotImplementedYetException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.leonding.model.Store;
import at.htl.leonding.model.ToDo;
import at.htl.leonding.util.resteasy.RestApiClientBuilder;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import android.os.Build;

import androidx.annotation.RequiresApi;

@Singleton
public class ToDoService {
    public static final String JSON_PLACEHOLDER_BASE_URL_SETTING = "json.placeholder.baseurl";
    public final ToDoClient toDoClient;
    public final Store store;

    @Inject
    ToDoService(Config config, RestApiClientBuilder builder, Store store) {
        var baseUrl = config.getValue(JSON_PLACEHOLDER_BASE_URL_SETTING, String.class);
        toDoClient = builder.build(ToDoClient.class, baseUrl);
        this.store = store;
    }

    /** read the first 20 todos from the service.
     * TODO: add currentPage und pageSize to UIState
     */
    public void getAll() {
        Consumer<ToDo[]> setToDos = todos -> {
            store.apply(model -> model.toDos = todos);
        };
        CompletableFuture
                .supplyAsync(() -> toDoClient.all(0, 40))
                .thenAccept(setToDos);
    }

    public void deleteById(Long id){
        ToDo[] todos = store.get().toDos;
        todos = Arrays.stream(todos).filter(todo -> !todo.id.equals(id)).toArray(ToDo[]::new);
        final ToDo[] finalTodos = todos;
        store.apply(model -> model.toDos = finalTodos);
    }

    public void update(Long id, String title){
        ToDo[] toDos = store.get().toDos;
        toDos = Arrays.stream(toDos).peek(todo -> {
            if(todo.id.equals(id)){
                todo.title = title;
            }

        }).toArray(ToDo[]::new);
        final ToDo[] finalToDos = toDos;
        store.apply(model -> model.toDos = finalToDos);
    }

    public void create(String title) {
        var todos = store.get().toDos;
        ToDo newTodo = new ToDo();
        newTodo.title = title;
        newTodo.id = todos[todos.length-1].id + 1;
        store.apply(model -> model.toDos = addTodoToArray(todos, newTodo));
    }

    private ToDo[] addTodoToArray(ToDo[] todos, ToDo newTodo){
        ToDo[] newTodos = new ToDo[todos.length+1];

        for(int i = 0; i < todos.length; i++){
            newTodos[i] = todos[i];
        }

        newTodos[newTodos.length-1] = newTodo;
        return newTodos;
    }
}
