package xyz.ivancea.todolist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    val repository: TodoListRepository
) : ViewModel() {
}
