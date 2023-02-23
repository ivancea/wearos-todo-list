package xyz.ivancea.todolist.persistence.api

interface ItemRepository {
	suspend fun getIncompleteItems(): List<PersistedItem>
	suspend fun toggleDone(item: PersistedItem): PersistedItem
}