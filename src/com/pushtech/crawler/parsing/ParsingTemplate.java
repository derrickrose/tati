package com.pushtech.crawler.parsing;

import java.util.HashMap;

import org.apache.http.HttpResponse;

public interface ParsingTemplate {

   public Object parse(Object entry, Object Object, HashMap<String, String> parameters);

//   public static ParsingTemplate getAppropriateParsingTemplate(Object object) {
//	   
//	   
//	   
//	   
//      if ((object instanceof HttpResponse)) {
//         return new PageParsing();
//      }
//      return null;
//   }

   public  ParsingTemplate getAppropriateParsingTemplate(Object object);
   
   
}
