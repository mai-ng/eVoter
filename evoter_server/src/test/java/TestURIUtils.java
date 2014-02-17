import static org.junit.Assert.*;

import org.junit.Test;

import evoter.server.http.URIUtils;


public class TestURIUtils {

	URIUtils utils;
	
	@Test
	public void test_fixedEncodeValueParameter() {
		
		String before = "2013-56-12+02%3A56%3A12";
		String after = URIUtils.fixedEncodeValueParameter(before);
		assertEquals(after, "2013-56-12 02:56:12");
	}

}
