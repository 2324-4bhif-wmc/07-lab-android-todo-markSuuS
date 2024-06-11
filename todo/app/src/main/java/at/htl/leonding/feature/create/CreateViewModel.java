package at.htl.leonding.feature.create;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.leonding.feature.todo.ToDoService;
import at.htl.leonding.model.Model;
import at.htl.leonding.model.Store;
import at.htl.leonding.model.ToDo;
import at.htl.leonding.util.store.ViewModelBase;

@Singleton
public class CreateViewModel extends ViewModelBase<CreateViewModel.CreateModel> {

    public record CreateModel(List<ToDo> toDos) {}

    @Inject
    public CreateViewModel(Store store, ToDoService toDoService) {
        super(store);
        this.toDoService = toDoService;
    }

    private final ToDoService toDoService;

    @Override
    protected CreateModel toViewModel(Model model) {
        return new CreateModel(List.of(model.toDos));
    }

    public void create(String title){
        toDoService.create(title);
    }
}
