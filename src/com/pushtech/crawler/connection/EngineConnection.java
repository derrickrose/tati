package com.pushtech.crawler.connection;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;
import static com.pushtech.crawler.logging.LoggingHelper.*;
public class EngineConnection {
    // TODO set proxy , set time out , set cookie policy

    private static DefaultHttpClient httpClient = null;
    private String proxy = "123.orange.mg";
    private int proxyPort = 0;
    private int timeOut = 30000;
    private int soTimeOut = 60000;

    public static EngineConnection getDefaultEngineConnection() {
	return new EngineConnection();

    }

    public DefaultHttpClient getHttpClient() {
	if (httpClient == null)
	    httpClient = createClient();
	return httpClient;
    }

    public DefaultHttpClient getHttpClient(String proxy, int proxyPort) {
	this.proxy = proxy;
	this.proxyPort = proxyPort;
	httpClient = createClient();
	return httpClient;
    }

    private EngineConnection() {

    }

    private DefaultHttpClient createClient() {
	DefaultHttpClient httpclient = new DefaultHttpClient();
	httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut);
	httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOut);
	httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
	if (proxy != null && proxyPort != 0) {
	    HttpHost proxyHost = new HttpHost(proxy, proxyPort);
	    HttpRoutePlanner routePlanner = new HttpRoutePlanner() {
		@Override
		public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
		    return new HttpRoute(target, null, new HttpHost(proxy, proxyPort), "https".equalsIgnoreCase(target.getSchemeName()));
		}
	    };
	    httpclient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxyHost);
	    httpclient.setRoutePlanner(routePlanner);
	    logger.info("Engine configured with proxy - "+proxy+":"+proxyPort);
	}else{
		logger.info("Engine configured without proxy");
	}
	return httpclient;
    }

    public void setHttpClient(DefaultHttpClient httpClient) {
	this.httpClient = httpClient;
    }

    public void setProxy(String proxy) {
	this.proxy = proxy;
    }

    public void setProxyPort(int proxyPort) {
	this.proxyPort = proxyPort;
    }

    public void setTimeOut(int timeOut) {
	this.timeOut = timeOut;
    }

    public void setSoTimeOut(int soTimeOut) {
	this.soTimeOut = soTimeOut;
    }

}
