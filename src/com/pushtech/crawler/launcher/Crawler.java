package com.pushtech.crawler.launcher;

import org.apache.commons.lang3.StringUtils;

import com.pushtech.crawler.ihm.FormHandler;

public class Crawler extends Thread {

   @Override
   public void run() {
      String linkToCrawl = FormHandler.getForm().getLinkToCrawl();
      if (StringUtils.isBlank(linkToCrawl)) {
         linkToCrawl = "http://www.tati.fr/";
      }
      // new TreatCrawl(linkToCrawl);

      new Crawl(linkToCrawl);
   }

}
