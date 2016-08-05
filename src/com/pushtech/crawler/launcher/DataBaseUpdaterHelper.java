/*****
 * 
 * VERY IMPORTANT
 * to set a blank field value in database, site field value must be null and force update
 * siteProductValue = null && forceUpdate = true;
 * 
 * */

package com.pushtech.crawler.launcher;

import com.pushtech.commons.Product;

public class DataBaseUpdaterHelper {
   // forceUpdate <==> to Force update product in the data base
   private boolean forceUpdate = true;
   private UpdateWay updateWay;

   public static enum UpdateWay {
      SIMPLE_UPDATE, DELETE_THEN_UPDATE;
   }

   private String getFieldValueToSave(final String siteProductFieldValue, final String dataBaseProductFieldValue) {
      String valueToSave = siteProductFieldValue;
      if (updateWay == UpdateWay.SIMPLE_UPDATE) {
         if (forceUpdate) {
            valueToSave = siteProductFieldValue;
         }
         if (valueToSave != null && valueToSave.equals("")) {
            valueToSave = dataBaseProductFieldValue; // if blank siteProduct
            // field value we do
            // not
            // change anything
            // from
            // database
         }
         if (valueToSave == null || valueToSave.equals("null")) {
            // System.out.println("mandalo eto ");
            valueToSave = ""; // not null constraint on dataBase
         }
      }
      // System.out.println("value to save : |" + valueToSave + "|");
      return valueToSave;
   }

   // TODO may be needed one day
   private float getFieldValueToSave(final float siteProductFieldValue, final float dataBaseProductFieldValue) {
      float valueToSave = siteProductFieldValue;
      if (updateWay == UpdateWay.SIMPLE_UPDATE) {
         if (forceUpdate) {
            valueToSave = siteProductFieldValue;
         }
         if (valueToSave == -1f) {
            valueToSave = dataBaseProductFieldValue; // if -1f siteProduct
            // field
            // value
            // we do not
            // change anything from
            // database
         }
      }

      return valueToSave;
   }

   private int getFieldValueToSave(final int siteProductFieldValue, final int dataBaseProductFieldValue) {
      int valueToSave = siteProductFieldValue;
      if (updateWay == UpdateWay.SIMPLE_UPDATE) {
         if (forceUpdate) {
            valueToSave = siteProductFieldValue;
         }
         if (valueToSave == 0) {
            valueToSave = dataBaseProductFieldValue; // if 0 siteProduct
            // field
            // value
            // we do not
            // change anything
            // from
            // database
         }
      }

      return valueToSave;
   }

   private DataBaseUpdaterHelper(boolean forceUpdate, UpdateWay updateWay) {
      this.forceUpdate = forceUpdate;
      this.updateWay = updateWay;
   }

   public static DataBaseUpdaterHelper getUpdaterMode(boolean forceUpdate, UpdateWay updateWay) {
      return new DataBaseUpdaterHelper(forceUpdate, updateWay);
   }

   public Product updateProduct(final Product siteProduct, final Product databaseProduct) {
      Product product = new Product();
      product = siteProduct;
      product.setName(getFieldValueToSave(siteProduct.getName(), databaseProduct.getName()));

      product.setLink(getFieldValueToSave(siteProduct.getLink(), databaseProduct.getLink()));
      product.setDescription(getFieldValueToSave(siteProduct.getDescription(), databaseProduct.getDescription()));
      product.setBrand(getFieldValueToSave(siteProduct.getBrand(), databaseProduct.getBrand()));
      product.setCategory(getFieldValueToSave(siteProduct.getCategory(), databaseProduct.getCategory()));
      product.setImage(getFieldValueToSave(siteProduct.getImage(), databaseProduct.getImage()));
      // System.out.println("product.getImage() " + product.getImage());
      // System.out.println("siteProduct.getKeyWord() " + siteProduct.getKeyWord());
      // System.out.println(" databaseProduct.getKeyWord()" + databaseProduct.getKeyWord());
      product.setKeyWord(getFieldValueToSave(siteProduct.getKeyWord(), databaseProduct.getKeyWord()));
      // System.out.println("product.getKeyWord() " + product.getKeyWord());
      product.setPrice(getFieldValueToSave(siteProduct.getPrice(), databaseProduct.getPrice()));
      product.setShippingCost(getFieldValueToSave(siteProduct.getShippingCost(), databaseProduct.getShippingCost()));
      product.setShippingDelay(getFieldValueToSave(siteProduct.getShippingDelay(), databaseProduct.getShippingDelay()));
      product.setQuantity(getFieldValueToSave(siteProduct.getQuantity(), databaseProduct.getQuantity()));
      // System.out.println("3 : " + "change made");
      return product;
   }

}