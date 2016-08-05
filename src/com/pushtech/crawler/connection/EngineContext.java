package com.pushtech.crawler.connection;

import static com.pushtech.commons.UriHandler.cleanPath;

import java.util.HashMap;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class EngineContext {

   private String url = null;
   private HashMap<String, String> parameters = null;
   private HashMap<String, String> headers = null;
   private EngineConnection engineConnection = null;

   public static enum MethodType {
      GET_METHOD, POST_METHOD;
   }

   private EngineContext(String url, HashMap<String, String> parameters, HashMap<String, String> headers, EngineConnection engineConnection) {
      this.url = url;
      this.parameters = parameters;
      this.headers = headers;
      this.engineConnection = engineConnection;
   }

   public static EngineContext getEngineContext(String url, HashMap<String, String> parameters, HashMap<String, String> headers, EngineConnection engineConnection) {
      return new EngineContext(cleanPath(url), parameters, headers, engineConnection);
   }

   public HttpRequestBase getFormedRequest(EngineContext engineContext, MethodType method) {
      HttpRequestBase request = null;
      if (method.equals(MethodType.GET_METHOD)) {
         request = new HttpGet(engineContext.url);
      } else if (method.equals(MethodType.POST_METHOD)) {
         // TODO postMethod
      } else return null;
      request = affectHeaders(request, engineContext.headers);
      request = affectParameters(request, engineContext.parameters);
      return request;
   }

   private HttpRequestBase affectHeaders(HttpRequestBase request, HashMap<String, String> headers) {
      if (headers != null) {
         request.addHeader("Cookie", headers.get("Cookie"));
         request.addHeader("Host", headers.get("Host"));
      }
      return request;
   }

   private HttpRequestBase affectParameters(HttpRequestBase request, HashMap<String, String> parameters) {
      // TODO affect parameters
      return request;
   }

   public HttpContext createDefaultPolicy() {
      // in order to save cookie
      CookieStore cookieStore = engineConnection.getHttpClient().getCookieStore();
      HttpContext localContext = new BasicHttpContext();
      localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
      return localContext;
   }

   public EngineConnection getEngineConnection() {
      return engineConnection;
   }

}
