package at.htl.leonding.feature.tabscreen;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.leonding.model.Model;
import at.htl.leonding.model.Store;
import at.htl.leonding.model.UIState;
import at.htl.leonding.util.store.ViewModelBase;

@Singleton
public class TabViewModel extends ViewModelBase<TabViewModel.TabScreenModel> {

    /** all the TabView needs to know is the number of todos and the selected tab */
    public record TabScreenModel(int getNumberOfToDos, UIState.Tab selectedTab) {}

    @Inject
    TabViewModel(Store store) {
        super(store);
    }
    @Override
    protected TabScreenModel toViewModel(Model model) {
        return new TabScreenModel(model.toDos.length, model.uiState.selectedTab);
    }
    public void selectTabByIndex(int index) {
        var tabs = Arrays.stream(UIState.Tab.values());
        var tab = tabs.filter(t -> t.index() == index).findFirst().orElse(UIState.Tab.home);
        store.apply(model -> model.uiState.selectedTab = tab);
    }
}
