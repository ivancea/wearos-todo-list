package xyz.ivancea.todolist.presentation.main.components.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.Text
import xyz.ivancea.todolist.R
import xyz.ivancea.todolist.presentation.common.components.BaseLayout
import xyz.ivancea.todolist.presentation.main.MainViewModel
import xyz.ivancea.todolist.presentation.main.components.list.ListDataLoader

@Composable
fun MainComponent(
	onConfigure: () -> Unit, viewModel: MainViewModel = hiltViewModel()
) {
	val repository = viewModel.itemRepository.collectAsState().value

	BaseLayout {
		if (repository == null) {
			Chip(
				colors = ChipDefaults.primaryChipColors(),
				border = ChipDefaults.chipBorder(),
				onClick = { viewModel.reloadItemRepositoryFromStorage() },
				modifier = Modifier.align(Alignment.BottomCenter)
			) {
				Text("Use current data", modifier = Modifier.align(Alignment.CenterVertically))
			}
			Chip(
				colors = ChipDefaults.primaryChipColors(),
				border = ChipDefaults.chipBorder(),
				onClick = onConfigure,
				modifier = Modifier.align(Alignment.Center)
			) {
				Text(
					stringResource(R.string.main__configure),
					modifier = Modifier.align(Alignment.CenterVertically)
				)
			}
		} else {
			SwipeToDismissBox(onDismissed = { viewModel.clearItemRepository() }) { isBackground ->
				if (!isBackground) {
					ListDataLoader(repository)
				}
			}
		}
	}
}