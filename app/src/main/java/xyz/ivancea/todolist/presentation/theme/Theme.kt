package xyz.ivancea.todolist.presentation.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun TodoListTheme(
	content: @Composable () -> Unit
) {
	androidx.compose.material.MaterialTheme(
		colors = colorPalette,
		// typography = Typography,
		// For shapes, we generally recommend using the default Material Wear shapes which are
		// optimized for round and non-round devices.
	) {
		MaterialTheme(
			colors = wearColorPalette,
			// typography = Typography,
			// For shapes, we generally recommend using the default Material Wear shapes which are
			// optimized for round and non-round devices.
			content = content
		)
	}
}