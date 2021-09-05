package ex12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

public class Sql12 {

  //sanitize only acctNum
  public ResultSet getUser(HttpServletRequest req)
      throws SQLException {
    String sql;
    Connection connection = getJDBCConnection();
    Statement statement;
    String acctNum = req.getParameter("accNum");
    String userid = req.getParameter("userid");
    String name = req.getParameter("name");
    if (userid.equals("admin")) {
      statement = connection.createStatement();
    } else {
      statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }
    userid = userid.toLowerCase();
    if (System.getProperty("os.name").contains("Win")) {
      sql = "SELECT TOP 1 first_name FROM user_data WHERE name = '"
          + name + "'";
    } else {
      sql = "SELECT first_name FROM user_data WHERE userid = '"
          + userid + "'";
    }
    sql += " AND account = '" + acctNum + "'";
    return statement.executeQuery(sql);
  }

  private Connection getJDBCConnection() {
    try {
      return DriverManager.getConnection("jdbc:sqlserver://myserver.database.windows.net:1433;");
    } catch (SQLException exception) {
      return null;
    }
  }

}
