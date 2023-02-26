package xyz.ivancea.todolist.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.ivancea.todolist.ItemRepositoryLoader
import xyz.ivancea.todolist.persistence.api.ItemRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val itemRepositoryLoader: ItemRepositoryLoader
) : ViewModel() {
	private val _itemRepository = MutableStateFlow<ItemRepository?>(null)
	val itemRepository = _itemRepository.asStateFlow()

	fun reloadItemRepositoryFromStorage() {
		_itemRepository.value = itemRepositoryLoader.getRepository()
	}

	fun clearItemRepository() {
		_itemRepository.value = null
	}
}
