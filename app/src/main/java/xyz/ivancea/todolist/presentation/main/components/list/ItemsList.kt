package xyz.ivancea.todolist.presentation.main.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import xyz.ivancea.todolist.R
import xyz.ivancea.todolist.persistence.api.PersistedItem

@Composable
fun ItemsList(
	items: List<PersistedItem>,
	toggleItemDone: suspend (item: PersistedItem) -> Unit
) {
	val sortedItems = items.sortedBy { it.name }.sortedBy { it.done }

	if (items.isEmpty()) {
		Box(
			modifier = Modifier
				.fillMaxSize(),
		) {
			Text(
				text = stringResource(R.string.list__no_items),
				textAlign = TextAlign.Center,
				modifier = Modifier.align(Alignment.Center)
			)
		}
		return
	}

	LazyColumn(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colors.background),
		verticalArrangement = Arrangement.Center
	) {
		items(items = sortedItems, key = { it.id }) { item ->
			Item(
				item = item
			) { toggleItemDone(item) }
		}
	}
}