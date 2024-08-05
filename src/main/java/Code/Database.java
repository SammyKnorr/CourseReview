package Code;

public interface Database {

    void connect();
    void close();
    void createTables();
    void createDatabase();
}
