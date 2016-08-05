package com.pushtech.crawler.launcher;

import com.pushtech.crawler.beans.Page;

public class PageType {

	public static boolean isProductPage(Page page) {
		
		
		if (page != null)
			return page.getDoc().select(Selectors.PRODUCT_PAGE_IDENTIFIER)
					.first() != null;
		return false;
	}

	public static boolean isListingPage(Page page) {
		if (page != null)
			return page.getDoc().select(Selectors.LISTING_PAGE_IDENTIFIER)
					.first() != null;
		return false;
	}

	public static boolean isHomePage(Page page) {
		if (page != null)
			return page.getDoc().select(Selectors.HOME_PAGE_IDENTIFIER)
					.first() != null;
		return false;
	}

}
