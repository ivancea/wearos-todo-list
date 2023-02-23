package xyz.ivancea.todolist.presentation.picker.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text
import xyz.ivancea.todolist.R
import xyz.ivancea.todolist.presentation.common.components.BaseLayout
import xyz.ivancea.todolist.presentation.picker.PersistenceType
import xyz.ivancea.todolist.presentation.picker.PickerViewModel
import xyz.ivancea.todolist.presentation.picker.components.wizards.NotionWizard

data class Persistence(
	val name: String, val component: @Composable () -> Unit
)

@Composable
fun PickerComponent(viewModel: PickerViewModel = hiltViewModel()) {
	val chosenPersistenceType = viewModel.chosenPersistenceType.collectAsState().value

	val persistenceToComponent =
		mapOf(PersistenceType.Notion to Persistence(stringResource(R.string.picker__notion)) { NotionWizard() })

	BaseLayout {
		if (chosenPersistenceType == null) {
			Column(
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxSize()
			) {
				for ((persistenceType, persistence) in persistenceToComponent) {
					key(persistenceType) {
						Chip(
							colors = ChipDefaults.primaryChipColors(),
							border = ChipDefaults.chipBorder(),
							onClick = { viewModel.choosePersistenceType(persistenceType) },
						) {
							Text(
								persistence.name,
								modifier = Modifier.align(Alignment.CenterVertically)
							)
						}
					}
				}
			}
		} else {
			val persistence = persistenceToComponent[chosenPersistenceType]

			if (persistence == null) {
				viewModel.choosePersistenceType(null)
				return@BaseLayout
			}

			persistence.component.invoke()
		}
	}
}