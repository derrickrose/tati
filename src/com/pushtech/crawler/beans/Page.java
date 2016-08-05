package com.pushtech.crawler.beans;

import org.jsoup.nodes.Document;

public class Page {

   private int statusCode = 0;
   private String url = null;
   private String redirectionUrl = null;
   private String content = null;
   private Document doc = null;

   public Page(int statusCode, String content, Document doc) {
      this.statusCode = statusCode;
      this.content = content;
      this.doc = doc;
   }

   public int getStatus() {
      return statusCode;
   }

   public String getUrl() {
      return url;
   }

   public String getRedirectionUrl() {
      return redirectionUrl;
   }

   public String getContent() {
      return content;
   }

   public Document getDoc() {
      return doc;
   }

   public void setStatus(int status) {
      this.statusCode = statusCode;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public void setRedirectionUrl(String redirectionUrl) {
      this.redirectionUrl = redirectionUrl;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public void setDoc(Document doc) {
      this.doc = doc;
   }

}
