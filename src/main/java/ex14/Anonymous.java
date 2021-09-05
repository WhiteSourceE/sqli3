package ex14;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

class Anonymous {

  interface Sql {

    String getSql();

    String addPrefix(String name);
  }

  public static HttpServletRequest req;
  String input = req == null ? "" : req.getParameter("user_id");
  public static Connection dbConnection;

  public void bad() throws SQLException {
    Sql sql = new Sql() {
      public String getSql() {
        return "safe" + input;
      }

      @Override
      public String addPrefix(String name) {
        return "name_" + name;
      }
    };
    Statement statement = dbConnection.createStatement();
    statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" + sql.getSql() + "'");
  }

  public void bad2() throws SQLException {
    Sql sql = new Sql() {
      public String getSql() {
        return "safe" + input;
      }

      public String addPrefix(String name) {
        return "name_" + name;
      }
    };
    Statement statement = dbConnection.createStatement();
    statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" +
        sql.addPrefix(req.getParameter("user_id")) + "' AND name = '"
        + sql.addPrefix("guest") + "'");
  }
}
