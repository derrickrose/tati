package com.pushtech.crawler.launcher;

public class Selectors {

   // product page
   public static final String PRODUCT_PAGE_IDENTIFIER = "div#retour_product";
   public static final String PRODUCT_NAME = "h1#ajax_product_name>span";
   public static final String PRODUCT_LINK = "p.product-name>a";
   public static final String PRODUCT_DESCRIPTION = "meta[name=description]";
   public static final String PRODUCT_KEYWORDS = "meta[name=keywords]";
   public static final String PRODUCT_IDENTIFIER = ".product-shop > p";
   public static final String PRODUCT_CATEGORY = "div#ariane>a";// elements
   public static final String PRODUCT_IMAGE = "img#img_large";
   public static final String PRODUCT_PRICE = "div.product_price>div>meta[itemprop=price]";
   public static final String PRODUCT_QUANTITY = "p.availability.in-stock>span";
   public static final String PRODUCT_DELIVERY = ".a-shipping";

   // listing page
   public static final String LISTING_PAGE_IDENTIFIER = "ul.list_rayons>li>a";
   public static final String LISTING_PAGE_PRODUCTS = ".products-grid > li";
   public static final String LISTING_PAGE_PRODUCT_LINK = "a";
   public static final String NEXT_PAGE_LINK = ".next.i-next";

   // home page
   public static final String ALL_LISTING = "ul.list_rayons>li>a"; //
   public static final String HOME_PAGE_IDENTIFIER = "#main_wrapper_home";

}
