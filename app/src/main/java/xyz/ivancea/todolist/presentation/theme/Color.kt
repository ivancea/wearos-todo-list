package xyz.ivancea.todolist.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val Purple200 = Color(0xFFBB86FC)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)

internal val wearColorPalette = Colors(
	primary = Purple200,
	primaryVariant = Purple700,
	onPrimary = Color.Black,

	secondary = Teal200,
	secondaryVariant = Teal200,
	onSecondary = Color.Black,

	background = Color.Black,
	onBackground = Color.White,

	surface = Color.Black,
	onSurface = Color.White,

	error = Red400,
	onError = Color.Black
)

internal val colorPalette = androidx.compose.material.Colors(
	primary = Purple200,
	primaryVariant = Purple700,
	onPrimary = Color.Black,

	secondary = Teal200,
	secondaryVariant = Teal200,
	onSecondary = Color.Black,

	background = Color.Black,
	onBackground = Color.White,

	surface = Color.Black,
	onSurface = Color.White,

	error = Red400,
	onError = Color.Black,

	isLight = false
)