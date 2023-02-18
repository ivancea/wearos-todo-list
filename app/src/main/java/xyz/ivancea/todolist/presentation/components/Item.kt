package xyz.ivancea.todolist.presentation.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.CompactChip
import androidx.wear.compose.material.Text
import xyz.ivancea.todolist.dtos.DatabaseItem


@Composable
fun Item(
    item: DatabaseItem,
    done: Boolean,
    modifier: Modifier = Modifier,
    onDone: () -> Unit
) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            LocalContext.current.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    CompactChip(
        label = {
            Text(item.name)
        },
        enabled = !done,
        onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            } else {
                vibrator.vibrate(100)
            }

            onDone()
        },
        modifier = modifier
    )
}


@Preview
@Composable
fun ItemPreview() {
    Item(DatabaseItem(id = "abc", name = "Item 1"), false) {}
}