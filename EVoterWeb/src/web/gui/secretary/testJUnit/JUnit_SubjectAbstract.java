/**
 * 
 */
package web.gui.secretary.testJUnit;

import org.junit.Test;

import web.gui.secretary.spec.SubjectGUIAbstract;
import evoter.share.model.Subject;

/**.
 * Test properties of components such as visible, editable...<br
 * Extends by {@link AddStudent_Test}, {@link JUnit_ViewSubject}, {@link JUnit_EditSubject}.
 * @author maint
 *
 */
public abstract class JUnit_SubjectAbstract extends JUnit_GUIAbstract{

	protected static Subject subjectTest;
	
	public void setUp() {
		super.setUp();
		subjectTest = enParams.getSubject("Object");
	}
	
	/**
	 * test {@link SubjectGUIAbstract#getLblTitle()}
	 */
	@Test
	public void test_GetLable() {
		assertEquals("Title: ", ((SubjectGUIAbstract) frame).getLblTitle().getText());
	}
	
	/**
	 * test {@link SubjectGUIAbstract#getLblTitle()} to check whether the label is showing or not?
	 */
	@Test
	public void test_IsShowingLable() {
		assertTrue(((SubjectGUIAbstract) frame).getLblTitle().isShowing());
	}

	/**
	 * test {@link SubjectGUIAbstract#getTxtTitle()} 
	 * to check whether the textfield is showing or not?
	 */
	@Test
	public void test_IsShowingTextField() {
		assertTrue(((SubjectGUIAbstract) frame).getTxtTitle().isShowing());
	}

	/**
	 * test {@link SubjectGUIAbstract#getListStudentView())} and {@link SubjectGUIAbstract#getListTeacherView()}
	 * to check whether the test areas are showing or not?
	 */
	@Test
	public void test_IsShowingTextArea() {
		assertTrue(((SubjectGUIAbstract) frame).getListStudentView().isShowing());
		assertTrue(((SubjectGUIAbstract) frame).getListTeacherView().isShowing());
	}
	
	/**
	 * test {@link SubjectGUIAbstract#getBtnAddStudent()},  {@link SubjectGUIAbstract#getBtnAddTeacher()},
	 * {@link SubjectGUIAbstract#getBtnSave()} 
	 * to check whether the buttons are showing or not?
	 */
	@Test
	public void test_IsShowingButtons() {
		assertTrue(((SubjectGUIAbstract) frame).getBtnAddStudent().isShowing());
		assertTrue(((SubjectGUIAbstract) frame).getBtnAddTeacher().isShowing());
		assertTrue(frame.getBtnSave().isShowing());
	}
	
}
