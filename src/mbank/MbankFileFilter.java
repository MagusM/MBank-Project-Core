package mbank;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MbankFileFilter extends FileFilter {
	public MbankFileFilter() {
		super();
	}

	@Override
	public boolean accept(File f) {
		// TODO Au to-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		return "MBank Query Logs (*.txt)";
	}
	
}
