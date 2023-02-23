package xyz.ivancea.todolist.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import xyz.ivancea.todolist.presentation.theme.TodoListTheme

@Composable
fun BaseLayout(content: @Composable BoxScope.() -> Unit) {
	TodoListTheme {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colors.background)
		) {
			content()
		}
	}
}