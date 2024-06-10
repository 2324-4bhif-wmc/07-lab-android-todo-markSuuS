package at.htl.leonding;

import android.app.Application;
import android.util.Log;

import javax.inject.Singleton;

import dagger.hilt.android.HiltAndroidApp;

/** Our application entry point.
 * Needed as the dependency injection container.
 */
@HiltAndroidApp
@Singleton
public class ToDoApplication extends Application {
}
