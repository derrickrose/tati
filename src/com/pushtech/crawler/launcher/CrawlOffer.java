package com.pushtech.crawler.launcher;

import static com.pushtech.crawler.logging.LoggingHelper.logger;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pushtech.commons.Product;
import com.pushtech.commons.SpecialChar;
import com.pushtech.crawler.beans.Page;

public class CrawlOffer {
   private static final Locale CURRENT_LOCALE = Locale.FRENCH;

   public Product doAction(Page page) {

      Product product = new Product();
      final Document productPageDocument = page.getDoc();

      String name = null;
      try {
         name = getName(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setName(name);
      logger.debug("Name : " + name);

      String description = null;
      try {
         description = getDescription(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setDescription(description);
      logger.debug("Description : " + description);

      String brand = "";
      product.setBrand(brand);
      logger.debug("Brand : " + brand);

      String category = null;
      try {
         category = getCategory(productPageDocument);
         product.setCategory(category);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      logger.debug("Category : " + category);

      String image = null;
      try {
         image = getImage(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setImage(image);
      logger.debug("Image : " + image);

      float price = -1f;
      try {
         price = getPrice(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setPrice(price);
      logger.debug("Price : " + price);

      String strKeyWord = "Empty";
      try {
         strKeyWord = getKeywords(productPageDocument);
      } catch (Exception e1) {
         logger.error(e1.getMessage() + " on " + page.getUrl());
      }

      product.setKeyWord(strKeyWord);
      logger.debug("KeyWord : " + strKeyWord);

      float shippingCost = -1f;
      product.setShippingCost(shippingCost);
      logger.debug("Shipping cost : " + shippingCost);

      int shippingDelay = 0;
      shippingDelay = getShippingDelay(getShippingDelayRaw(productPageDocument));
      product.setShippingDelay(shippingDelay);
      logger.debug("Shipping delay : " + shippingDelay);

      int quantity = 10;
      // try {
      // quantity = getQuantity(productPageDocument);
      // } catch (Exception e) {
      // logger.error(e.getMessage() + " on " + page.getUrl());
      // }
      product.setQuantity(quantity);
      logger.debug("Quantity : " + quantity);

      String id = null;
      try {
         id = getProductId(productPageDocument);
      } catch (Exception e) {
         logger.error(e.getMessage() + " on " + page.getUrl());
      }
      product.setId(id);
      logger.debug("Id : " + id);

      return product;
   }

   private String getProductId(final Document productPageDocument) throws Exception {
      final Element productIdElement = productPageDocument.select(Selectors.PRODUCT_IDENTIFIER).first();
      String productIdRaw = null;
      if (productIdElement != null) {
         productIdRaw = fromAttribute(productIdElement, "value");
      }
      productIdRaw = validateField(productIdRaw, "Product Id");
      return productIdRaw;
   }

   public static String getProductIdFromLink(final String link) throws Exception {
      String productId = null;
      if (StringUtils.isNotEmpty(link)) {
         productId = productId.substring(productId.lastIndexOf("/") + 1);
         productId = productId.replaceAll("[^\\d]", "").trim();
         if (!productId.matches("\\d+")) {
            logger.error("Invalid productId : |" + productId + "| set to null");
            productId = null;
         }
      }
      return productId;
   }

   // example
   private String getName(final Document productPageDocument) throws Exception {
      final Element nameElement = findElement(productPageDocument, Selectors.PRODUCT_NAME); // TODO
      String name = fromElementText(nameElement);
      name = validateField(name, "Name");
      return name;
   }

   private String getLink(final Document productPageDocument) throws Exception {
      final Element linkElement = findElement(productPageDocument, Selectors.PRODUCT_LINK); // TODO
      String link = fromAttribute(linkElement, "href");
      link = validateField(link, "Link");
      return link;
   }

   private String getDescription(final Document productPageDocument) throws Exception {
      final Element descriptionElement = findElement(productPageDocument, Selectors.PRODUCT_DESCRIPTION); // TODO
      String description = fromElementText(descriptionElement);
      description = validateField(description, "Description");
      return description;
   }

   private String getKeywords(final Document productPageDocument) throws Exception {
      final Element descriptionElement = findElement(productPageDocument, Selectors.PRODUCT_KEYWORDS); // TODO
      String description = fromAttribute(descriptionElement, "content");
      description = validateField(description, "Keywords");
      return description;
   }

   private String getBrand(final Document productPageDocument) throws Exception {
      // final Element brandElement = findElement(productPageDocument, Selectors.PRODUCT_BRAND); // TODO
      // String brand = fromElementText(brandElement);
      // brand = validateField(brand, "Brand");
      return "aaaaaaaaaaaa";
   }

   private String getCategory(final Document productPageDocument) throws Exception {
      String category = "";
      final Elements categoryElements = productPageDocument.select(Selectors.PRODUCT_CATEGORY);
      for (Element elmt : categoryElements) {
         if (!category.equals("")) category += " > ";
         category += elmt.text();
      }
      return category;
   }

   private String cleanCategory(String category) {
      return category != null ? category.trim() : null;
   }

   private String getImage(final Document productPageDocument) throws Exception {
      final Element imageElement = findElement(productPageDocument, Selectors.PRODUCT_IMAGE); // TODO
      String image = fromAttribute(imageElement, "src");
      image = validateField(image, "Image");
      image = cleanPath(image);
      return image;
   }

   private float getPrice(final Element element) {
      final Element priceElement = findElement(element, Selectors.PRODUCT_PRICE);
      String priceRaw = fromElementText(priceElement);
      priceRaw = validateField(priceRaw, "Price", 1);
      return parseLocalizedPrice(priceRaw.replace(".", ","));
   }

   private float getShippingCost(final Element element) {
      return -1f;
   }

   private int getQuantity(final Element element) throws Exception {
      Element quantityElement = findElement(element, Selectors.PRODUCT_QUANTITY);// TODO
      String quantityRaw = fromElementText(quantityElement);
      quantityRaw = validateField(quantityRaw, "Quantity", 1);
      try {
         return Integer.parseInt(quantityRaw.replaceAll("[^\\d]", ""));
      } catch (Exception e) {
         System.err.println("Unparsable quantity raw : " + quantityRaw);
         return 0;
      }
   }

   private int getShippingDelay(final String delayRaw) {// TODO
      if (StringUtils.isNotBlank(delayRaw)) {
         final String lcRawDelivery = StringUtils.lowerCase(delayRaw);
         final DateTime when = parseLocalizedDeliveryDate(lcRawDelivery);
         if (when != null) {
            int delivery = getDeliveryDaysBetween(DateTime.now(), when);
            // if (delivery == 0) {
            // return 1;
            // }
            return delivery;
         }
      }
      return 0;
   }

   private String getShippingDelayRaw(final Element element) {
      Element shippingDelayElement = findElement(element, Selectors.PRODUCT_DELIVERY);// TODO
      String shippingDelayRaw = fromElementText(shippingDelayElement);
      shippingDelayRaw = StringUtils.substringAfterLast(shippingDelayRaw, "le").replace("/", "-").trim();
      shippingDelayRaw = validateField(shippingDelayRaw, "Raw delivery", 1);
      return shippingDelayRaw;
   }

   private int getDeliveryDaysBetween(final DateTime reference, final DateTime when) {
      return Days.daysBetween(reference, when).getDays();
   }

   private String cleanDeliveryToGetParseableDate(final String rawDelivery) {
      // TODO
      return StringUtils.trim(rawDelivery);
   }

   private String fromAttribute(final Element element, final String attr) {
      if (element != null) {
         String text = element.attr(attr);
         // text = text.replace(CARACTERE_ESPACE, " ");
         return StringUtils.trim(text);
      }
      return null;
   }

   // private DateTime parseLocalizedDeliveryDate(final String rawDelivery, final String url) {
   // if (isExpressedAsPeriod(rawDelivery)) { // Ex : rawDelivery -> "livraison sous 5 jours"
   // return parseLocalizedPeriodDelivery(rawDelivery);
   // }
   // if (isExpressedAsDate(rawDelivery)) { // Ex : rawDelivery -> "date de livraison : 02-05-2016"
   // return parseLocalizedDateDelivery(rawDelivery);
   // }
   // logger.error("New form of raw delivery found [" + rawDelivery + "]");
   // return null;
   // }

   private DateTime parseLocalizedDeliveryDate(final String rawDelivery) {
      if (isExpressedAsPeriod(rawDelivery)) {
         return parseLocalizedPeriodDelivery(rawDelivery);
      }
      if (isExpressedAsDate(rawDelivery)) { // Ex : rawDelivery -> "date de livraison : 02-05-2016"
         return parseLocalizedDateDelivery(rawDelivery);
      }
      logger.error("New form of raw delivery found [" + rawDelivery + "]");
      return null;
   }

   private DateTime parseLocalizedDateDelivery(String rawDelivery) {
      final String delivery = cleanDeliveryToGetParseableDate(rawDelivery); // Ex : delivery -> "02-10-2016"
      try {
         final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy").withLocale(CURRENT_LOCALE); // TODO
         final LocalDateTime localDateTime = LocalDateTime.parse(delivery, dateTimeFormatter);
         return localDateTime.toDateTime().plusDays(1);
      } catch (Exception exc) {
         logger.error("Delivery date not parseable [" + delivery + "]");
         // conn.debug(ExceptionUtils.getStackTrace(exc));
      }
      return null;
   }

   private DateTime parseLocalizedPeriodDelivery(final String rawDelivery) {
      String delivery = cleanDeliveryToGetParseablePeriodText(rawDelivery); // Ex : delivery -> "2 semaines"
      if (delivery.matches("\\d+h")) {
         delivery = "" + Integer.parseInt(delivery.replaceAll("[^\\d]", "")) / 24 + " jours";
      }
      try {
         final PeriodFormatter periodFormatter = PeriodFormat.wordBased(CURRENT_LOCALE);
         final Period period = periodFormatter.parsePeriod(delivery);
         return new DateTime().plus(period).plusHours(5);

      } catch (Exception exc) {
         logger.error("Delivery period not parseable [" + delivery + "]");
         // conn.debug(ExceptionUtils.getStackTrace(exc));
      }
      return null;
   }

   private String cleanDeliveryToGetParseablePeriodText(String rawDelivery) {
      // TODO
      if (rawDelivery.contains(SpecialChar.FRENCH_PREPOSITION_A)) {
         rawDelivery = rawDelivery.substring(rawDelivery.indexOf(SpecialChar.FRENCH_PREPOSITION_A));
      }
      Matcher matcher = Pattern.compile("\\d+\\s*\\p{L}+").matcher(rawDelivery); // TODO
      if (matcher.find()) {
         return matcher.group(0);
      }
      return null;
   }

   private boolean isExpressedAsDate(final String rawDelivery) {
      return rawDelivery.matches("\\d{1,2}-\\d{1,2}-\\d{1,4}"); // TODO
   }

   private boolean isExpressedAsPeriod(final String rawDelivery) {
      return StringUtils.contains(rawDelivery, "jour"); // TODO
   }

   private float parseLocalizedPrice(final String priceRaw) {
      final String priceText = cleanPrice(priceRaw);
      // logger.warn("price test " + priceRaw);
      if (StringUtils.isNotBlank(priceText)) {
         try {
            NumberFormat priceFormat = NumberFormat.getNumberInstance(CURRENT_LOCALE);
            Number priceNumber = priceFormat.parse(priceText);
            // return (float) (priceNumber.floatValue() * (1 + (19.8 / 100)));
            return priceNumber.floatValue();
         } catch (ParseException pexc) {
            logger.error("Price number not parseable [" + priceText + "]");
         }
      }
      return -1f;
   }

   private String cleanPrice(final String priceRaw) {
      // TODO
      return priceRaw.replaceAll("[^\\d.,]", "");
   }

   private Element findElement(final Element element, final String cssSelector) {
      return element.select(cssSelector).first();
   }

   private String fromElementText(final Element element) {
      if (element != null) {
         String text = element.text();
         text = StringEscapeUtils.unescapeHtml4(text);
         // text = text.replace(CARACTERE_ESPACE, " ");
         return StringUtils.trim(text);
      }
      return null;
   }

   private String fromOwnElementText(final Element element) {
      if (element != null) {
         String text = element.ownText();
         text = StringEscapeUtils.unescapeHtml4(text);
         return StringUtils.trim(text);
      }
      return null;
   }

   private String validateField(final String value, final String name) throws Exception {
      if (StringUtils.isBlank(value)) {
         throw new NullPointerException(name + " not found");
      }
      return value;
   }

   private String validateField(final String value, final String name, final int log) {
      if (StringUtils.isBlank(value)) {
         if (log == 2) logger.error("" + name + " not found");
         else if (log == 1) logger.warn("" + name + " not found");
         return StringUtils.EMPTY;
      }
      return value;
   }

   private static String cleanPath(String path) {
      if (!StringUtils.startsWith(path, "http:")) {
         return ("http://www.tati.fr/" + path).replace("fr//", "fr/");
      }
      return path;
   }

}
