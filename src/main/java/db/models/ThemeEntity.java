package db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "themes")
public class ThemeEntity {
    public static final String NAME_COLUMN = "name";
    @DatabaseField(generatedId = true)
    private long themeId;

    @DatabaseField(canBeNull = false)
    private String name;

    public ThemeEntity() {}

    public ThemeEntity(String name) {
        this.name = name;
    }
}
