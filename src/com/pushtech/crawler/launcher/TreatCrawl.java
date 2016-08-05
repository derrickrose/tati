package com.pushtech.crawler.launcher;

import static com.pushtech.crawler.launcher.CrawlListing.getNextPageLink;
import static com.pushtech.crawler.logging.LoggingHelper.logger;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;
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
public class TreatCrawl {
   public TreatCrawl(String url) {
      try {

         ArrayList<Product> products = new ArrayList<Product>();

         Page page = null;

         try {

            ArrayList<String> alllisting = getAllListing(url);
            for (String listing : alllisting) {
               boolean continueCrawl = true;
               String rayon = listing;
               while (continueCrawl) {

                  page = getPageFromUrl(rayon, EngineContext.MethodType.GET_METHOD);

                  if (PageType.isProductPage(page)) {
                     Product product = new CrawlOffer().doAction(page);
                     products.add(product);
                     continueCrawl = false;
                  } else if (PageType.isListingPage(page)) {
                     int indexProduit = 0;
                     for (String link : CrawlListing.getProductLinks(page)) {
                        logger.debug("-------------------- Product n* " + indexProduit + " --------------------");

                        Product product = new Product();
                        logger.debug("Link : " + link);
                        String productId = getIdFromLink(link);
                        logger.debug("Product Id :" + productId);
                        // if(Persistance.lireEnBase()){
                        // continue;
                        // }

                        try {
                           Page productPage = getPageFromUrl(link, EngineContext.MethodType.GET_METHOD);
                           product = new CrawlOffer().doAction(productPage);
                           product.setLink(link);
                           product.setId(productId);

                           // products.add(product);
                           // Persistance.sauverEnBase(product);
                           // System.out.println("-------------");

                           DAOFactory daoFactory = new DataBaseDAO().getFactoryInstance();
                           AbstractDAOEntity daoEntity = new ProductDAO(daoFactory);
                           daoEntity.updateEntity(product);

                           // System.out.println("-------------");
                           indexProduit++;
                           // break;
                        } catch (Exception e) {
                           logger.error("Error =>>> treat product failed");
                        }

                     }
                     rayon = getNextPageLink(page.getDoc());
                     continueCrawl = rayon != null ? true : false;
                  } else continueCrawl = false;
               }
            }
         } catch (Exception e) {

         }

         // CSVService csvService = new CSVService();
         // csvService.buildCSV(products, ";");

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
      }
   }

   private static ArrayList<String> getAllListing(String link) {
      ArrayList<String> listes = new ArrayList<String>();
      Page productPage = getPageFromUrl(link, EngineContext.MethodType.GET_METHOD);
      Document doc = productPage.getDoc();

      Elements elts = doc.select(Selectors.ALL_LISTING);
      if (elts.size() > 0) {
         for (org.jsoup.nodes.Element data : elts) {
            logger.debug("Url :" + data.attr("href"));
            listes.add(cleanPath(data.attr("href")));
         }
      }
      return listes;
   }

   private static String cleanPath(String path) {
      if (path == null) return null;
      path = path.replace("" + (char) 201, "%C3%89").replace(" ", "%20").replace("" + (char) 232, "%C3%A8");
      path = path.replace("" + ((char) 96), "%60").replace("" + ((char) 233), "%C3%A9").replace("" + ((char) 146), "%E2%80%99");
      if (!StringUtils.startsWith(path, "http:")) {
         path = "https://www.wordans.fr/" + path;

      }
      // try {
      // path = URIUtil.encodeQuery(URIUtil.decode(path));
      // } catch (URIException e) {
      // // TODO Auto-generated catch block
      // // return path;
      // System.out.println("tsssssssss mety ttttttttttttt");
      // e.printStackTrace();
      // }
      return path;
   }

   private static Page getPageFromUrl(final String url, EngineContext.MethodType methodeType) {
      Page page = null;
      HttpResponse response = null;
      response = ConnectionHandler.getResponse(url, null, null, methodeType);
      page = (Page) ParserFactory.getAppropriateParsingTemplate(response).parse(url, response, null);
      return page;
   }

   private static String getIdFromLink(String url) {
      String id = null;
      if (url.contains("articulo")) {
         id = url.substring(url.indexOf("articulo/") + "articulo/".length());
         id = id.substring(0, id.indexOf("/"));
      }
      logger.debug("Id : " + id);
      return id;
   }
}
