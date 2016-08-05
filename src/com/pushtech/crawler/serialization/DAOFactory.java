package com.pushtech.crawler.serialization;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {

	public abstract DAOFactory getFactoryInstance() ;
	
	public abstract Connection getConnection() throws SQLException;

}
