package xyz.ivancea.todolist

import android.content.Context
import android.util.Log
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue
import xyz.ivancea.todolist.persistence.api.ItemRepository
import xyz.ivancea.todolist.persistence.api.PersistedItem

@OptIn(ExperimentalTime::class)
class LoggerItemRepository @Inject constructor(private val repository: ItemRepository) :
	ItemRepository {
	
	override fun getTranslatedName(context: Context): String {
		return repository.getTranslatedName(context)
	}

	override suspend fun getIncompleteItems(): List<PersistedItem> {
		val (value, elapsed) = measureTimedValue {
			repository.getIncompleteItems()
		}

		Log.d("LoggerItemRepository.getItems", "$elapsed seconds")

		return value
	}

	override suspend fun toggleDone(item: PersistedItem): PersistedItem {
		val (value, elapsed) = measureTimedValue {
			repository.toggleDone(item)
		}

		println("markAsDone(): $elapsed seconds")

		return value
	}
}