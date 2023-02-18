package xyz.ivancea.todolist

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.ivancea.todolist.dtos.DatabaseData
import javax.inject.Inject


class TodoListStorage @Inject constructor(@ApplicationContext val context: Context) {
    fun getToken(): String {
        return context.getString(R.string.notion_api_token)
    }

    fun getDatabaseData(): DatabaseData {
        return DatabaseData(
            databaseId = context.getString(R.string.notion_database_id),
            nameColumnId = context.getString(R.string.notion_database_name_column_id),
            booleanColumnId = context.getString(R.string.notion_database_boolean_column_id)
        )
    }
}