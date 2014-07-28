package com.dnsun.filesearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.regex.Matcher;
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
		//make sure we find the request pattern inside a block of text
		final StringBuffer newRegex = new StringBuffer(".*").append(pattern.pattern()).append(".*");
		
		this.filter = filter;
		this.pattern = Pattern.compile(newRegex.toString());
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
		return  this.filter.accept(file) || this.contentsMatch(file);
	}
	
	/**
	 * Checks the file content
	 * 
	 * @return match result
	 * @throws IOException 
	 */
	private boolean contentsMatch (final File file)  {
		try {
			final BufferedReader reader = Files.newBufferedReader(file.toPath(), Charset.defaultCharset() );
			
			String line = null;
			while ( (line = reader.readLine()) != null ) {
				 final Matcher matcher = this.pattern.matcher(line);
				 if (matcher.matches()) {
					 return true;
				 }
			}
		}
		catch (IOException e) {
			//we're not going to stop if we can't read the file
		}
		
		return false;
	}

}
