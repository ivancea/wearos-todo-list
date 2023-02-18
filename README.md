# WearOS To-Do list

A To-Do list app for WearOS, that uses an existing Notion database as a task list.

## Build

First, configure your Notion integration:

- Create a Notion integration at https://www.notion.so/my-integrations, and grant it read and update
  access
- Add your integration to your database, by going to the database page, configuration, and "Add
  integration"
- Create the file `app/src/main/res/values/secrets.xml`, and add there the configuration:

```xml

<resources>
    <string name="notion_api_token">YOUR_NOTION_API_TOKEN</string>
    <string name="notion_database_id">YOUR_DATABASE_ID</string>
    <string name="notion_database_name_column_id">title</string>
    <string name="notion_database_boolean_column_id">YOUR_BOOLEAN_COLUMN_ID</string>
</resources>
```

* You can find the database ID in the URL of the database page, and the boolean column ID in the
  requests the page makes.

Then, you can use Android Studio to build the project.

## Usage

- Connect it with a Notion database (It will use the title and a checkbox column)
- List the tasks in your WearOS device
- Mark tasks as done. Marked tasks will remain disabled in the list until you update it

## Future roadmap

More backends may be added (Notion, GitHub, SQL... Ideas are welcome).