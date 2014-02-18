/**
 * 
 */
package web.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Define format of a file to read.
 * @author maint
 *
 */
public class FileFormat extends FileFilter {
	private static final String txtPattern = ".*\\.txt";
	
	public boolean accept(File f) {
		  if (f.isDirectory()) {
		        return true;
		    }
		    return f.getName().matches(txtPattern);

	}

	public String getDescription() {
		return null;
	}

}
