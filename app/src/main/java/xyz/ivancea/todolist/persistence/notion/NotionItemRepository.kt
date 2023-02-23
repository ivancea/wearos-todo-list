package xyz.ivancea.todolist.persistence.notion

import notion.api.v1.NotionClient
import notion.api.v1.model.common.Emoji
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.databases.query.filter.condition.CheckboxFilter
import notion.api.v1.model.pages.PageProperty
import xyz.ivancea.todolist.persistence.api.ItemRepository
import xyz.ivancea.todolist.persistence.api.PersistedItem

class NotionItemRepository constructor(private val data: NotionConnectionData) : ItemRepository {
	companion object {
		const val DONE_VALUE = false
	}

	override suspend fun getIncompleteItems(): List<NotionPersistedItem> {
		return NotionClient(data.integrationToken).use { client ->
			val results = client.queryDatabase(
				databaseId = data.databaseId, filter = PropertyFilter(
					property = data.booleanColumnId, checkbox = CheckboxFilter(equals = !DONE_VALUE)
				)
			)

			results.results.map { page ->
				val icon = if (page.icon is Emoji) (page.icon as Emoji).emoji else null
				val titleProperty = page.properties.values.first { it.id == data.nameColumnId }

				NotionPersistedItem(
					id = page.id,
					name = titleProperty.title?.first()?.plainText ?: "",
					emoji = icon,
					done = false
				)
			}
		}
	}

	override suspend fun toggleDone(item: PersistedItem): PersistedItem {
		val notionItem = item as NotionPersistedItem

		NotionClient(data.integrationToken).use { client ->
			client.updatePage(
				pageId = item.id,
				properties = mapOf(data.booleanColumnId to PageProperty(checkbox = item.done xor DONE_VALUE))
			)

			return notionItem.copy(done = !item.done)
		}
	}
}