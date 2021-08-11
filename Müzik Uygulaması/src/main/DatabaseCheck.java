package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCheck {
    private Connection connection;
    private DBHelper helper = new DBHelper();
    private Statement stmt;
    private ResultSet rs;

    public DatabaseCheck() {
        try {
            connection = helper.getConnection();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        try {
            stmt = connection.createStatement();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }

    public boolean ifUserExist(String userName, String password) throws SQLException {
        String query = String.format("SELECT UserName, Password FROM users WHERE UserName='%s' AND Password='%s';", userName, password);
        rs = stmt.executeQuery(query);

        return rs.next();
    }

    public boolean isPremium(String userName) throws SQLException {
        String query = String.format("SELECT Subscription FROM users WHERE UserName='%s';", userName);
        rs = stmt.executeQuery(query);

        while (rs.next()) {
            if (rs.getString("Subscription").equals("Premium")) {
                return true;
            }
        }
        return false;
    }
}
