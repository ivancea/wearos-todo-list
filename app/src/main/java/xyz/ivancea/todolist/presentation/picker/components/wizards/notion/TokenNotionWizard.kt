package xyz.ivancea.todolist.presentation.picker.components.wizards.notion

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import notion.api.v1.NotionClient
import notion.api.v1.request.search.SearchRequest
import xyz.ivancea.todolist.R

@Composable
fun TokenNotionWizard(token: String, onSetToken: (String) -> Unit, onValid: () -> Unit) {
	val context = LocalContext.current
	val coroutineScope = rememberCoroutineScope()
	var checkingToken by remember { mutableStateOf(false) }

	val checkToken: () -> Unit = {
		checkingToken = true

		coroutineScope.launch {
			try {
				withContext(Dispatchers.IO) {
					NotionClient(token).use { client ->
						val result = client.search(
							query = "",
							filter = SearchRequest.SearchFilter(
								property = "object",
								value = "database"
							)
						)
					}
				}

				onValid()
			} catch (e: Exception) {
				Log.e("TokenNotionWizard", "Error trying token", e)
				withContext(Dispatchers.Main) {
					Toast.makeText(
						context,
						"Error trying token (${e.message})",
						Toast.LENGTH_LONG
					).show()
				}
			} finally {
				checkingToken = false
			}
		}
	}

	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier.fillMaxSize()
	) {
		TextField(
			label = { Text(stringResource(R.string.picker__notion_integration_token)) },
			value = token,
			onValueChange = onSetToken,
			singleLine = true,
			readOnly = checkingToken,
			colors = TextFieldDefaults.textFieldColors(),
		)
		Chip(
			colors = ChipDefaults.primaryChipColors(),
			border = ChipDefaults.chipBorder(),
			enabled = !checkingToken,
			onClick = checkToken,
			modifier = Modifier.align(Alignment.CenterHorizontally)
		) {
			androidx.wear.compose.material.Text(
				stringResource(R.string.picker__notion_use_token),
				modifier = Modifier.align(Alignment.CenterVertically)
			)
		}

	}
}