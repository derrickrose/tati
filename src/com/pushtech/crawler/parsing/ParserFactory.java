package com.pushtech.crawler.parsing;

import org.apache.http.HttpResponse;

public class ParserFactory {

	public static PageParser getAppropriateParsingTemplate(Object object) {
		if ((object instanceof HttpResponse)) {
			return new PageParser();
		}
		return null;
	}

}
