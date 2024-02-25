package com.Search;

import java.sql.Connection;

public interface Searchable {
	void searchByCategory(Connection con);
	void searchByPrice(Connection con);
   
}
