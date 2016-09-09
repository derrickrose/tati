package com.pushtech.crawler.launcher;

public class Selectors {

   // product page
   public static final String PRODUCT_PAGE_IDENTIFIER = "div#retour_product";
   public static final String PRODUCT_NAME = "h1#ajax_product_name>span";
   public static final String PRODUCT_LINK = "p.product-name>a";
   public static final String PRODUCT_DESCRIPTION = "div.tab_nfo_cnt:has(ul.info_liste)";
   public static final String PRODUCT_KEYWORDS = "meta[name=keywords]";
   public static final String PRODUCT_IDENTIFIER = "input[name=produit_id]";
   public static final String PRODUCT_CATEGORY = "div#ariane>a";// elements
   public static final String PRODUCT_IMAGE = "img#img_large";
   public static final String PRODUCT_PRICE = "p#prix_site";
   public static final String PRODUCT_QUANTITY = "p.availability.in-stock>span";
   public static final String PRODUCT_DELIVERY = "p#chez_vous_le";
   public static final String PRODUCT_COLOR_VARIANT = "input[data-prodid=160199]";
   public static final String PRODUCT_IMG_ALL = "div#prod_detail_panner>img";

   // listing page
   public static final String LISTING_PAGE_IDENTIFIER = ".product_link";
   public static final String LISTING_PAGE_PRODUCTS = ".product_link";
   public static final String LISTING_PAGE_PRODUCT_LINK = "a.wrapper_product";
   public static final String NEXT_PAGE_LINK = ".next.i-next";

   // home page
   public static final String ALL_LISTING = "ul.list_rayons>li>a"; //
   public static final String HOME_PAGE_IDENTIFIER = "#main_wrapper_home";

}
