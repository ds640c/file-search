file-search
===========
This is a "homework" assignment with the following requirements:

You will be implementing a program to search for occurrences of regular
expressions within a directory tree (a little like 'grep' and a little like
'find'). The program should accept command-line arguments in the following
form:
 
     [options] <path> <regex>
    
The program should search files in specified path for the specified regex, with
the following options available:
 
     -c  Look for the regex in file contents; otherwise look at the filename.
 
     -r  Walk the directory structure recursively, examining all sub-folders,
         sub-sub-folder, etc. Otherwise just look in the specified directory.
        
Output will simply be the relative path to all files in the directory (or tree)
whose name (or content) matches the regex filter. The program should handle
illegal input in a friendly way.


Requirements
------------
	Java 1.7+
	Maven 3.2+


Implementation
--------------
The project uses Maven for dependency management, building and testing.  Apache Commons IO and Java 7 nio libraries
are used extensively. One would think that given the 'homework' requirements, the 'instructor' intends to see code dealing with recursion and tree walking.  Since this isn't explicitly required, we'll just use the well known libraries to get this done.

Run Tests
------------
To execute all unit tests, at the project root:

    mvn test


Run
---
This would probably be an executable jar file.  
