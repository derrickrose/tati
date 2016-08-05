package com.pushtech.crawler.launcher;

public class Selectors {

   // product page
   public static final String PRODUCT_PAGE_IDENTIFIER = ".a-back";
   public static final String PRODUCT_NAME = ".product-essential > h1";
   public static final String PRODUCT_LINK = "p.product-name>a";
   public static final String PRODUCT_DESCRIPTION = ".std";
   public static final String PRODUCT_KEYWORDS = "meta[name=keywords]";
   public static final String PRODUCT_IDENTIFIER = ".product-shop > p";
   public static final String PRODUCT_CATEGORY = ".breadcrumbs > ul > li > a";// elements
   public static final String PRODUCT_IMAGE = "#image , #img";
   public static final String PRODUCT_PRICE = ".price";
   public static final String PRODUCT_QUANTITY = "p.availability.in-stock>span";
   public static final String PRODUCT_DELIVERY = ".a-shipping";

   // listing page
   public static final String LISTING_PAGE_IDENTIFIER = ".block.block-nav-category";
   public static final String LISTING_PAGE_PRODUCTS = ".products-grid > li";
   public static final String LISTING_PAGE_PRODUCT_LINK = "a";
   public static final String NEXT_PAGE_LINK = ".next.i-next";

   // home page
   public static final String ALL_LISTING = ".level1 > a"; //
   public static final String HOME_PAGE_IDENTIFIER = ".slides.accueil-slides";

}
