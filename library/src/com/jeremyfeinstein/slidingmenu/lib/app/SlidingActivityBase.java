package com.jeremyfeinstein.slidingmenu.lib.app;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public interface SlidingActivityBase {
	
	/**
	 * Set the behind myview content to an explicit myview. This myview is placed directly into the behind myview 's myview hierarchy.
	 * It can itself be a complex myview hierarchy.
	 *
	 * @param view The desired content to display.
	 * @param layoutParams Layout parameters for the myview.
	 */
	public void setBehindContentView(View view, LayoutParams layoutParams);

	/**
	 * Set the behind myview content to an explicit myview. This myview is placed directly into the behind myview 's myview hierarchy.
	 * It can itself be a complex myview hierarchy. When calling this method, the layout parameters of the specified
	 * myview are ignored. Both the width and the height of the myview are set by default to MATCH_PARENT. To use your
	 * own layout parameters, invoke setContentView(android.myview.View, android.myview.ViewGroup.LayoutParams) instead.
	 *
	 * @param view The desired content to display.
	 */
	public void setBehindContentView(View view);

	/**
	 * Set the behind myview content from a layout resource. The resource will be inflated, adding all top-level views
	 * to the behind myview.
	 *
	 * @param layoutResID Resource ID to be inflated.
	 */
	public void setBehindContentView(int layoutResID);

	/**
	 * Gets the SlidingMenu associated with this activity.
	 *
	 * @return the SlidingMenu associated with this activity.
	 */
	public SlidingMenu getSlidingMenu();
		
	/**
	 * Toggle the SlidingMenu. If it is open, it will be closed, and vice versa.
	 */
	public void toggle();
	
	/**
	 * Close the SlidingMenu and show the content myview.
	 */
	public void showContent();
	
	/**
	 * Open the SlidingMenu and show the menu myview.
	 */
	public void showMenu();

	/**
	 * Open the SlidingMenu and show the secondary (right) menu myview. Will default to the regular menu
	 * if there is only one.
	 */
	public void showSecondaryMenu();
	
	/**
	 * Controls whether the ActionBar slides along with the above myview when the menu is opened,
	 * or if it stays in place.
	 *
	 * @param slidingActionBarEnabled True if you want the ActionBar to slide along with the SlidingMenu,
	 * false if you want the ActionBar to stay in place
	 */
	public void setSlidingActionBarEnabled(boolean slidingActionBarEnabled);
	
}
