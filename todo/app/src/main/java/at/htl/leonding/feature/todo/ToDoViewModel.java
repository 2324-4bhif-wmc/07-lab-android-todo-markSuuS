package at.htl.leonding.feature.todo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.leonding.model.Model;
import at.htl.leonding.model.Store;
import at.htl.leonding.model.ToDo;
import at.htl.leonding.util.store.ViewModelBase;

@Singleton
public class ToDoViewModel extends ViewModelBase<ToDoViewModel.ToDoModel> {
    public record ToDoModel(List<ToDo> toDos) {}

    @Inject
    public ToDoViewModel(Store store, ToDoService toDoService) {
        super(store);
        this.toDoService = toDoService;
    }

    private final ToDoService toDoService;
    @Override
    protected ToDoModel toViewModel(Model model) {
        return new ToDoModel(List.of(model.toDos));
    }

    public void deleteTodo(ToDo todo){
        toDoService.deleteById(todo.id);
    }
}
