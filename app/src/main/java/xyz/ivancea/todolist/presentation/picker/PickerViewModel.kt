package xyz.ivancea.todolist.presentation.picker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.ivancea.todolist.TodoListStorage
import javax.inject.Inject

@HiltViewModel
class PickerViewModel @Inject constructor(
	val storage: TodoListStorage
) : ViewModel()
