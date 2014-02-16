/**
 * 
 */
package web.gui.secretary.test;

import junit.framework.TestCase;

import org.junit.Test;

import web.gui.secretary.spec.GUIAbstract;

/**
 * @author maint
 *
 */
public abstract class GUI_TestAbstract extends TestCase {

	protected static TestEnvironment enParams;
	protected static GUIAbstract frame;
	
	public void setUp() {
		enParams = new TestEnvironment();
		enParams.dologin();
	}

	public static void tearDownAfterClass() throws Exception {
		enParams = null;
		if (frame != null) {
			frame.dispose();
			frame = null;
		}
	}

	/**
	 * check if the frame can display on screen.<br>
	 * make sure set title for frame.
	 */
	@Test
	public void test_IsShowing() {
		assertTrue(frame.isShowing());
		assertFalse(frame.getTitle().isEmpty());
	}
	
	public abstract void test_GetLable();
	
	public abstract void test_IsShowingLable();
	
	public abstract void test_IsShowingTextField();
	
	public abstract void test_IsShowingButtons();
	
	public abstract void test_EditableTextField();
	
	public abstract void test_EmptyTextField();
}
