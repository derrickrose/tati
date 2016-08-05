package com.pushtech.crawler.ihm;

public class Form {

	private String linkToCrawl = null;
	private String dataBaseLink = null;
	private String dataBaseUser = null;
	private String userPassWord = null;

	public Form(String linkToCrawl, String dataBaseLink, String dataBaseUser,
			String userPassWord) {
		this.linkToCrawl = linkToCrawl;
		this.dataBaseLink = dataBaseLink;
		this.dataBaseUser = dataBaseUser;
		this.userPassWord = userPassWord;
	}

	public String getLinkToCrawl() {
		return linkToCrawl;
	}

	public void setLinkToCrawl(String linkToCrawl) {
		this.linkToCrawl = linkToCrawl;
	}

	public String getDataBaseLink() {
		return dataBaseLink;
	}

	public void setDataBaseLink(String dataBaseLink) {
		this.dataBaseLink = dataBaseLink;
	}

	public String getDataBaseUser() {
		return dataBaseUser;
	}

	public void setDataBaseUser(String dataBaseUser) {
		this.dataBaseUser = dataBaseUser;
	}

	public String getUserPassWord() {
		return userPassWord;
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

}
