
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Project 2
TA: Julian Weiss

I affirm that I did not give or receive any unauthorized help on this project and that all work is my own.

In completing this project, I referenced the course slides, github repository, and the android developers training resources.

Class designs:
	My model and database classes are each in sub-packages. My model consists of a class that represents a User, a class that represents a Post, and classes that represent collections of each that enable database connectivity. The database classes are laid out in much the same way that was demonstrated in the lectures. I wrote a parent class called MenuActivity that all of my activity classes extend in order to have the same menu available across the application. The only activity that does not extend the MenuActivity is the LoginActivity, as the user cannot access other portions of the application until they are logged in. Each other activity hosts a full screen fragment or recyclerview.

Database schema:
	I used one database with two tables, one for Users and one for Posts. Each table contained a column for each instance variable detailed in my model classes.

Extra Credit:
	I put a substantial amount of effort into making my application somewhat attractive, which I would like to have considered for extra credit. I also think the way I implemented viewing user profiles, as a dialog initiated by a listener on the list recyclerview, is worth consideration. I also think my Admin post at startup is a worthwhile usability enhancement. Lastly, I would like my use of DatePickerDialog to be considered.