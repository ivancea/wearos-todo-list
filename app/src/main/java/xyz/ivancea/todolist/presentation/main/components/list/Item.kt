package xyz.ivancea.todolist.presentation.main.components.list

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.CompactChip
import androidx.wear.compose.material.LocalTextStyle
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import xyz.ivancea.todolist.persistence.api.EmojiIcon
import xyz.ivancea.todolist.persistence.api.PersistedItem
import xyz.ivancea.todolist.persistence.api.UrlIcon

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun LazyItemScope.Item(
	item: PersistedItem,
	toggleItemDone: suspend () -> Unit
) {
	val coroutineScope = rememberCoroutineScope()
	val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
		val vibratorManager =
			LocalContext.current.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
		vibratorManager.defaultVibrator
	} else {
		LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
	}
	var toggling by remember { mutableStateOf(false) }

	val iconLambda: @Composable BoxScope.() -> Unit = {
		val size = with(LocalDensity.current) {
			LocalTextStyle.current.fontSize.toDp()
		}

		if (toggling) {
			CircularProgressIndicator(modifier = Modifier.size(size))
		} else if (item.icon != null) {
			when (val icon = item.icon) {
				is EmojiIcon -> Text(icon.emoji)
				is UrlIcon -> AsyncImage(
					model = icon.url,
					contentDescription = null,
					modifier = Modifier.size(size)
				)
			}
		}
	}

	CompactChip(
		label = {
			Text(item.name)
		},
		icon = if (toggling || item.icon != null) iconLambda else null,
		colors = if (item.done) {
			ChipDefaults.secondaryChipColors()
		} else {
			ChipDefaults.primaryChipColors()
		},
		enabled = !toggling,
		onClick = {
			toggling = true

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
				vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
			} else {
				vibrator.vibrate(100)
			}

			coroutineScope.launch {
				toggleItemDone()

				toggling = false
			}
		},
		modifier = Modifier
			.animateItemPlacement(
				animationSpec = tween(
					durationMillis = 200
				)
			)
	)
}