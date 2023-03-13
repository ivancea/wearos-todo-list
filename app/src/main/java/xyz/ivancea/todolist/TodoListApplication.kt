package xyz.ivancea.todolist;

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
class TodoListApplication : Application(), ImageLoaderFactory {
	override fun newImageLoader(): ImageLoader {
		return ImageLoader.Builder(this)
			.components {
				add(SvgDecoder.Factory())
			}
			.build()
	}
}
