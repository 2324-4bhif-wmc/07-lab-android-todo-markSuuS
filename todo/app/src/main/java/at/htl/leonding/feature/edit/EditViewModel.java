package at.htl.leonding.feature.edit;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.leonding.feature.todo.ToDoService;
import at.htl.leonding.model.Model;
import at.htl.leonding.model.Store;
import at.htl.leonding.model.ToDo;
import at.htl.leonding.util.store.ViewModelBase;

@Singleton
public class EditViewModel extends ViewModelBase<EditViewModel.EditModel> {
    public record EditModel(List<ToDo> toDos) {}

    @Inject
    public EditViewModel(Store store, ToDoService toDoService) {
        super(store);
        this.toDoService = toDoService;
    }

    private final ToDoService toDoService;
    @Override
    protected EditModel toViewModel(Model model) {
        return new EditModel(List.of(model.toDos));
    }

    public void updateTodo(ToDo todo, String title){
        toDoService.update(todo.id, title);
    }
}
