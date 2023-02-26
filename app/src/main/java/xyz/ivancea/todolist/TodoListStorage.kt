package xyz.ivancea.todolist

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.ivancea.todolist.persistence.notion.NotionConnectionData
import javax.inject.Inject

val USE_CONFIG = false

class TodoListStorage @Inject constructor(@ApplicationContext val context: Context) {
	private val gson = Gson()
	private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

	fun getPersistenceType(): String? {
		if (USE_CONFIG) {
			return NotionConnectionData.TYPE
		}

		return preferences.getString("persistence_type", null)
	}

	fun getPersistenceData(): String? {
		if (USE_CONFIG) {
			return gson.toJson(
				NotionConnectionData(
					integrationToken = context.getString(R.string.notion_api_token),
					databaseId = context.getString(R.string.notion_database_id),
					nameColumnId = context.getString(R.string.notion_database_name_column_id),
					booleanColumnId = context.getString(R.string.notion_database_boolean_column_id)
				)
			)
		}

		return preferences.getString("persistence_data", null)
	}

	fun setPersistenceData(persistenceType: String, persistenceData: String) {
		preferences.edit()
			.putString("persistence_type", persistenceType)
			.putString("persistence_data", persistenceData)
			.apply()
	}
}