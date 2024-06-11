package at.htl.leonding.feature.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import javax.inject.Inject

class CreateView @Inject constructor() {
    @Inject
    lateinit var createViewModel: CreateViewModel

    @Composable
    fun CreateTodo(){
        val model = createViewModel.subject.subscribeAsState(createViewModel.getValue()).value
        Text(text = "not implemented yet!")
    }
}