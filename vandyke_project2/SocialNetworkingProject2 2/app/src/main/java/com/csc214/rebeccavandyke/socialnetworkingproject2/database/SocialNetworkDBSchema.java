package com.csc214.rebeccavandyke.socialnetworkingproject2.database;

/*
* Rebecca Van Dyke
* rvandyke@u.rochester.edu
* CSC 214 Project 2
* TA: Julian Weiss
*/

public class SocialNetworkDBSchema {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "social_network.db";

    public static final class UserTable {
        public static final String NAME = "users";

        public static final class Cols {
            public static final String ID = "id";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String FULLNAME = "full_name";
            public static final String EMAIL = "email";
            public static final String BIRTHDATE = "birthdate";
            public static final String HOMETOWN = "hometown";
            public static final String PHOTO = "photo";
            public static final String BIO = "bio";
            public static final String USERS_FAVORITED = "users_favorited";
        } //end class Cols
    } //end class UserTable

    public static final class PostTable {
        public static final String NAME = "posts";

        public static final class Cols {
            public static final String ID = "id";
            public static final String TIMESTAMP = "timestamp";
            public static final String USER = "user";
            public static final String POST_TEXT ="post_text";
            public static final String POST_PHOTO = "post_photo";
        } //end class Cols
    } //end class PostTable

} //end class SocialNetworkDBSchema
