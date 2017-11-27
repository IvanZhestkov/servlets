package dao.impl.mysql;

import dao.DaoFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Создает соединение с базой
 */
public class MySQLDao {

    Connection getConnection() {
        try {
            return DaoFactory.getDaoFactory(1).getConnection();
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

}
