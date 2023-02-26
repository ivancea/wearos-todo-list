package xyz.ivancea.todolist.presentation.picker.components.wizards.notion

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.rememberPickerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import notion.api.v1.NotionClient
import notion.api.v1.model.common.PropertyType
import xyz.ivancea.todolist.R

@Composable
fun BooleanColumnNotionWizard(
	client: NotionClient,
	databaseId: String,
	onSetColumnInfo: (ColumnInfo) -> Unit,
	onBack: () -> Unit
) {
	val context = LocalContext.current
	var columns by remember { mutableStateOf<List<ColumnInfo>?>(null) }

	LaunchedEffect(client) {
		try {
			columns = withContext(Dispatchers.IO) {
				val database = client.retrieveDatabase(databaseId)

				database.properties.values.filter { property ->
					property.type == PropertyType.Checkbox
				}.map { property ->
					ColumnInfo(
						id = property.id, name = property.name ?: "-"
					)
				}.sortedBy { it.name }
			}
		} catch (e: Exception) {
			Log.e("BooleanColumnNotionWizard", "Error fetching columns", e)
			withContext(Dispatchers.Main) {
				Toast.makeText(
					context,
					"Error fetching columns (${e.message})",
					Toast.LENGTH_LONG
				).show()
			}
			onBack()
		}
	}

	if (columns == null) {
		Box(modifier = Modifier.fillMaxSize()) {
			CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
		}

		return
	}

	val pickerState = rememberPickerState(
		columns?.size ?: 0, 0, false
	)

	Picker(
		state = pickerState,
		contentDescription = stringResource(R.string.picker__notion_column_picker_description),
		modifier = Modifier.fillMaxSize()
	) { optionIndex ->
		val isSelected = pickerState.selectedOption == optionIndex
		val columnInfo = columns!![optionIndex]

		ClickableText(
			text = AnnotatedString(columnInfo.name),
			style = TextStyle.Default.copy(
				color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
			),
			onClick = {
				onSetColumnInfo(columnInfo)
			},
		)
	}
}