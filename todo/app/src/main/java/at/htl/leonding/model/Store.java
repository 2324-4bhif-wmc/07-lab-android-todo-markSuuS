package at.htl.leonding.model;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.htl.leonding.util.store.StoreBase;

/** This is our Storage area for our <a href="https://redux.js.org/understanding/thinking-in-redux/three-principles">single source of truth</a> {@link Model}. */
@Singleton
public class Store extends StoreBase<Model> {
    @Inject
    public Store() {
        super(Model.class, new Model());
    }
}
