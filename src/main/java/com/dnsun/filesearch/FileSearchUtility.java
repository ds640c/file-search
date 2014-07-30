package com.dnsun.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.dnsun.filesearch.RegexContentFileFilter;


/**
 * This utility provides the ability to find files that match a regular
 * expressions. The search can optionally be recursive and to match content.
 *
 */
public class FileSearchUtility {
	final private Pattern pattern;
	final private File directory;
	final private boolean recursive;
	final private boolean matchContents;
	final private IOFileFilter fileFilter;
	final private IOFileFilter contentFilter;
	final private IOFileFilter activeFileFilter;
	final private IOFileFilter activeDirectoryFilter;
	
	
	/**
	 * Constructs the file search utility.
	 *  
	 * @param pattern the regular expression to match against files and/or file content
	 * @param directory first level directory to start search
	 * @param recursive whether to search subdirectories
	 * @param matchContents whether to match content
	 */
	public FileSearchUtility(final Pattern pattern, final String directory, final boolean recursive, final boolean matchContents) {
		this.pattern = pattern;
		this.recursive = recursive;
		this.matchContents = matchContents;
		this.directory = new File(directory);
		
		this.fileFilter = new RegexFileFilter(this.pattern);
		this.contentFilter = new RegexContentFileFilter(this.fileFilter, this.pattern);
		
		this.activeDirectoryFilter = (this.recursive) ? TrueFileFilter.INSTANCE : null;
		this.activeFileFilter = (this.matchContents) ? contentFilter : fileFilter;
	}
	
	/**
	 * Searches the directory with the requested criteria.
	 * 
	 * @return list of matching files
	 */
	public List<File> search() { 
		final Collection<File> files = FileUtils.listFiles(directory, this.activeFileFilter , this.activeDirectoryFilter);
		return new ArrayList<File>(files);
	}
}
