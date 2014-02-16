package web.gui.secretary.test;

import org.junit.Test;

import web.gui.secretary.ViewSubject;
import web.gui.secretary.spec.SubjectGUIAbstract;

/**
 * Test properties of components such as visible, editable of {@link ViewSubject}.
 * @see SubjectGUI_TestAbstract
 * @author maint
 */
public class ViewSubject_Test extends SubjectGUI_TestAbstract{

	public void setUp() {
		super.setUp();
		frame = new ViewSubject(subjectTest);
	}
	
	/**
	 * test  {@link SubjectGUIAbstract#getTxtTitle()},
	 * {@link SubjectGUIAbstract#getListStudentView()} and 
	 * {@link SubjectGUIAbstract#getListTeacherView()}
	 * can NOT be edited.
	 */
	public void test_EditableTextField() {
		assertFalse(((SubjectGUIAbstract) frame).getTxtTitle().isEditable());
		assertFalse(((SubjectGUIAbstract) frame).getListStudentView().isEditable());
		assertFalse(((SubjectGUIAbstract) frame).getListTeacherView().isEditable());
	}

	/**
	 *  test  {@link SubjectGUIAbstract#getTxtTitle()} is the title of <code>testSubject#title</code>
	 */
	public void test_EmptyTextField() {
		assertEquals(((SubjectGUIAbstract) frame).getTxtTitle().getText(),subjectTest.getTitle());
	}
	
	/**
	 * test {@link SubjectGUIAbstract#getBtnAddStudent()},  {@link SubjectGUIAbstract#getBtnAddTeacher()},
	 * {@link SubjectGUIAbstract#getBtnSave()} 
	 * NOT showing on screen.
	 */
	@Test
	public void test_IsShowingButtons() {
		assertFalse(((SubjectGUIAbstract) frame).getBtnAddStudent().isShowing());
		assertFalse(((SubjectGUIAbstract) frame).getBtnAddTeacher().isShowing());
		assertFalse(frame.getBtnSave().isShowing());
	}
}
