package at.htl.leonding.feature.create

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import javax.inject.Inject

class CreateView @Inject constructor() {
    @Inject
    lateinit var createViewModel: CreateViewModel

    @Composable
    fun CreateTodo(){
        val model = createViewModel.subject.subscribeAsState(createViewModel.getValue()).value
        Text(text = "Hallo Edit")
    }
}