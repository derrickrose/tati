package com.pushtech.crawler.serialization;

import static com.pushtech.crawler.logging.LoggingHelper.logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.pushtech.crawler.ihm.FormHandler;

public class DataBaseDAO extends DAOFactory {

   private static final String MYSQL_PROPERTIES_PATH = "com/pushtech/crawler/serialization/database.mysql.properties";
   private static final String ORACLE_PROPERTIES_PATH = "";// lol
   private static final String DRIVER_PROPERTY_FIELD = "driver";
   private static final String URL_PROPERTY_FIELD = "url";
   private static final String LOGIN_PROPERTY_FIELD = "login";
   private static final String PASSWORD_PROPERTY_FIELD = "password";
   private String driver = null;
   private String url = null;
   private String login = null;
   private String password = null;

   private DataBaseDAO(String driver, String url, String login, String password) {
      loadDriver(driver);
      this.driver = driver;
      this.url = url;
      this.login = login;
      this.password = password;
   }

   public DataBaseDAO() {

   }

   // public static DataBaseDAOFactory getDefaultInstance() {
   // return new DataBaseDAOFactory();
   // }

   private void loadDriver(String driver) {
      try {
         Class.forName(driver);
      } catch (ClassNotFoundException e) {
         // TODO Auto-generated catch block
         logger.error(e.getMessage());
      }
   }

   private DAOFactory buildDAOInstance() {
      // System.out.println("buid ra ty ato ein");

      // a changer par un pattern

      Properties properties = new Properties();
      String driver = null;
      String url = FormHandler.getForm().getDataBaseLink();
      String login = FormHandler.getForm().getDataBaseUser();
      String password = FormHandler.getForm().getUserPassWord();
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      InputStream mysqlPropertiesFile = classLoader.getResourceAsStream(MYSQL_PROPERTIES_PATH);

      try {
         properties.load(mysqlPropertiesFile);
         driver = properties.getProperty(DRIVER_PROPERTY_FIELD);
         url = StringUtils.isNotBlank(url) ? url : properties.getProperty(URL_PROPERTY_FIELD);
         login = StringUtils.isNotBlank(login) ? login : properties.getProperty(LOGIN_PROPERTY_FIELD);
         password = StringUtils.isNotBlank(password) ? password : properties.getProperty(PASSWORD_PROPERTY_FIELD);
      } catch (Exception e) {
         e.printStackTrace();
      }

      System.err.println("=======>=>+>+>+ " + url + " " + login + " " + password);

      return new DataBaseDAO(driver, url, login, password);
   }

   @Override
   public DAOFactory getFactoryInstance() {
      return buildDAOInstance();
   }

   public ProductDAO getProductDAO() {
      return new ProductDAO(this);
   }

   @Override
   public Connection getConnection() throws SQLException {
      return DriverManager.getConnection(url, login, password);
   }

}
