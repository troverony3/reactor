package bd;
import com.company.Reactor;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class DBProvider {
    DBBuilder builder;
    Connection conn;
    Statement stat;

    public DBProvider(DBBuilder builder) {
        this.builder = builder;
    }

    public void connect() throws SQLException {
        String FullPath = "C:\\Users\\Admin\\Downloads\\" + builder.getDBName() + ".accdb";// подключиться к БД
        conn = DriverManager.getConnection("jdbc:ucanaccess://" + FullPath, "", "");
        stat = conn.createStatement();//состояние бд для этого подключения
        //Statement используется для выполнения SQL-запросов.
        // Существует три типа класса Statement,
        // которые являются как бы контейнерами
        // для выполнения SQL-выражений через установленное соединение
    }

    public void getAll(String s, List<Reactor> Re) throws SQLException {
        String SQLQuery = getQuery(s);
        stat.execute(SQLQuery);//для состояния БД выполнить SQL запрос
        ResultSet res = stat.getResultSet();//берем из БД набор ответов
        builder.handle(res, s, Re);
    }

    public void close() throws SQLException { //чтобы закончить работу
        conn.close();
    }

    private String getQuery( String s) {
        return builder.getAllQuery(s);

    }
}