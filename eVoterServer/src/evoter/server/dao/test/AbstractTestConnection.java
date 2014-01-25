package evoter.server.dao.test;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.DriverManager;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;

public abstract class AbstractTestConnection extends DatabaseTestCase{

	String url = "jdbc:mysql://localhost:3306/evoter";
	String username = "root";
	String password = "root";
	protected IDataSet loadedDataSet;
	private String testedTable;
	
	/**
	 * set value for testedTable </br>
	 */
	@Before
	public abstract void setUp();
	
	public void setTestedTable(String tableName){
		this.testedTable = tableName;
	}
	
	// Provide a connection to the database
	protected IDatabaseConnection getConnection() throws Exception{
		System.out.println("get conn");
		Class.forName("com.mysql.jdbc.Driver");
		Connection jdbcConnection = (Connection) DriverManager.getConnection(url, username, password);
		System.out.println(jdbcConnection.isClosed());
		return new DatabaseConnection(jdbcConnection);
	}
	// Load the data which will be inserted for the test
	@SuppressWarnings("deprecation")
	protected IDataSet getDataSet() throws Exception{
		System.out.println("get dataset");
		
		String workingDir = System.getProperty("user.dir");
		
		
		File f = new File("user_type.xml");
		
		loadedDataSet =  new  FlatXmlDataSet(new File(workingDir + File.pathSeparator + "resources" + File.pathSeparator + "user_type.xml"));
		System.out.println("check dataset : "+loadedDataSet);
		return loadedDataSet;
	}
}
