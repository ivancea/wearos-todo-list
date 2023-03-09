package xyz.ivancea.todolist.presentation.main.components.list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
	val context = LocalContext.current
	val coroutineScope = rememberCoroutineScope()
	val items = remember { mutableStateListOf<PersistedItem>() }
	val (refreshing, setRefreshing) = remember { mutableStateOf(false) }

	val refresh = {
		setRefreshing(true)
		coroutineScope.launch {
			try {
				val newItems = withContext(Dispatchers.IO) {
					repository.getIncompleteItems()
				}

				items.clear()
				items.addAll(newItems)
			} catch (e: Exception) {
				Log.e("ListDataLoader", "Error fetching items", e)
				withContext(Dispatchers.Main) {
					Toast.makeText(
						context,
						"Error fetching items (${e.message})",
						Toast.LENGTH_LONG
					).show()
				}
			} finally {
				setRefreshing(false)
			}
		}
	}

	LaunchedEffect(repository) {
		refresh()
	}

	val pullRefreshState = rememberPullRefreshState(refreshing, { refresh() })

	Box(Modifier.pullRefresh(pullRefreshState)) {
		ItemsList(repository.getTranslatedName(context), items) {
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
		PullRefreshIndicator(
			refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter)
		)
	}
}