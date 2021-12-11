package de.swa.infrastructure.repositories.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User could not be found!");
    }
}
