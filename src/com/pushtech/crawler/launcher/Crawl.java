package com.pushtech.crawler.launcher;

import static com.pushtech.crawler.launcher.CrawlListing.getNextPageLink;
import static com.pushtech.crawler.logging.LoggingHelper.logger;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.http.HttpResponse;

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

      headers.put("Host", "www.decosdumonde.com");
      headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");

      response = ConnectionHandler.getResponse(url, null, headers, methodeType);
      page = (Page) ParserFactory.getAppropriateParsingTemplate(response).parse(url, response, null);

      return page;
   }

   private void offerCrawling(Page page, String productPath) {
      Product product = new CrawlOffer().doAction(page);
      System.out.println("Link : " + productPath);
      System.out.println("Id : " + product.getId());

      product.setLink(productPath);
      DAOFactory daoFactory = new DataBaseDAO().getFactoryInstance();
      AbstractDAOEntity daoEntity = new ProductDAO(daoFactory);
      daoEntity.updateEntity(product);
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
