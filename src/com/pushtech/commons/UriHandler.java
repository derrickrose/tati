package com.pushtech.commons;

import org.apache.commons.lang3.StringUtils;

public class UriHandler {

   public static String cleanPath(String path) {
      if (path == null) return null;
      path = path.replace("" + (char) 201, "%C3%89").replace(" ", "%20").replace("" + (char) 232, "%C3%A8");
      path = path.replace("" + ((char) 96), "%60").replace("" + ((char) 233), "%C3%A9").replace("" + ((char) 146), "%E2%80%99");
      if (!StringUtils.startsWith(path, "http")) {
         path = ("http://www.tati.fr/" + path).replace("fr//", "fr/");
      }

      return path;
   }

}
