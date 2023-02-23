package xyz.ivancea.todolist.presentation.picker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import xyz.ivancea.todolist.presentation.picker.components.PickerComponent

@AndroidEntryPoint
class PickerActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			PickerComponent()
		}
	}
}