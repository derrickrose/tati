package com.pushtech.crawler.ihm;

import org.apache.commons.lang3.StringUtils;

public class FormHandler {

	private static Form pushTechConsultingForm = null;// this long name in order
														// to avoid wrong access
														// memory

	public static void validateForm(String linkToCrawl, String dataBaseLink,
			String dataBaseUser, String userPassWord) {
		linkToCrawl = validateField(linkToCrawl);
		dataBaseLink = validateField(dataBaseLink);
		dataBaseUser = validateField(dataBaseUser);
		userPassWord = validateField(userPassWord);
		pushTechConsultingForm = new Form(linkToCrawl, dataBaseLink,
				dataBaseUser, userPassWord);
	}

	public static Form getForm(String linkToCrawl, String dataBaseLink,
			String dataBaseUser, String userPassWord) {
		if (pushTechConsultingForm == null) {
			validateForm(linkToCrawl, dataBaseLink, dataBaseUser, userPassWord);
		}
		return pushTechConsultingForm;
	}
	
	public static Form getForm() {
		return pushTechConsultingForm;
	}

	private static String validateField(String field) {
		if (StringUtils.isBlank(field))
			return null;
		return field;
	}
}
