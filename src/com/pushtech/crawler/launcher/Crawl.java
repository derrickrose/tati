package com.pushtech.crawler.launcher;

import static com.pushtech.crawler.launcher.CrawlListing.getNextPageLink;
import static com.pushtech.crawler.logging.LoggingHelper.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pushtech.commons.Product;
import com.pushtech.crawler.beans.Page;
import com.pushtech.crawler.connection.ConnectionHandler;
import com.pushtech.crawler.connection.EngineContext;
import com.pushtech.crawler.parsing.ParserFactory;
import com.pushtech.crawler.serialization.AbstractDAOEntity;
import com.pushtech.crawler.serialization.DAOFactory;
import com.pushtech.crawler.serialization.DataBaseDAO;
import com.pushtech.crawler.serialization.ProductDAO;

/**
 * Created by Workdev on 10/06/2016.
 */
public class Crawl {
   public Crawl(String entryPointUrl) {
      // try {
      logger.info("Begin crawl");
      Page page = null;
      String urlToConnect = entryPointUrl;
      try {
         page = getPageFromUrl(urlToConnect, EngineContext.MethodType.GET_METHOD);
         if (PageType.isProductPage(page)) {
            offerCrawling(page, urlToConnect);
         } else if (PageType.isListingPage(page)) {
            listingCrawling(page);
         }

         else if (PageType.isHomePage(page)) {
            homeCrawling(page);
         }
      } catch (Exception e) {
         SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               JOptionPane jop = new JOptionPane();
               jop.showMessageDialog(null, "Crawl failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
         });
         logger.fatal(e.getMessage());
         e.printStackTrace();
      }
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null, "Crawl ended", "Information", JOptionPane.INFORMATION_MESSAGE);
         }
      });
      logger.info("Crawl ended");
   }

   public static Page getPageFromUrl(final String url, EngineContext.MethodType methodeType) {
      Page page = null;
      HttpResponse response = null;
      HashMap<String, String> headers = new HashMap<String, String>();

      headers.put("Host", "www.tati.fr");
      headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");

      response = ConnectionHandler.getResponse(url, null, headers, methodeType);
      page = (Page) ParserFactory.getAppropriateParsingTemplate(response).parse(url, response, null);

      return page;
   }

   private void offerCrawling(Page page, String productPath) {
      Product product = new CrawlOffer().doAction(page);
      Document productPage = page.getDoc();
      System.out.println("Link : " + productPath);
      System.out.println("Id : " + product.getId());
      product.setLink(productPath);
      for (Product produit : getproducts(productPage, product)) {
         DAOFactory daoFactory = new DataBaseDAO().getFactoryInstance();
         AbstractDAOEntity daoEntity = new ProductDAO(daoFactory);
         daoEntity.updateEntity(produit);
      }

   }

   private List<Product> getproducts(Document docProduct, Product p) {
      List<Product> products = new ArrayList<Product>();
      Elements lists = docProduct.select("div.radio_input_container.size>label");
      List<String> colorlist = productColors(docProduct);
      System.out.println("Variante Color list:" + colorlist.size());
      System.out.println("Variante size list:" + lists.size());
      for (Element productElement : lists) {
         Product variantProduct = new Product();
         String strVariantSize = productElement.text();
         System.out.println("Variant size Name :" + strVariantSize);
         variantProduct.setSizeName(strVariantSize);// SIZE NAME
         variantProduct.setBrand(strVariantSize);
         variantProduct.setName(p.getName());
         variantProduct.setId(p.getId() + "-" + strVariantSize);
         variantProduct.setDescription(cleanDescription(p.getDescription()));
         variantProduct.setKeyWord(cleanDescription(p.getKeyWord()));
         variantProduct.setPrice(p.getPrice());
         variantProduct.setCategory(p.getCategory());
         variantProduct.setShippingDelay(p.getShippingDelay());
         variantProduct.setQuantity(10);
         variantProduct.setParentId(p.getId() + strVariantSize);
         variantProduct.setImage(p.getImage());
         variantProduct.setUpdated(p.getUpdated());
         variantProduct.setLink(p.getLink());
         products.add(variantProduct);

      }
      return products;
   }

   private List<String> productColors(Document docProduct) {
      List<String> products = new ArrayList<String>();
      Elements lists = docProduct.select(Selectors.PRODUCT_COLOR_VARIANT);
      for (Element p : lists) {
         products.add(p.text());
      }
      return products;
   }

   private String cleanDescription(String strDescription) {
      return strDescription.replace(";", "").replace(",", "");
   }

   private void homeCrawling(Page homePage) {
      ArrayList<String> allListing = CrawlHome.getAllListing(homePage);
      for (String listing : allListing) {
         Page listingPage = getPageFromUrl(listing, EngineContext.MethodType.GET_METHOD);
         listingCrawling(listingPage);
      }
   }

   private String listingProcess(Page listingPage) {
      int indexProduit = 0;
      for (String productPath : CrawlListing.getProductLinks(listingPage)) {
         System.out.println("-------------------- Produit n* " + indexProduit + " --------------------");

         try {
            Page productPage = getPageFromUrl(productPath, EngineContext.MethodType.GET_METHOD);
            offerCrawling(productPage, productPath);
            // System.out.println("-------------");
            indexProduit++;
            // break;
         } catch (Exception e) {
            logger.error("" + e.getMessage());
         }
      }
      return getNextPageLink(listingPage.getDoc());
   }

   private void listingCrawling(Page firstListingPage) {
      boolean continueCrawl = true;
      Page page = firstListingPage;
      String nextPageLink = null;
      while (continueCrawl) {
         nextPageLink = listingProcess(page);
         continueCrawl = nextPageLink != null ? true : false;
         if (continueCrawl) {
            page = getPageFromUrl(nextPageLink, EngineContext.MethodType.GET_METHOD);
         }
      }
   }
}
