package web.gui.secretary.test;

import org.junit.Test;

import web.gui.secretary.AddSubject;
import web.gui.secretary.spec.SubjectGUIAbstract;

/**
 * Test properties of components such as visible, editable of {@link AddSubject}.
 * @see SubjectGUI_TestAbstract
 * @author maint
 */
public class AddSubject_Test extends SubjectGUI_TestAbstract{

	public void setUp() {
		super.setUp();
		frame = new AddSubject();
	}

	/**
	 * test  {@link SubjectGUIAbstract#getTxtTitle()},
	 * {@link SubjectGUIAbstract#getListStudentView()} and 
	 * {@link SubjectGUIAbstract#getListTeacherView()}
	 * can be edited.
	 */
	@Override
	public void test_EditableTextField() {
		assertTrue(((SubjectGUIAbstract) frame).getTxtTitle().isEditable());
		assertTrue(((SubjectGUIAbstract) frame).getListStudentView().isEditable());
		assertTrue(((SubjectGUIAbstract) frame).getListTeacherView().isEditable());
	}

	/**
	 *  test  {@link SubjectGUIAbstract#getTxtTitle()},
	 * {@link SubjectGUIAbstract#getListStudentView()} and 
	 * {@link SubjectGUIAbstract#getListTeacherView()} are empty after creating a {@link AddSubject}.
	 */
	@Override
	public void test_EmptyTextField() {
		assertEquals(((SubjectGUIAbstract) frame).getTxtTitle().getText(),"");
		assertEquals(((SubjectGUIAbstract) frame).getListStudentView().getText(),"");
		assertEquals(((SubjectGUIAbstract) frame).getListTeacherView().getText(),"");
		
	}
	
	/**
	 * test all buttons are showing.<br>
	 * {@link AddSubject#getBtnSave()} set text = "Add".
	 */
	@Test
	public void test_IsShowingButtons() {
		super.test_IsShowingButtons();
		assertEquals(frame.getBtnSave().getText(), "Add");
	}

}
