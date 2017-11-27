package dao.impl.mysql;

import dao.interfaces.TokenDao;
import pojo.user.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Класс, реализующий интерфейс TokenDao
 * @see TokenDao
 */
public class MySQLTokenDao extends MySQLDao implements TokenDao {

    static MySQLUserDao userDao = new MySQLUserDao();

    public void create(Token token) {
        PreparedStatement stmt = null;
        Connection con = getConnection();

        try {
            stmt = con.prepareStatement("UPDATE users SET uuid = ?, delete_date = ? " +
                    "WHERE user_id = ?");
            stmt.setString(1, token.getUuid());
            stmt.setDate(2, new java.sql.Date(token.getDeleteDate().getTime()));
            stmt.setInt(3, token.getUser().getUserId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Token readToken(String uuid) {
        String sql = "SELECT * FROM users WHERE uuid = ?";
        User user = null;
        Token token = new Token();
        PreparedStatement pstm = null;
        Connection con = getConnection();
        try {
            pstm = con.prepareStatement(sql);
            pstm.setString(1, uuid);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                user = userDao.getUser(rs.getInt("user_id"));
                token.setUuid(uuid);
                token.setDeleteDate(rs.getDate("delete_date"));
                token.setUser(user);
            }
            else {token=null;}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstm.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return token;
    }

    public void deleteUser(Token token) {
        PreparedStatement stmt = null;
        Connection con = getConnection();
        try {
            stmt = con.prepareStatement("DELETE FROM users WHERE uuid = ?");
            stmt.setString(1, token.getUuid());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteToken(Token token) {
        PreparedStatement stmt = null;
        Connection con = getConnection();

        try {
            stmt = con.prepareStatement("UPDATE users SET uuid =  NULL, delete_date=NULL " +
                    "WHERE uuid = ?");
            stmt.setString(1, token.getUuid());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteToken(Date date) {
        PreparedStatement stmt = null;
        Connection con = getConnection();

        try {
            stmt = con.prepareStatement("DELETE FROM users  " +
                    "WHERE delete_date<?");
            stmt.setDate(1, new java.sql.Date(date.getTime()));
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User readUser(String uuid) {
        String sql = "SELECT * FROM users WHERE uuid = ?";
        User user = null;
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, uuid);
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                userDao.getUser(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stm.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

}
