package com.dnsun.filesearch;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileSearchUtilityTest {

	private final Pattern pattern= Pattern.compile("^[a-z]{3}\\d{3}.*");
	private final String directoryPath = System.getProperty("user.dir") + "/target/testfilesearch/"; 
	private final String subdirectoryPath = directoryPath + "/testsubdir/";
	private File directory;
	private File subdirectory;
	
	
	@Before
	public void setup() throws IOException{
 		this.directory = new File(this.directoryPath); 
		this.directory.mkdir();
		
		this.subdirectory = new File(this.subdirectoryPath);
		this.subdirectory.mkdir();
		
		//these are the control files ... they live in the directory and subdirectory, but should not be returned in matches
		final File nomatchName1 = new File(this.directoryPath + "unmatched.txt");
		Files.write( nomatchName1.toPath(), "contents don't matter".getBytes(),  StandardOpenOption.CREATE);
		
		final File nomatchName2 = new File(this.subdirectoryPath + "unmatched.txt");
		Files.write( nomatchName2.toPath(), "contents don't matter".getBytes(),  StandardOpenOption.CREATE);
	}
	
	@Test
	public void testFileNameAndContentInDirectory() throws IOException {
		final File file1 = new File(this.directoryPath + "bob999");
		Files.write( file1.toPath(), "contents don't matter".getBytes(),  StandardOpenOption.CREATE);
		
		final File file2 = new File(this.directoryPath + "file2.txt");
		Files.write( file2.toPath(), "mom123 should match".getBytes(),  StandardOpenOption.CREATE);
		
		final FileSearchUtility fsUtility = new FileSearchUtility(this.pattern, this.directoryPath, false, true);
		List<File> files = fsUtility.search(); 
		
		assertTrue("Two files found", files.size() ==2);
		assertTrue("Found by name in this directory", files.contains(file1));
		assertTrue("Found by content in this directory", files.contains(file2));
	}
	
	@Test
	public void testFileNameOnlyAndInDirectory() throws IOException {
		final File file1 = new File(this.directoryPath + "bob999");
		Files.write( file1.toPath(), "contents don't matter".getBytes(),  StandardOpenOption.CREATE);
		
		final File file2 = new File(this.directoryPath + "file2.txt");
		Files.write( file2.toPath(), "mom123 should not match".getBytes(),  StandardOpenOption.CREATE);
		
		final FileSearchUtility fsUtility = new FileSearchUtility(this.pattern, this.directoryPath, false, false);
		List<File> files = fsUtility.search(); 
		
		assertTrue("One file found", files.size() ==1);
		assertTrue("Found by name in this directory", files.contains(file1)); 
	}	
	
	@Test
	public void testFileNameAndContentRecursive() throws IOException {
		final File file1 = new File(this.directoryPath + "file1.txt");
		final String content1 = "zll111 this should match";
		Files.write( file1.toPath(), content1.getBytes(),  StandardOpenOption.CREATE);
		
		final File file2 = new File(this.subdirectoryPath + "ale765");
		final String content2 = "this file should match";
		Files.write( file2.toPath(), content2.getBytes(),  StandardOpenOption.CREATE);
		
		final FileSearchUtility fsUtility = new FileSearchUtility(this.pattern, this.directoryPath, true, true);
		List<File> files = fsUtility.search(); 
		
		assertTrue("Two files found", files.size() ==2);
		assertTrue("Found by content in directory", files.contains(file1));
		assertTrue("Found by name in subdirectory", files.contains(file2));
	}	
	
	@Test
	public void testFileNameOnlyAndRecursive() throws IOException {
		final File file1 = new File(this.directoryPath + "file1.txt");
		final String content1 = "file999 this should not match";
		Files.write( file1.toPath(), content1.getBytes(),  StandardOpenOption.CREATE);
		
		final File file2 = new File(this.subdirectoryPath + "ale765");
		final String content2 = "this file should match";
		Files.write( file2.toPath(), content2.getBytes(),  StandardOpenOption.CREATE);
		
		final FileSearchUtility fsUtility = new FileSearchUtility(this.pattern, this.directoryPath, true, false);
		List<File> files = fsUtility.search(); 
		
		assertTrue("One files found", files.size() ==1);
		assertTrue("Found by name in subdirectory", files.contains(file2));
	}
	

	
	@After
	public void teardown() throws IOException {
		FileUtils.deleteDirectory(new File(this.directoryPath));
		this.directory = null;
	}

}
