package at.htl.leonding.feature.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.htl.leonding.model.Store
import at.htl.leonding.model.ToDo
import at.htl.leonding.ui.theme.ToDoTheme
import javax.inject.Inject

class ToDoView @Inject constructor() {
    @Inject
    lateinit var toDoViewModel: ToDoViewModel

    @Composable
    fun ToDos() {
        val model = toDoViewModel.subject.subscribeAsState(toDoViewModel.getValue()).value
        val todos = model.toDos
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(todos.size) { ToDoRow(todos[it]) }
        }
    }
    @Composable
    fun ToDoRow(toDo: ToDo) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = toDo.id.toString(),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = toDo.title,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Button(
                onClick = { toDoViewModel.deleteTodo(toDo) },
                //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Done",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "", color = Color.White)
            }
        }
    }
    @Composable
    fun preview() {
        if (LocalInspectionMode.current) {
            fun toDo(id: Long, title: String): ToDo {
                val toDo = ToDo()
                toDo.id = id
                toDo.title = title
                return toDo
            }
            val todos = arrayOf(
                toDo(1, "short title"),
                toDo(2, "title generated by ChatGPT: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Viverra ipsum nunc aliquet bibendum enim facilisis gravida neque convallis. Vulputate enim nulla aliquet porttitor lacus luctus accumsan tortor. Neque egestas congue quisque egestas diam in arcu. Est lorem ipsum dolor sit amet consectetur adipiscing elit pellentesque. Ut venenatis tellus in metus. Consectetur adipiscing elit pellentesque habitant morbi tristique. Ut tellus elementum sagittis vitae et leo duis ut. Vulputate ut pharetra sit amet aliquam id diam. Arcu cursus vitae congue mauris rhoncus aenean vel. Consequat interdum varius sit amet mattis. Faucibus purus in massa tempor nec feugiat nisl pretium fusce. Facilisi nullam vehicula ipsum a arcu cursus. Enim ut tellus elementum sagittis vitae et leo. Neque sodales ut etiam sit amet nisl purus. Vitae tempus quam pellentesque nec. Diam quam nulla porttitor massa id neque aliquam vestibulum morbi. Sed sed risus pretium quam vulputate dignissim suspendisse in est. Nibh mauris cursus mattis molestie a."),
                toDo(3, "another tile that is too long for portrait mode, but ok for landscape")
            )

            val store = Store()
            store.pipe.value!!.toDos = todos
            toDoViewModel = ToDoViewModel(store, null)
            ToDoTheme {
                ToDos()
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun ToDoViewPreviewPortrait() {
        preview()
    }
    @Preview(device = "spec:parent=pixel_5,orientation=landscape")
    @Preview(name = "150%", fontScale = 1.5f, showBackground = true, device = "spec:parent=Nexus 5,orientation=landscape")
    @Composable
    fun ToDoViewPreviewLandscape() {
        preview()
    }
}
