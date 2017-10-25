package hr.hrg.hipster.dao.test;

import java.sql.*;
import java.util.*;

import hr.hrg.hipster.sql.*;
import hr.hrg.hipster.sql.Query;

public class Testh2dbVisitorNoDep {
	static {
	    @SuppressWarnings ("unused") Class<?>[] classes = new Class<?>[] {
	    	org.h2.Driver.class
	    };
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println("Starting ");
		long start = System.currentTimeMillis();
		
		Connection conn = Testh2dbNoDep.makeTestConnection();
		Testh2dbNoDep.createUserTable(conn);
		
        System.out.println(" created table in "+(System.currentTimeMillis()-start)+"ms");
		

        ResultGetterSource getterSource = new ResultGetterSource();
        getterSource.registerFor(new StringListGetter(), List.class, String.class);

        HipsterSql hipSql = new HipsterSql(new PreparedSetterSource(), getterSource);

//        hipSql.getVisitorSource().registerFor(new UserVisitorSampleHandler(getterSource), UserVisitorSample.class);
        
		HipsterConnectionImpl hip = new HipsterConnectionImpl(hipSql, conn);
        
		System.out.println(" prepared hipster "+(System.currentTimeMillis()-start)+"ms");

		System.out.println();
		System.out.println("where name like '%world%'  ...... UserInner interface");
		Query query = new Query("select user_id, name, age from user_table WHERE name like ","%world%");
		hip.rowsVisit(query, new UserVisitorSample() {	
			@Override
			public void visitUser(Long id, List<String> name, int age) {
				System.out.print("User #"+id+" ");
				if(name != null) for(String n:name){
					System.out.print(n+", ");
				}
				System.out.println(" "+age);
			}
		});
        //printUsersInner(hip.entities(UserInner.class,"from user_table WHERE name like ","%world%"));		
		System.out.println(" printed results in "+(System.currentTimeMillis()-start)+"ms");		

		System.out.println();
		
		query = new Query(" from user_table WHERE name like ","%world%");
		hip.rowsVisit(query, new UserVisitorSample() {	
			@Override
			public void visitUser(Long id, List<String> name, int age) {
				System.out.print("User #"+id+" ");
				if(name != null) for(String n:name){
					System.out.print(n+", ");
				}
				System.out.println(" "+age);
			}
		});
        //printUsersInner(hip.entities(UserInner.class,"from user_table WHERE name like ","%world%"));		
		System.out.println(" printed results in "+(System.currentTimeMillis()-start)+"ms");		

	}
}
