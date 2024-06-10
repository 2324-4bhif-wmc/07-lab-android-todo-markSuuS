package at.htl.leonding.util.immer;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import at.htl.leonding.util.mapper.Mapper;

/** Immer simplifies handling immutable data structures.
 * @author Christian Aberger (http://www.aberger.at)
 * @see <a>https://immerjs.github.io/immer/</a>
 * @see <a>https://redux.js.org/understanding/thinking-in-redux/motivation</a>
 *
 * @param <T> The type of the baseState
 */

public class Immer<T> {
    private static final String TAG = Immer.class.getSimpleName();
    final public Mapper<T> mapper;
    final Handler handler;

    public Immer(Class<? extends T> type) {
        mapper = new Mapper<T>(type);
        handler = new Handler(Looper.getMainLooper());
    }
    /** Create a deep clone of the existing model, apply a recipe to it and finally pass the new state to the consumer.
     * To reduce the load on the main thread we clone the current state in a separate thread.
     * To avoid multithreading issues we call back the recipe and resultConsumer running on the one and only Main thread of the app.
     * We do not call the resultConsumer if the clone equals the currentState,
     * @param currentState the previous readonly single source or truth
     * @param recipe the callback function that modifies parts of the cloned state
     * @param resultConsumer the callback function that uses the cloned & modified model
     */
    public void produce(final T currentState, Consumer<T> recipe, Consumer<T> resultConsumer) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            produceNextState(currentState, recipe, resultConsumer);
        } else {
            handler.post(() -> produceNextState(currentState, recipe, resultConsumer));
        }
    }
    private void produceNextState(final T state, Consumer<T> recipe, Consumer<T> resultConsumer) {
        var currentStateAsJson = mapper.toResource(state);
        var clone = mapper.fromResource(currentStateAsJson);
        recipe.accept(clone);
        var nextStateAsJson = mapper.toResource(clone);
        if (!nextStateAsJson.equals(currentStateAsJson)) {
            Log.d(TAG, String.format("=== state changed ===\n%s\n=>\n%s---", currentStateAsJson, nextStateAsJson));
            resultConsumer.accept(clone);
        } else {
            Log.w(TAG, "produce() without change");
        }
    }
}
