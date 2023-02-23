package xyz.ivancea.todolist.persistence.api

interface PersistedItem {
	// The unique ID of the item
	val id: String

	// The name to be shown to the user
	val name: String

	// An emoji representing the item
	val emoji: String?

	// The done state of the item
	val done: Boolean
}