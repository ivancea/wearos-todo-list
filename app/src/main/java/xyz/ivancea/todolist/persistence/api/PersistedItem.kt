package xyz.ivancea.todolist.persistence.api

interface PersistedItem {
	// The unique ID of the item
	val id: String

	// The name to be shown to the user
	val name: String

	// An icon representing the item
	val icon: Icon?

	// The done state of the item
	val done: Boolean
}