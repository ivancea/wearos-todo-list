package xyz.ivancea.todolist

import xyz.ivancea.todolist.persistence.api.ItemRepository
import xyz.ivancea.todolist.persistence.api.PersistedItem
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
class LoggerItemRepository @Inject constructor(private val repository: ItemRepository) :
	ItemRepository {

	override suspend fun getIncompleteItems(): List<PersistedItem> {
		val (value, elapsed) = measureTimedValue {
			repository.getIncompleteItems()
		}

		println("getItems(): $elapsed seconds")

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