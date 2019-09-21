## Description

Create an application that maintains a database of books. The user, through a command line interface, should be able to view, add and edit book entries. In addition, a search function should allow the user to find books by keyword. 

- Data types  
•	Book  
•	id – int  
•	title – String  
•	author – String  
•	description – String  

- User functions
•	View all books in the database.  
•	List the ID and title of each book.  
•	Allow the user to see details of a particular book.  
•	Add a new book.  
•	Prompt the user for the book title, author and description.  
•	Save their changes to the database.  
•	Edit an existing book.  
•	Display a list of available books.  
•	Allow the user to select a book to edit.  
•	Display each field, one at a time, and allow them to change the value of the field. The user should be able to leave the value unchanged.   
•	Search for books using keywords.  
•	The search function is up to you to define. Keep the scope small enough that you can accomplish it within the time frame. If you have extra time, you can spend it adding additional features to the search.    

- System functions
•	Write the database of books to disk, upon exiting the application.  
•	Load the database of books from disk, at application start time.  

### Build & Run The Application

```bash
# Clone the repo
git clone https://github.com/devahmedshendy/library-cmd.git

# Change directory to project
cd library-cmd/

# Compile and build the jar file
sh build_jar.sh

# Run the jar file
java -jar library.jar
```

### What Happens When App Starts

* For first time, App will create binary database file in user's home directory called **library.db**.
* Only one instance of this app can run, this is done by checking for a lock file **library.lock** which will be created/deleted when your run/stop.

