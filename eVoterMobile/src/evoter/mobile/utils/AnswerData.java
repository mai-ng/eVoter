/**
 * 
 */
package evoter.mobile.utils;

/**
 * @author  luongnv89
 */
public class AnswerData {
	int value;
	int statistic;
	
	/**
	 * @param value
	 * @param statistic
	 */
	public AnswerData(int value, int statistic) {
		super();
		this.value = value;
		this.statistic = statistic;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
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