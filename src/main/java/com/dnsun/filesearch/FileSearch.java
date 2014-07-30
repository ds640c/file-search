package com.dnsun.filesearch;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class FileSearch {
	private static final String HELP_PARAM = "h";
	private static final String RECURSIVE_PARAM = "r";
	private static final String CONTENT_PARAM = "c";
	private static final String HELP_TEXT = "FileSearch [options] directory regex";
	private static final String HELP_HEADER = "where options are:";
	private final FileSearchUtility utility;
	private final String directory;
	
	public FileSearch(final String directory, final FileSearchUtility utility) {
		this.utility = utility;
		this.directory = directory;
	}
	
	public void search() {
		List<File> files = utility.search();
		for (File file : files) {
			System.out.println(file.getPath().replace(this.directory, "."));
		}
		
		if(files.size()==0) {
			System.out.println("No files found matching your search criteria.");
		}
	}
	
	public static void main (String[] args) {
		final HelpFormatter help = new HelpFormatter();
		final Options options = createOptions();
		
		try { 
			//Parse the command line parameters 
			final CommandLine cmd = createCommandLine(args, options); 
			if (cmd.hasOption(HELP_PARAM)) {
				help.printHelp(HELP_TEXT, HELP_HEADER, options, "");
			}
			
			if (cmd.getArgList().size() !=2) {
				throw new IllegalArgumentException("Unexpected number of arguments.");
			}
			
			final boolean recursive = (cmd.hasOption(RECURSIVE_PARAM)) ? true : false;
			final boolean contents = (cmd.hasOption(CONTENT_PARAM)) ? true : false;
			final String directory = cmd.getArgs()[0].replaceAll("\\/$", "");
			final StringBuffer regx = new StringBuffer(".*").append(cmd.getArgs()[1]).append(".*");
			final Pattern pattern = Pattern.compile(regx.toString());
			
			//The utility that actually does the searching
			final FileSearchUtility utility = new FileSearchUtility(pattern, directory, recursive, contents);
			final FileSearch fileSearch = new FileSearch(directory, utility);
			
			//search and display the output
			fileSearch.search();
 
		} catch (ParseException e) {
			System.out.println("Error reading your command line arguments.");
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			System.out.println("Directory and regex are required parameters. Please make sure your directory parameter is an actual directory.");
			help.printHelp(HELP_TEXT, HELP_HEADER, options, "");
		}  
	}  
	
	private static Options createOptions() {
		final Options options = new Options();
		options.addOption(HELP_PARAM, false, "prints the help");
		options.addOption(CONTENT_PARAM, false, "include contents");
		options.addOption(RECURSIVE_PARAM, false, "recursive");
		return options;
	}
	
	private static CommandLine createCommandLine(final String[] args, final Options options) throws ParseException{
		final CommandLineParser parser = new PosixParser();
		final CommandLine cmd = parser.parse(options, args);
		return cmd;
	}
}
