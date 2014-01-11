package evoter.mobile.model;


public class Question extends ItemData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5892190903011268048L;

	private String answers;
	private int questionType;

	/**
	 * Student and teacher: Create an object of {@link Question} from response
	 * 
	 * @param id
	 * @param questionText
	 * @param answers
	 * @param questionTy
	 */
	public Question(long id, String questionText, String answers, int questionTy) {
		super(id, questionText);
		this.answers = answers;
		this.questionType = questionTy;
	}

	/**
	 * @param question
	 */
	public Question(Question question) {
		super(question);
		this.questionType = question.getQuestionType();
		this.answers = question.getAnswers();
	}

	/**
	 * @return the answers
	 */
	public String getAnswers() {
		return answers;
	}

	/**
	 * @return the questionTy
	 */
	public int getQuestionType() {
		return questionType;
	}

}
