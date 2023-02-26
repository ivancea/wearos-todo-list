package xyz.ivancea.todolist.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import xyz.ivancea.todolist.presentation.main.components.main.MainComponent
import xyz.ivancea.todolist.presentation.picker.PickerActivity

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	private val viewModel: MainViewModel by viewModels()

	private val startList = registerForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) { result: ActivityResult ->
		if (result.resultCode == Activity.RESULT_OK) {
			viewModel.reloadItemRepositoryFromStorage()
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		viewModel.reloadItemRepositoryFromStorage()

		setContent {
			MainComponent(::startPicker)
		}
	}

	private fun startPicker() {
		startList.launch(Intent(this, PickerActivity::class.java))
	}
}