/* PasswordPromptActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 23, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * The Password Prompt activity is the front gate for Perfect Paper Passwords.  If the
 * user has set a password for the application, this activity will prompt the user to
 * enter the password before they are allowed to continue.  If there is no password
 * set, this activity transparently passes control to the Main Menu activity.
 * 
 * If a password is set, the user cannot pass to the Main Menu until the correct
 * password is entered.  However, in the event that the user forgets their password,
 * they can elect to clear the password at the expense of wiping the database and
 * losing all their saved card set data.  This allows some level of recovery while
 * still preserving the security of the stored data.
 * 
 * This program is Copyright 2011, Jeffrey T. Darlington.
 * E-mail:  android_apps@gpf-comics.com
 * Web:     https://code.google.com/p/android-ppp/
 * 
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this
 * program.  If not, see http://www.gnu.org/licenses/.
*/
package com.gpfcomics.android.ppp;

/**
 * The Password Prompt activity is the front gate for Perfect Paper Passwords.  If the
 * user has set a password for the application, this activity will prompt the user to
 * enter the password before they are allowed to continue.  If there is no password
 * set, this activity transparently passes control to the Main Menu activity.
 * 
 * If a password is set, the user cannot pass to the Main Menu until the correct
 * password is entered.  However, in the event that the user forgets their password,
 * they can elect to clear the password at the expense of wiping the database and
 * losing all their saved card set data.  This allows some level of recovery while
 * still preserving the security of the stored data.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class PasswordPromptActivity {
	
	/** This constant identifies the Clear Password option menu */
	private static final int OPTMENU_CLEAR_PASSWORD = 7530;
	
	/** This constant identifies the Help option menu */
	private static final int OPTMENU_HELP = 7531;
	
	/** This constant identifies the Help option menu */
	private static final int OPTMENU_ABOUT = 7532;
	
	/** This constant identifies the Clear Password dialog */
	private static final int DIALOG_CLEAR_PASSWORD = 7550;
	
	protected Object onCreateDialog(int id)
    {
		return new Object();
    }
}
