package de.swa.infrastructure.repositories.exceptions;

public class UniqueExternalIdRequiredException extends Exception {
    public UniqueExternalIdRequiredException(String id) {
        super("The externalId '" + id + "' is not unique!");
    }
}
