package xyz.ivancea.todolist.presentation.picker.components.wizards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import xyz.ivancea.todolist.R

@Composable
fun NotionWizard() {
	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier.fillMaxSize()
	) {
		Surface(color = Color.Transparent) {
			OutlinedTextField(
				label = { Text(stringResource(R.string.picker__notion)) },
				value = "asd",
				onValueChange = {},
				colors = TextFieldDefaults.outlinedTextFieldColors()
			)
		}
	}
}