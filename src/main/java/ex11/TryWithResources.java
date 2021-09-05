package ex11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

public class TryWithResources {
  void bad(HttpServletRequest req) throws SQLException {

    String sql;
    String name = req.getParameter("name");
    String userid = req.getParameter("userid");
    String acctNum = req.getParameter("acctNum");
    Connection connection = getJDBCConnection();
    try (Statement statement = connection.createStatement()) {
      if (System.getProperty("os.name").contains("Win")) {
        sql = "SELECT TOP 1 first_name FROM user_data WHERE name = '"
            + name + "'";
      } else {
        sql = "SELECT first_name FROM user_data WHERE userid = '"
            + userid + "'";
      }
      sql += " AND account = '" + acctNum + "'";
      statement.executeQuery(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  String bad2(HttpServletRequest req) throws SQLException {

    String sql;
    String name = req.getParameter("name");
    String userid = req.getParameter("userid");
    String acctNum = req.getParameter("acctNum");
    Connection connection = getJDBCConnection();
    try (Statement statement = connection.createStatement()) {
      if (System.getProperty("os.name").contains("Win")) {
        sql = "SELECT TOP 1 first_name FROM user_data WHERE name = '"
            + name + "'";
      } else {
        sql = "SELECT first_name FROM user_data WHERE userid = '"
            + userid + "'";
      }
      sql += " AND account = '" + acctNum + "'";
      return statement.executeQuery(sql).getString(0);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      return "Done";
    }
  }

  private Connection getJDBCConnection() {
    try {
      return DriverManager.getConnection("jdbc:sqlserver://myserver.database.windows.net:1433;");
    } catch (SQLException exception) {
      return null;
    }
  }
}
