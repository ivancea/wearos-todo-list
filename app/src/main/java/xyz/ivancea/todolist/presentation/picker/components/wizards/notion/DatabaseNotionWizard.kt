package xyz.ivancea.todolist.presentation.picker.components.wizards.notion

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.rememberPickerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import notion.api.v1.NotionClient
import notion.api.v1.model.common.Emoji
import notion.api.v1.request.search.SearchRequest

@Composable
fun DatabaseNotionWizard(
	client: NotionClient, onSetDatabaseInfo: (DatabaseInfo) -> Unit, onBack: () -> Unit
) {
	val context = LocalContext.current
	var databases by remember { mutableStateOf<List<DatabaseInfo>?>(null) }

	LaunchedEffect(client) {
		try {
			val result = withContext(Dispatchers.IO) {
				client.search(
					query = "", filter = SearchRequest.SearchFilter(
						property = "object", value = "database"
					)
				)
			}

			databases = result.results.map { page ->
				val icon = if (page.icon is Emoji) (page.icon as Emoji).emoji else null
				val title = page.asDatabase().title?.first()?.plainText ?: "-"

				DatabaseInfo(
					id = page.id, name = title, icon = icon
				)
			}.sortedBy { it.name }
		} catch (e: Exception) {
			Log.e("DatabaseNotionWizard", "Error fetching databases", e)
			withContext(Dispatchers.Main) {
				Toast.makeText(
					context,
					"Error fetching databases (${e.message}) - TODO: Translate and use more specific messages by error type",
					Toast.LENGTH_LONG
				).show()
			}
			onBack()
		}
	}

	if (databases == null) {
		Box(modifier = Modifier.fillMaxSize()) {
			CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
		}

		return
	}

	val pickerState = rememberPickerState(
		databases?.size ?: 0, 0, false
	)

	Picker(
		state = pickerState,
		contentDescription = "Choose a database - TODO: Translate",
		modifier = Modifier.fillMaxSize()
	) { optionIndex ->
		val isSelected = pickerState.selectedOption == optionIndex
		val databaseInfo = databases!![optionIndex]

		val text = if (databaseInfo.icon != null) {
			"${databaseInfo.icon} ${databaseInfo.name}"
		} else {
			databaseInfo.name
		}

		ClickableText(
			text = AnnotatedString(text),
			style = TextStyle.Default.copy(
				color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
			),
			onClick = {
				onSetDatabaseInfo(databaseInfo)
			},
		)
	}
}