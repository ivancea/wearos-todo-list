package xyz.ivancea.todolist.presentation.picker.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text
import xyz.ivancea.todolist.R
import xyz.ivancea.todolist.presentation.common.components.BaseLayout
import xyz.ivancea.todolist.presentation.picker.PersistenceType
import xyz.ivancea.todolist.presentation.picker.components.wizards.notion.NotionWizard

data class Persistence(
	val name: String, val component: @Composable (onSuccess: () -> Unit, onBack: () -> Unit) -> Unit
)

@Composable
fun PickerComponent(onFinish: () -> Unit) {
	var chosenPersistenceType by rememberSaveable { mutableStateOf<PersistenceType?>(null) }

	val persistenceToComponent =
		mapOf(PersistenceType.Notion to Persistence(stringResource(R.string.picker__notion)) { onSuccess, onBack ->
			NotionWizard(onSuccess = onSuccess, onBack = onBack)
		})

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
							onClick = { chosenPersistenceType = persistenceType },
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
				chosenPersistenceType = null
				return@BaseLayout
			}

			persistence.component.invoke(onSuccess = onFinish,
				onBack = { chosenPersistenceType = null })
		}
	}
}