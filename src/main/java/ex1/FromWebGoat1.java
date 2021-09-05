package ex1;

import java.util.Random;
import testcasesupport.IO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class FromWebGoat1 {

  private Random random;

  void bad(HttpServletRequest req) throws SQLException {

    String data = null;
    Connection dbConnection = null;
    PreparedStatement sqlStatement = null;
    try {
      if (random.nextBoolean()) {
        data = req.getParameter("one");
      } else {
        data = "guest";
      }
      dbConnection = IO.getDBConnection();
    } catch (Exception e) {
      data = "dfgdfg";
    }

    sqlStatement = dbConnection.prepareStatement(
        "insert into users (status) values ('updated') where name='" + data + "'");

    sqlStatement.execute();
  }

  void bad2(HttpServletRequest req) throws SQLException {

    String data = null;
    Connection dbConnection = null;
    PreparedStatement sqlStatement = null;
    try {
      if (random.nextBoolean()) {
        data = req.getParameter("one");
      } else if (random.nextBoolean()) {
        data = req.getParameter("two");
      }
      dbConnection = IO.getDBConnection();
    } catch (Exception e) {
      data = "dfgdfg";
    }

    sqlStatement = dbConnection.prepareStatement(
        "insert into users (status) values ('updated') where name='" + data + "'");

    sqlStatement.execute();
  }

  void bad3(HttpServletRequest req) throws SQLException {

    Connection dbConnection = null;
    PreparedStatement sqlStatement = null;
    String data = Integer.parseInt(req.getParameter("index")) == 1 ? req.getParameter("one") : req.getParameter("two");
    dbConnection = IO.getDBConnection();

    sqlStatement = dbConnection.prepareStatement(
        "insert into users (status) values ('updated') where name='" + data + "'");

    sqlStatement.execute();
  }

  void bad4(HttpServletRequest req) throws SQLException {

    Connection dbConnection = null;
    PreparedStatement sqlStatement = null;
    dbConnection = IO.getDBConnection();

    sqlStatement = dbConnection.prepareStatement(
        "insert into users (status) values ('updated') where name='" +
            (Integer.parseInt(req.getParameter("index")) == 1 ? req.getParameter("one") : req.getParameter("two")) +
            "'");

    sqlStatement.execute();
  }
}
