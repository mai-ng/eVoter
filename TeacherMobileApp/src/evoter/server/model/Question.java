package evoter.server.model;

import java.io.Serializable;

public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5892190903011268048L;

	private long id;
	private String questionText;
	private String answers;
	private int questionType;
	private String correctAnswer;
	
	
	
	/**
	 * Student and teacher: Create an object of {@link Question} from response
	 * @param id
	 * @param questionText
	 * @param answers
	 * @param questionTy
	 */
	public Question(long id, String questionText, String answers, int questionTy) {
		this.id = id;
		this.questionText = questionText;
		this.answers = answers;
		this.questionType = questionTy;
	}
	
	
	
	/**
	 * Teacher: Create new question to submit to sever
	 * @param questionText
	 * @param answers
	 * @param questionTy
	 * @param correctAnswer
	 */
	public Question(String questionText, String answers, int questionTy,
			String correctAnswer) {
		this.questionText = questionText;
		this.answers = answers;
		this.questionType = questionTy;
		this.correctAnswer = correctAnswer;
	}

	
	/**
	 * Teacher: Edit a question
	 * @param id
	 * @param questionText
	 * @param answers
	 * @param questionTy
	 * @param correctAnswer
	 */
	public Question(long id, String questionText, String answers,
			int questionTy, String correctAnswer) {
		this.id = id;
		this.questionText = questionText;
		this.answers = answers;
		this.questionType = questionTy;
		this.correctAnswer = correctAnswer;
	}



	/**
	 * @param question
	 */
	public Question(Question question) {
		this.id = question.getId();
		this.questionText = question.getQuestionText();
		this.questionType = question.getQuestionType();
		this.answers = question.getAnswers();
	}



	/**
	 * @return the correctAnswer
	 */
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
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
