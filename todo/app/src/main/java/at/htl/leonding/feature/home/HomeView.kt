package at.htl.leonding.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.htl.leonding.model.Store
import at.htl.leonding.model.UIState.Orientation
import at.htl.leonding.ui.theme.ToDoTheme
import javax.inject.Inject

/**
 * Example of an editing composable using <a href="https://medium.com/androiddevelopers/under-the-hood-of-jetpack-compose-part-2-of-2-37b2c20c6cdd">remember</a>.
 */
class HomeView @Inject constructor() {
    @Inject
    lateinit var homeScreenViewModel: HomeViewModel
    @Composable
    fun HomeScreen() {
        val model = homeScreenViewModel.subject.subscribeAsState(homeScreenViewModel.value)
        val text = remember { mutableStateOf(model.value.greetingText) }
        val orientation = model.value.orientation

        /** we update the model whenever the text is changed by the user */
        SideEffect {
            homeScreenViewModel.setGreetingText(text.value)
        }
        fun reset() {
            homeScreenViewModel.cleanToDos()
            text.value = ""
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.weight(2.0f))
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(
                    text = text.value,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                TextField(value = text.value,
                    onValueChange = {text.value = it},
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    placeholder = {
                        Text(text="Header to display", modifier = Modifier.alpha(0.2f))
                    })
            }
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                Text("${model.value.numberOfToDos} Todos have been loaded")
            }
            Spacer(Modifier.weight(1.0f))
            if (orientation == Orientation.landscape) {
                Row(Modifier.align(Alignment.CenterHorizontally)) {
                    Buttons(Modifier.align(Alignment.CenterVertically)) { reset() }
                }
            } else {
                Buttons(Modifier.align(Alignment.CenterHorizontally)) { reset() }
            }
            Spacer(Modifier.weight(2.0f))
        }
    }
    @Composable
    fun LoadAllToDosButton(modifier: Modifier) {
        Row(modifier) {
            Button(modifier = Modifier.padding(16.dp),
                onClick = { homeScreenViewModel.loadAllTodos() }) {
                Text("load ToDos")
            }
        }
    }
    @Composable
    fun ClearButton(modifier: Modifier, onClick: () -> Unit) {
        Row(modifier) {
            Button(
                onClick = {
                    //homeScreenViewModel.cleanToDos();
                    onClick()
                }) {
                Text("reset")
            }
        }
    }
    @Composable
    fun Buttons(modifier: Modifier, reset: () -> Unit) {
        LoadAllToDosButton(modifier)
        ClearButton(modifier, reset)
    }

    @Composable
    fun Preview(orientation: Orientation) {
        if (LocalInspectionMode.current) {
            val store = Store()
            store.pipe.value!!.uiState.orientation = orientation
            homeScreenViewModel = HomeViewModel(store, null)
            ToDoTheme {
                HomeScreen()
            }
        }
    }
    @Preview(name = "85%", fontScale = 0.85f)
    @Preview(name = "100%", fontScale = 1f)
    @Preview(name = "200%", fontScale = 2f)
    @Composable
    fun HomeViewPreviewPortrait() {
        Preview(Orientation.portrait)
    }

    @Preview(name = "100%", fontScale = 1f, device = "spec:parent=pixel_5,orientation=landscape")
    @Preview(name = "150%", fontScale = 1.5f, device = "spec:parent=pixel_5,orientation=landscape")
    @Composable
    fun HomeViewPreviewLandscape() {
        Preview(Orientation.landscape)
    }
}