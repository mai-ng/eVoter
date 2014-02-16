package web.gui.secretary.testJUnit;

import org.junit.Test;

import web.gui.secretary.EditSubject;
import web.gui.secretary.spec.SubjectGUIAbstract;

/**
 * Test properties of components such as visible, editable of {@link EditSubject}.
 * @author maint
 * @see JUnit_SubjectAbstract
 */
public class JUnit_EditSubject extends JUnit_SubjectAbstract{

//	public static void main(String[] args) throws IOException
//	{
//		Subject sub = new Subject();
//		EditSubject subject = new EditSubject(sub);
//	}
	
	public void setUp() {
		super.setUp();
		frame = new EditSubject(subjectTest);
	}
	
	/**
	 *  test  {@link SubjectGUIAbstract#getTxtTitle()} is the title of <code>testSubject#title</code>
	 */
	@Override
	public void test_EmptyTextField() {
		assertEquals(((SubjectGUIAbstract) frame).getTxtTitle().getText(),subjectTest.getTitle());
	}

	/**
	 * test all buttons are showing.<br>
	 * {@link EditSubject#getBtnSave()} set text = "Update".
	 */
	@Test
	public void test_IsShowingButtons() {
		super.test_IsShowingButtons();
		assertEquals(((EditSubject)frame).getBtnSave().getText(), "Update");
	}

	/**
	 * test  {@link SubjectGUIAbstract#getTxtTitle()},
	 * {@link SubjectGUIAbstract#getListStudentView()} and 
	 * {@link SubjectGUIAbstract#getListTeacherView()}
	 * can be edited.
	 */
	public void test_EditableTextField() {
		assertTrue(((SubjectGUIAbstract) frame).getTxtTitle().isEditable());
		assertTrue(((SubjectGUIAbstract) frame).getListStudentView().isEditable());
		assertTrue(((SubjectGUIAbstract) frame).getListTeacherView().isEditable());
	}
}
