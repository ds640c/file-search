package com.dnsun.filesearch;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * Decorator that filters for file content matching a regular expression in addition
 * to wrapped filter.
 */
public class RegexContentFileFilter implements IOFileFilter {
	final private FileFilter filter;
	final private Pattern pattern;
	
	
	/**
	 * Constructs a file filter that also matches content to a regular expression.
	 * 
	 * @param filter the wrapped FileFilter
	 * @param pattern regular expression to match content against
	 */
	public RegexContentFileFilter (final IOFileFilter filter, final Pattern pattern) {
		this.filter = filter;
		this.pattern = pattern;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.io.filefilter.IOFileFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String name) {
		throw new UnsupportedOperationException();
	}
	
	
	/* (non-Javadoc)
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(final File file) {
		return  filter.accept(file) || this.contentsMatch(file);
	}
	
	/**
	 * Checks the file content
	 * 
	 * @return match result
	 */
	private boolean contentsMatch (final File file) {
		return true;
	}

}
