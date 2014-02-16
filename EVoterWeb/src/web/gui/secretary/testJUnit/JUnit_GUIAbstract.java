/**
 * 
 */
package web.gui.secretary.testJUnit;

import junit.framework.TestCase;

import org.junit.Test;

import web.gui.secretary.AddSubject;
import web.gui.secretary.AddUser;
import web.gui.secretary.EditSubject;
import web.gui.secretary.spec.GUIAbstract;

/**
 * create environment to skip log in step in purpose of testing JUnit for:<br>
 * {@link AddSubject}, {@link AddUser},
 * {@link EditSubject},{@link EditUser},
 * {@link ViewSubject}, {@link ViewUser}.
 * @author maint
 *
 */
public abstract class JUnit_GUIAbstract extends TestCase {

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
