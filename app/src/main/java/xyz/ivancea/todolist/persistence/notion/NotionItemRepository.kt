package xyz.ivancea.todolist.persistence.notion

import android.content.Context
import notion.api.v1.NotionClient
import notion.api.v1.model.common.Emoji
import notion.api.v1.model.common.File
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.databases.query.filter.condition.CheckboxFilter
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PageProperty
import xyz.ivancea.todolist.R
import xyz.ivancea.todolist.persistence.api.EmojiIcon
import xyz.ivancea.todolist.persistence.api.Icon
import xyz.ivancea.todolist.persistence.api.ItemRepository
import xyz.ivancea.todolist.persistence.api.PersistedItem
import xyz.ivancea.todolist.persistence.api.UrlIcon

class NotionItemRepository constructor(private val data: NotionConnectionData) : ItemRepository {
	companion object {
		const val DONE_VALUE = false
	}

	override fun getTranslatedName(context: Context): String {
		return context.getString(R.string.picker__notion)
	}

	override suspend fun getIncompleteItems(): List<NotionPersistedItem> {
		return NotionClient(data.integrationToken).use { client ->
			val results = client.queryDatabase(
				databaseId = data.databaseId, filter = PropertyFilter(
					property = data.booleanColumnId, checkbox = CheckboxFilter(equals = !DONE_VALUE)
				)
			)

			results.results.map { page ->
				val titleProperty = page.properties.values.first { it.id == data.nameColumnId }

				NotionPersistedItem(
					id = page.id,
					name = titleProperty.title?.first()?.plainText ?: "",
					icon = getIcon(page),
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

	private fun getIcon(page: Page): Icon? {
		when (val icon = page.icon) {
			is Emoji -> {
				val emoji = icon.emoji

				if (emoji != null) {
					return EmojiIcon(emoji)
				}
			}
			is File -> {
				val url = icon.external?.url

				if (url != null) {
					return UrlIcon(url)
				}
			}
		}
		
		return null
	}
}