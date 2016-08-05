package com.pushtech.crawler.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pushtech.commons.Product;

public class Persistance {

   public static void sauverEnBase(Product produit) {

      // Information d'acc�s � la base de donn�es
      // String url = "jdbc:mysql://localhost/crawl";
      String url = "jdbc:mysql://Devworkit-005/crawl";
      String login = "workdev";
      String passwd = "javdev2";
      Connection cn = null;
      Statement st = null;

      try {

         // Etape 1 : Chargement du driver
         Class.forName("com.mysql.jdbc.Driver");

         // Etape 2 : r�cup�ration de la connexion
         cn = DriverManager.getConnection(url, login, passwd);

         // Etape 3 : Cr�ation d'un statement
         Date date = new Date();
         SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
         st = cn.createStatement();
         String request = "'" + produit.getId() + "','" + produit.getName() + "',";
         request += "'" + produit.getLink() + "','" + produit.getImage() + "','" + produit.getDescription() + "',";
         request += "'" + produit.getKeyWord() + "'," + produit.getPrice();
         request += "," + produit.getShippingCost() + "," + produit.getShippingDelay();
         request += ",'" + produit.getBrand() + "','" + produit.getCategory() + "'";
         request += "," + produit.getQuantity() + ",'" + df.format(date) + "'";

         String sql = "INSERT INTO `alcodistribution` VALUES (" + request + ")";

         // Etape 4 : ex�cution requ�te
         st.executeUpdate(sql);

         // Si r�cup donn�es alors �tapes 5 (parcours Resultset)

      } catch (SQLException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         // TODO: handle exception
         e.printStackTrace();
      } finally {
         try {
            // Etape 6 : lib�rer ressources de la m�moire.
            cn.close();
            st.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }

   public static boolean lireEnBase(String productId) {

      String url = "jdbc:mysql://localhost/crawl";
      String login = "root";
      String passwd = "";
      Connection cn = null;
      Statement st = null;
      ResultSet rs = null;

      try {

         Class.forName("com.mysql.jdbc.Driver");

         cn = DriverManager.getConnection(url, login, passwd);

         st = cn.createStatement();

         String sql = "SELECT count(*) as isa FROM `alcodistribution` WHERE productId LIKE '%" + productId + "%'";

         rs = st.executeQuery(sql);
         while (rs.next()) {
            if (rs.getInt("isa") > 0) {
               System.out.println("L'offre existe déjà dans la base");
               cn.close();
               st.close();
               return true;
            }
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } finally {
         try {
            // Etape 6 : lib�rer ressources de la m�moire.
            cn.close();
            st.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      return false;
   }
}
