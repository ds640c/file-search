file-search
===========
This is a programming assignment with the following requirements:

You will be implementing a program to search for occurrences of regular
expressions within a directory tree (a little like 'grep' and a little like
'find'). The program should accept command-line arguments in the following
form:
 
     [options] <path> <regex>
    
The program should search files in specified path for the specified regex, with
the following options available:
 
     -c  Look for the regex in file contents in addition to the filename. Otherwise
     	 just the filename.
 
     -r  Walk the directory structure recursively, examining all sub-folders,
         sub-sub-folder, etc. Otherwise just look in the specified directory.
        
Output will simply be the relative path to all files in the directory (or tree)
whose name (or content) matches the regex filter. The program should handle
illegal input in a friendly way.


Requirements
------------ 
Build:

    Java 1.7+
	Maven 3.2+
	
Run:

    Java 1.7+

Implementation
--------------
The project uses Maven for dependency management, building and testing.  Apache Commons IO and Java 7 nio libraries are used extensively for the file processing and search. Apache Commons CLI library handles the command line parameter parsing.

Given the nature of this assignment, it seems to be an exercise in recursion and tree walking.  Since this isn't explicitly required, I choose to create a project that would most closely resemble what I would develop in a professional setting.  I'm focusing on standardized build and dependency management process, testing, documentation and leveraging well known and tested open source libraries.

Run Tests
------------
To execute all unit tests, at the project root:

    mvn test

Build Executable Jar
--------------------
Run this command to create the jar file in the target directory:

    mvn clean compile assembly:single
 	
Run the Program
---------------
The file-search-executable.jar is available for download at the root level of this project. The params and options can be use when executing the jar. For example:

    java -jar file-search-executable.jar -c -r "/Users/dsun/temp" "^the start\\d{3}"

Documentation
--------------
In addition to this README.MD, Javadocs are available in the doc directory of this project.
