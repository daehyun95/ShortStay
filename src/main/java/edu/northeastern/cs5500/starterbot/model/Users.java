package edu.northeastern.cs5500.starterbot.model;

// Class containintg the information for a simple java object: Users
import lombok.EqualsAndHashCode;

/*
 * Users class is a class that extends users, the users of this application
 * are expected to be interacting as users, but are also expected to be
 * students with a .edu email/
 */
@EqualsAndHashCode(callSuper = true)
public class Users extends Students {}
