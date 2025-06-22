package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectionLogHelper {
  public static String toString(Connection connection) throws SQLException {
    String clientInfo = connection.getClientInfo().entrySet().stream().map(entry -> toString(entry)).collect(Collectors.joining());
    String result = "ClientInfo: " + clientInfo + ", Identifier: " + connection.getMetaData().getIdentifierQuoteString();
    ResultSet rs = connection.createStatement().executeQuery("SELECT CURRENT_USER");
    while (rs.next()) {
      String username = rs.getString(1);
      result += ", current_user: " + username;
    }
    return result;
  }

  public static String toString(Map.Entry<?, ?> entry) {
    return "" + entry.getKey() + ": " + entry.getValue() + ", ";
  }
}
