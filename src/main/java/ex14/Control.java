package ex14;

import static ex14.Anonymous.dbConnection;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

public class Control {

  private Random rand = new Random();
  private String id;

  void bad(HttpServletRequest req) throws SQLException {
    Statement statement = dbConnection.createStatement();
    statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" +
        req.getParameterValues("userIds")[0] + "'");
  }

  void bad2(HttpServletRequest req) throws SQLException {
    int max = 0;
    String maxIdUser = "";
    for (String userId : req.getParameterValues("userIds")) {
      if (Integer.parseInt(userId.substring(0, 3)) > max) {
        maxIdUser = "user_" + userId;
      }
    }
    Statement statement = dbConnection.createStatement();
    statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" +
        maxIdUser + "'");
  }

  void updateId(HttpServletRequest req) {
    this.id = req.getParameter("userId");
  }

  void bad3(HttpServletRequest req) throws SQLException {
    Statement statement = dbConnection.createStatement();
    if (rand.nextBoolean()) {
      String id = "id_" + req.getParameter("userId");
    }
    statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" +
        id + "'");
  }

  void bad4(HttpServletRequest req) throws SQLException {
    Statement statement = dbConnection.createStatement();
    String id = "id_" + req.getParameter("userId");
    statement.executeQuery("SELECT first_name FROM user_data WHERE userid = '" +
        this.id + "'");
  }
}
