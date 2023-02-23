package xyz.ivancea.todolist

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.ivancea.todolist.persistence.notion.NotionConnectionData
import javax.inject.Inject


class TodoListStorage @Inject constructor(@ApplicationContext val context: Context) {
	private val gson = Gson()

	fun getPersistenceType(): String {
		return NotionConnectionData.TYPE
	}

	fun getDatabaseData(): String {
		return gson.toJson(
			NotionConnectionData(
				integrationToken = context.getString(R.string.notion_api_token),
				databaseId = context.getString(R.string.notion_database_id),
				nameColumnId = context.getString(R.string.notion_database_name_column_id),
				booleanColumnId = context.getString(R.string.notion_database_boolean_column_id)
			)
		)
	}
}