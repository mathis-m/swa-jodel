package de.swa.infrastructure.repositories.exceptions;

public class UniqueUserNameRequiredException extends Exception {
    public UniqueUserNameRequiredException(String userName) {
        super("The username '" + userName + "' is not unique!");
    }
}
