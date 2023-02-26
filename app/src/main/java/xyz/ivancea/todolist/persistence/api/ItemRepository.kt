package xyz.ivancea.todolist.persistence.api

import android.content.Context

interface ItemRepository {
	fun getTranslatedName(context: Context): String
	suspend fun getIncompleteItems(): List<PersistedItem>
	suspend fun toggleDone(item: PersistedItem): PersistedItem
}