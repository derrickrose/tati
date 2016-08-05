package com.pushtech.crawler.serialization;

import java.sql.SQLException;
import static com.pushtech.crawler.logging.LoggingHelper.logger;;
public class DAOUtilities {

   public static void closeAll(java.sql.ResultSet resultSet, java.sql.PreparedStatement searchStatement, java.sql.Connection connection) {

      if (resultSet != null) try {
         resultSet.close();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         logger.error(e.getMessage());
      }

      if (searchStatement != null) try {
         searchStatement.close();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
    	  logger.error(e.getMessage());
      }

      if (connection != null) try {
         connection.close();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
    	  logger.error(e.getMessage());
      }

      logger.debug("Res, state, connex nicely closed");

   }

}
