/**
 * 
 */
package evoter.mobile.activities;

/**
 * Define setup processes for every activity in application
 * <br> Created by @author luongnv89 on 13-Feb-2014
 *
 */
public interface ActivityInterface {
	
	/**
	 * Initial component, titlebar, mainmenu for activity
	 */
	public void init();
	
	/**
	 * Setup title bar for activity
	 */
	public void setupTitleBar();
	
	
	/**
	 * Setup main menu for activity, the menu when user click on the top-left corner icon of application.
	 */
	public void setupMainMenu();
	
	/**
	 * Setup the graphic component for activity such as: layout, button, ....
	 */
	public void setupGraphicComponent();
}
