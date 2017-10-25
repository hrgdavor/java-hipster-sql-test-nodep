package hr.hrg.hipster.dao.test;

import java.util.*;

import hr.hrg.hipster.sql.*;

@HipsterVisitor
public interface UserVisitorSample {

	public void visitUser(
			@HipsterColumn(name="user_id")
			Long id, 
			@HipsterColumn(name="name", sql="concat(name,'-xxx')")
			List<String> name, 
			@HipsterColumn(name="age")
			int age);
	
}
