/**
 * 
 */
package evoter.server.dao.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author btdiem
 *
 */
public class JUnit_TestUserType extends AbstractTestConnection{

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	@Before
	public void setUp() {
		setTestedTable("user_type");
		
	}
	
    // Check that the data has been loaded.
    public void testCheckLoginDataLoaded() throws Exception{
      
    	assertNotNull(loadedDataSet);
      int rowCount = loadedDataSet.getTable("user_type").getRowCount();
      assertEquals(3, rowCount);
    }


}
