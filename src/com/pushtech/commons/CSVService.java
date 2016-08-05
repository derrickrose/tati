package com.pushtech.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CSVService {

   public CSVService() {
      super();
   }

   private void createCSV(PrintWriter pw, String fileName) throws FileNotFoundException {
      pw.write(fileName);
   }

   private String getProductLineForCSV(Product product, String separator) {
      String data = product.getId() + separator;
      data += product.getName() + separator;
      data += product.getLink() + separator;
      data += product.getDescription() + separator;
      data += product.getKeyWord() + separator;
      data += product.getPrice() + separator;
      data += product.getShippingCost() + separator;
      data += product.getShippingDelay() + separator;
      data += product.getBrand() + separator;
      data += product.getCategory() + separator;
      data += product.getQuantity();
      data += "\n";
      return data;
   }

   public void buildCSV(ArrayList<Product> products, String separator) throws FileNotFoundException {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd hh:mm");
      Date date = new Date();
      String path = new File("").getAbsolutePath();
      System.out.println(path);
      // String sfile = path + "/alcodistributions" + dateFormat.format(date) + ".csv";
      String sfile2 = "/home/workdev/Bureau/shared/alco/ete/promotionDEte/" + dateFormat.format(date) + ".csv";
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sfile2));

      PrintWriter pw = new PrintWriter(out);
      pw.write("productId;Name;Link;Description;Key word;Price HT;Shipping Cost HT;Shipping Delay;Brand;Category;Quantity\n");

      for (Product p : products) {
         createCSV(pw, getProductLineForCSV(p, separator));
      }
      pw.flush();
      pw.close();
   }

}
