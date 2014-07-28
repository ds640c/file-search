package com.dnsun.filesearch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegexContentFileFilterTest {
	private final String validFileName = "test55.txt";
	private final String directoryPath = System.getProperty("user.dir") + "/testfilesearch/"; 
	private final Pattern pattern= Pattern.compile("test\\d{3}");
	private final IOFileFilter fileFilter = new NameFileFilter(this.validFileName);
	private final IOFileFilter contentFilter = new RegexContentFileFilter(this.fileFilter, this.pattern);
	private File directory;
	
	@Before
	public void setup() throws IOException{
 		this.directory = new File(this.directoryPath); 
		this.directory.mkdir();
	}
	
	@Test 
	public void testMatchFileMatchContents() throws IOException {
		final File file = new File(this.directoryPath + this.validFileName);
		String content = "this info test123 should match";
		Files.write( file.toPath(), content.getBytes(),  StandardOpenOption.CREATE); 
		
		boolean accepts = contentFilter.accept(file);
		assertTrue(accepts);
	}
	
	@Test
	public void testMatchFileNoMatchContents() throws IOException {
		final File file = new File(this.directoryPath + this.validFileName);
		String content = "this info  should not match";
		Files.write( file.toPath(), content.getBytes(),  StandardOpenOption.CREATE); 
		
		boolean accepts = contentFilter.accept(file);
		assertTrue(accepts);
	}
	
	@Test
	public void testNotMatchFileMatchContents() throws IOException {
		final File file = new File(this.directoryPath + "badfilename.txt");
		String content = "this info test123 should match";
		Files.write( file.toPath(), content.getBytes(),  StandardOpenOption.CREATE); 
		
		boolean accepts = contentFilter.accept(file);
		assertTrue(accepts);
	}
	
	@Test
	public void testNotMatchFileMatchContentEnd() throws IOException {
		final File file = new File(this.directoryPath + "badfilename.txt");
		String content = "this info  should match test888";
		Files.write( file.toPath(), content.getBytes(),  StandardOpenOption.CREATE); 
		
		boolean accepts = contentFilter.accept(file);
		assertTrue(accepts);
	}
	
	@Test
	public void testNotMatchFileMatchContentStart() throws IOException {
		final File file = new File(this.directoryPath + "badfilename.txt");
		String content = "test777 this info  should match";
		Files.write( file.toPath(), content.getBytes(),  StandardOpenOption.CREATE); 
		
		boolean accepts = contentFilter.accept(file);
		assertTrue(accepts);
	}
	
	@Test
	public void testNoMatchFileNoMatchContents() throws IOException {
		final File file = new File(this.directoryPath + "badfilename.txt");
		String content = "this info should not match";
		Files.write( file.toPath(), content.getBytes(),  StandardOpenOption.CREATE); 
		
		boolean accepts = contentFilter.accept(file);
		assertFalse(accepts);
	}
	
	@After
	public void teardown() throws IOException {
		FileUtils.deleteDirectory(new File(this.directoryPath));
		this.directory = null;
	}
}
