/**
 * 
 */
package evoter.mobile.utils;

/**
 * Present the statistic of an answer
 * @author  luongnv89
 */
public class AnswerData {
	/**
	 * id of answer
	 */
	long id;
	/**
	 * Statistic of answer
	 */
	int statistic;
	
	/**
	 * @param value
	 * @param statistic
	 */
	public AnswerData(long id, int statistic) {
		super();
		this.id = id;
		this.statistic = statistic;
	}


	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the statistic
	 */
	public int getStatistic() {
		return statistic;
	}
	/**
	 * @param statistic the statistic to set
	 */
	public void setStatistic(int statistic) {
		this.statistic = statistic;
	}
	
	
	
}