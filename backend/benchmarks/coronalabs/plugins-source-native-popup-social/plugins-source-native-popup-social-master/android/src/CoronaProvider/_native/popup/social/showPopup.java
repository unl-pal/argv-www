//
//  showPopup.java
//  Social Plugin
//
//  Copyright (c) 2013 Coronalabs. All rights reserved.
//

// Package name
package CoronaProvider._native.popup.social;

// Java Imports
import java.util.*;

/**
 * Implements the showPopup() function in Lua.
 * <p>
 * Show's a chooser dialog.
 */
public class showPopup 
{
	/**
	 * Gets the name of the Lua function as it would appear in the Lua script.
	 * @return Returns the name of the custom Lua function.
	 */
	public String getName() 
	{
		return "showPopup";
	}

	// Event task
	private static class RaisePopupResultEventTask 
	{
		private int fLuaListenerRegistryId;
		private int fResultCode;

		public RaisePopupResultEventTask( int luaListenerRegistryId, int resultCode ) 
		{
			fLuaListenerRegistryId = luaListenerRegistryId;
			fResultCode = resultCode;
		}
	}
	
	// Our lua callback listener
 	private int fListener;
}
