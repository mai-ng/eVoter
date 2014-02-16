/**
 * 
 */
package web.util;

import java.io.File;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileFilter;

/**
 * @author maint
 *
 */
public class TXTFileFilter extends FileFilter {
	private static final String txtPattern = ".*\\.txt";
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {
		  if (f.isDirectory()) {
		        return true;
		    }
		    return f.getName().matches(txtPattern);

	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
