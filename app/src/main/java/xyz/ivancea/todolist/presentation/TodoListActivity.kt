package xyz.ivancea.todolist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.ivancea.todolist.TodoListViewModel
import xyz.ivancea.todolist.dtos.DatabaseItem
import xyz.ivancea.todolist.presentation.components.ItemsList
import xyz.ivancea.todolist.presentation.theme.TodoListTheme

@AndroidEntryPoint
class TodoListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun WearApp(
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val repository = viewModel.repository

    val (items, setItems) = remember { mutableStateOf<List<DatabaseItem>>(emptyList()) }
    val (doneIds, setDoneIds) = remember { mutableStateOf<Set<String>>(emptySet()) }
    val (refreshing, setRefreshing) = remember { mutableStateOf(false) }

    val refresh = {
        setRefreshing(true)
        coroutineScope.launch {
            val newItems = withContext(Dispatchers.IO) {
                repository.getItems()
            }

            setItems(newItems)
            setRefreshing(false)
        }
    }

    LaunchedEffect(Unit) {
        refresh()
    }

    val pullRefreshState = rememberPullRefreshState(refreshing, { refresh() })

    Box(Modifier.pullRefresh(pullRefreshState)) {
        TodoListTheme {
            ItemsList(items, doneIds) {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        setDoneIds(doneIds + it.id)
                        repository.markAsDone(it)
                    }
                }
            }
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}