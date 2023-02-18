package xyz.ivancea.todolist.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import xyz.ivancea.todolist.dtos.DatabaseItem

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ItemsList(
    items: List<DatabaseItem>,
    doneIds: Set<String>,
    onItemDone: (item: DatabaseItem) -> Unit
) {
    val sortedItems = items.sortedBy { it.name }.sortedBy { it.id in doneIds }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center
    ) {
        items(sortedItems, key = { it.id }) { item ->
            Item(
                item = item,
                done = item.id in doneIds,
                modifier = Modifier
                    .animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 200
                        )
                    )
            ) { onItemDone(item) }
        }
    }
}