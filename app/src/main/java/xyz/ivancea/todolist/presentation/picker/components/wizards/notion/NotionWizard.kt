package xyz.ivancea.todolist.presentation.picker.components.wizards.notion

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.rememberSwipeToDismissBoxState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import notion.api.v1.NotionClient
import xyz.ivancea.todolist.persistence.notion.NotionConnectionData
import xyz.ivancea.todolist.presentation.picker.PickerViewModel

data class DatabaseInfo(val id: String, val name: String, val icon: String?)
data class ColumnInfo(val id: String, val name: String)

@Composable
fun NotionWizard(
	onSuccess: () -> Unit, onBack: () -> Unit, viewModel: PickerViewModel = hiltViewModel()
) {
	val context = LocalContext.current

	val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

	var token by rememberSaveable { mutableStateOf("") }
	var tokenValid by remember { mutableStateOf(false) }

	var client by remember { mutableStateOf<NotionClient?>(null) }

	var databaseInfo by rememberSaveable { mutableStateOf<DatabaseInfo?>(null) }

	// Restore token to ease changing database
	LaunchedEffect(Unit) {
		try {
			if (viewModel.storage.getPersistenceType() == NotionConnectionData.TYPE) {
				val connectionData = Json.decodeFromString<NotionConnectionData>(
					viewModel.storage.getPersistenceData() ?: "{}"
				)

				token = connectionData.integrationToken
			}
		} catch (e: Exception) {
			Log.e("NotionWizard", "Error restoring token", e)
			withContext(Dispatchers.Main) {
				Toast.makeText(
					context,
					"Error fetching databases (${e.message}) - TODO: Translate and use more specific messages by error type",
					Toast.LENGTH_LONG
				).show()
			}
		}
	}

	// For token selection

	DisposableEffect(tokenValid) {
		if (tokenValid && token.isNotBlank()) {
			client = NotionClient(token)
		}

		onDispose {
			client?.close()
		}
	}

	val safeClient = client

	if (!tokenValid || safeClient == null) {
		SwipeToDismissBox(
			state = swipeToDismissBoxState,
			onDismissed = onBack
		) {
			TokenNotionWizard(token = token,
				onSetToken = { token = it },
				onValid = { tokenValid = true })
		}
		return
	}

	// For database selection

	val safeDatabaseInfo = databaseInfo

	if (safeDatabaseInfo == null) {
		SwipeToDismissBox(
			state = swipeToDismissBoxState,
			onDismissed = { tokenValid = false }
		) {
			DatabaseNotionWizard(client = safeClient,
				onSetDatabaseInfo = { databaseInfo = it },
				onBack = { tokenValid = false })
		}
		return
	}


	// For boolean property selection

	SwipeToDismissBox(
		state = swipeToDismissBoxState,
		onDismissed = { databaseInfo = null }
	) {
		BooleanColumnNotionWizard(client = safeClient,
			databaseId = safeDatabaseInfo.id,
			onSetColumnInfo = { columnInfo ->
				val connectionData = NotionConnectionData(
					integrationToken = token,
					databaseId = safeDatabaseInfo.id,
					nameColumnId = "title",
					booleanColumnId = columnInfo.id
				)

				viewModel.storage.setPersistenceData(
					NotionConnectionData.TYPE,
					Json.encodeToString(connectionData)
				)

				onSuccess()
			},
			onBack = { databaseInfo = null }
		)
	}
}