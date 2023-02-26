package xyz.ivancea.todolist.presentation.main.components.list

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.ivancea.todolist.persistence.api.ItemRepository
import xyz.ivancea.todolist.persistence.api.PersistedItem

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ListDataLoader(
	repository: ItemRepository
) {
	val coroutineScope = rememberCoroutineScope()
	val items = remember { mutableStateListOf<PersistedItem>() }
	val (refreshing, setRefreshing) = remember { mutableStateOf(false) }

	val refresh = {
		setRefreshing(true)
		coroutineScope.launch {
			val newItems = withContext(Dispatchers.IO) {
				repository.getIncompleteItems()
			}

			items.clear()
			items.addAll(newItems)
			
			setRefreshing(false)
		}
	}

	LaunchedEffect(repository) {
		refresh()
	}

	val pullRefreshState = rememberPullRefreshState(refreshing, { refresh() })

	Box(Modifier.pullRefresh(pullRefreshState)) {
		ItemsList(items) {
			withContext(Dispatchers.IO) {
				val newItem = repository.toggleDone(it)

				items.replaceAll { currentItem ->
					if (currentItem.id == newItem.id) {
						newItem
					} else {
						currentItem
					}
				}
			}
		}
		PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
	}
}