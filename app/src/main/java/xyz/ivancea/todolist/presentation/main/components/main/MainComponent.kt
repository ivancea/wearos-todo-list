package xyz.ivancea.todolist.presentation.main.components.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
	var isOpen by remember { mutableStateOf(true) }

	LaunchedEffect(repository) {
		isOpen = repository != null
	}

	BaseLayout {
		if (isOpen && repository != null) {
			SwipeToDismissBox(onDismissed = { isOpen = false }) { isBackground ->
				if (!isBackground) {
					ListDataLoader(repository)
				}
			}
		} else {
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

			if (repository != null) {
				Chip(
					colors = ChipDefaults.primaryChipColors(),
					border = ChipDefaults.chipBorder(),
					onClick = { isOpen = true },
					modifier = Modifier.align(Alignment.BottomCenter)
				) {
					Text(
						stringResource(R.string.main__use_current_data),
						modifier = Modifier.align(Alignment.CenterVertically)
					)
				}
			}
		}
	}
}