package xyz.ivancea.todolist.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.ivancea.todolist.presentation.theme.TodoListTheme

@Composable
fun BaseLayout(content: @Composable BoxScope.() -> Unit) {
	TodoListTheme {
		Surface(
			modifier = Modifier
				.fillMaxSize()
		) {
			Box(modifier = Modifier.fillMaxSize()) {
				content()
			}
		}
	}
}