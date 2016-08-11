package com.pushtech.crawler.serialization;

import static com.pushtech.crawler.logging.LoggingHelper.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.pushtech.commons.Product;
import com.pushtech.crawler.launcher.DataBaseUpdaterHelper;
import com.pushtech.crawler.launcher.DataBaseUpdaterHelper.UpdateWay;

public class ProductDAO extends AbstractDAOEntity {

   private static final String TABLE_NAME = "tati";
   private static final String INSERT_REQUEST = initInsertRequest();
   private static final String READ_REQUEST = initReadRequest();
   private static final String SEARCH_REQUEST = initSearchRequest();
   private static final String UPDATE_REQUEST = initUpdateRequest();

   private DAOFactory daoFactory = null;

   public ProductDAO(DAOFactory daoFactory) {
      this.daoFactory = daoFactory;
   }

   public Product searchEntity(Product siteProduct) {
      return searchEntity(siteProduct.getId());
   }

   @Override
   public Product searchEntity(String entityIdentifier) {
      Product product = null;
      Connection connection = null;
      PreparedStatement searchStatement = null;
      ResultSet resultSet = null;
      try {
         connection = daoFactory.getConnection();
         searchStatement = initPreparedRequest(connection, SEARCH_REQUEST, false, entityIdentifier);
         resultSet = searchStatement.executeQuery();
         while (resultSet.next()) {
            product = mapper(resultSet);
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         DAOUtilities.closeAll(resultSet, searchStatement, connection);
      }

      return product;
   }

   @Override
   public int saveEntity(Product product) {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      int status = 0;
      try {
         connection = daoFactory.getConnection();
         preparedStatement = initPreparedRequest(connection, INSERT_REQUEST, true, product.getId(), validate(product.getName()), validate(product.getLink()), validate(product.getImage()), validate(product.getDescription()), validate(product.getKeyWord()), product.getPrice(), product.getShippingCost(), product.getShippingDelay(), validate(product.getBrand()), validate(product.getCategory()), product.getQuantity(), getNowDate(), product.getParentId(), validate(product.getColorName()), validate(product.getSizeName()));
         status = preparedStatement.executeUpdate();
         if (status == 0) {
            logger.error("Save product failed : " + product.getId() + " - " + product.getLink());
         } else logger.debug("Save product passed");
      } catch (Exception e) {
         logger.error(e.getMessage());
      }

      finally {
         DAOUtilities.closeAll(null, preparedStatement, connection);
      }

      return status;
   }

   // @Override
   // public void setEntity(AbstractDAOEntity abstractDAOEntity) {
   // // TODO Auto-generated method stub
   //
   // }

   public AbstractDAOEntity getAbstractDAOEntity(DAOFactory DAOFactoryInstance) {
      // TODO Auto-generated method stub
      return null;
   }

   public static PreparedStatement initPreparedRequest(Connection connection, String request, boolean returnGeneratedKeys, Object... objects) throws SQLException {
      PreparedStatement preparedStatement = connection.prepareStatement(request, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
      for (int i = 0; i < objects.length; i++) {
         preparedStatement.setObject(i + 1, objects[i]);
      }
      return preparedStatement;
   }

   private Product mapper(ResultSet resultSet) throws SQLException {
      Product product = new Product();
      product.setId(resultSet.getString("productId"));
      // product.setParentId(resultSet.getString( "parentid" ) );
      product.setName(resultSet.getString("Name"));
      product.setLink(resultSet.getString("link"));
      product.setDescription(resultSet.getString("description"));
      product.setBrand(resultSet.getString("brand"));
      product.setCategory(resultSet.getString("category"));
      product.setImage(resultSet.getString("image"));
      product.setKeyWord(resultSet.getString("motclef"));
      product.setPrice(resultSet.getFloat("price"));
      product.setShippingCost(resultSet.getFloat("shippingCost"));
      product.setShippingDelay(resultSet.getInt("shippingDealy"));
      product.setQuantity(resultSet.getInt("quantity"));
      product.setUpdated(resultSet.getString("update_time"));//
      product.setParentId(resultSet.getString("parent_Id"));//
      product.setColorName(resultSet.getString("color_name"));//
      product.setSizeName(resultSet.getString("size_name"));//
      return product;
   }

   @Override
   public int setEntity(Product product) {
      return 0;
   }

   private static String initInsertRequest() {
      String insertRequest = "INSERT INTO " + TABLE_NAME + " ";
      insertRequest += "(productId, Name, link, image, description, motclef, price, shippingCost, shippingDealy, brand, category, quantity, update_time,parent_Id,color_name,size_name)";

      insertRequest += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      return insertRequest;
   }

   private static String initUpdateRequest() {
      String updateRequest = "UPDATE " + TABLE_NAME + " SET";
      updateRequest += "  " + "Name = ?";
      updateRequest += " , " + "link = ?";
      updateRequest += " , " + "image = ?";
      updateRequest += " , " + "description = ?";
      updateRequest += " , " + "motclef = ?";
      updateRequest += " , " + "price = ?";
      updateRequest += " , " + "shippingCost = ?";
      updateRequest += " , " + "shippingDealy = ?";
      updateRequest += " , " + "brand = ?";
      updateRequest += " , " + "category = ?";
      updateRequest += " , " + "quantity = ?";
      updateRequest += " , " + "update_time = ?";
      updateRequest += " , " + "parent_id = ?";
      updateRequest += " , " + "color_name = ?";
      updateRequest += " , " + "size_name = ?";
      updateRequest += " WHERE productId = ? ";
      return updateRequest;
   }

   @Override
   public int updateEntity(Product siteProduct) {
      Connection connection = null;
      PreparedStatement updateStatement = null;
      int status = 0;
      Product databaseProduct = null;
      Product product;
      try {
         databaseProduct = searchEntity(siteProduct);
         if (databaseProduct != null) {
            logger.debug("Existing product");
            DataBaseUpdaterHelper dbuh = DataBaseUpdaterHelper.getUpdaterMode(true, UpdateWay.SIMPLE_UPDATE);
            product = dbuh.updateProduct(siteProduct, databaseProduct);
            connection = daoFactory.getConnection();
            updateStatement = initPreparedRequest(connection, UPDATE_REQUEST, true, product.getName(), product.getLink(), product.getImage(), product.getDescription(), product.getKeyWord(), product.getPrice(), product.getShippingCost(), product.getShippingDelay(), product.getBrand(), product.getCategory(), product.getQuantity(), getNowDate(), product.getParentId(), validate(product.getColorName()), validate(product.getSizeName()), product.getId());
            status = updateStatement.executeUpdate();
            if (status != 0) logger.debug("Update passed");
            else logger.error("Update failed : " + product.getId() + " - " + product.getLink());
         } else {
            logger.debug("New product");
            saveEntity(siteProduct);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DAOUtilities.closeAll(null, updateStatement, connection);
      }
      return status;
   }

   private static String initSearchRequest() {
      String searchRequest = "SELECT * FROM " + TABLE_NAME + " WHERE productId = ? ";
      return searchRequest;
   }

   private static String initReadRequest() {
      final String readRequest = "SELECT * FROM " + TABLE_NAME + " LIMIT 10";
      return readRequest;
   }

   private String getNowDate() {
      DateTime dt = new DateTime();
      DateTimeFormatter df = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
      return dt.toString(df);
   }

   private String validate(String fieldValue) {
      return (fieldValue == null) ? "" : fieldValue;
   }

}
