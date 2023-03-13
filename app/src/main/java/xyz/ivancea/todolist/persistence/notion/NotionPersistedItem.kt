package xyz.ivancea.todolist.persistence.notion

import xyz.ivancea.todolist.persistence.api.Icon
import xyz.ivancea.todolist.persistence.api.PersistedItem

data class NotionPersistedItem(
	override val id: String,
	override val name: String,
	override val icon: Icon? = null,
	override val done: Boolean
) : PersistedItem
