package xyz.ivancea.todolist.presentation.picker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.ivancea.todolist.TodoListStorage
import javax.inject.Inject

@HiltViewModel
class PickerViewModel @Inject constructor(
	private val storage: TodoListStorage
) : ViewModel() {
	private val _chosenPersistenceType = MutableStateFlow<PersistenceType?>(null)
	val chosenPersistenceType = _chosenPersistenceType.asStateFlow()

	fun choosePersistenceType(persistenceType: PersistenceType?) {
		_chosenPersistenceType.value = persistenceType
	}
}
