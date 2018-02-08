package ru.otus.java.hw9.db;

public enum QueryStorage {
    CREATE_TABLE_USER("CREATE TABLE IF NOT EXISTS user (id bigint(20) NOT NULL auto_increment primary key," +
        "name varchar(255),age int(3))"),
    SAVE_DATA("INSERT INTO user VALUES (%s);"),
    DELETE_TABLE_USER("DROP TABLE IF EXISTS user"),
    GET_DATA_BY_ID("SELECT * FROM user WHERE id=");

    private final String query;

    QueryStorage(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
