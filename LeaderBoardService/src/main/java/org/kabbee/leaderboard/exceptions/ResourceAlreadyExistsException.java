package org.kabbee.leaderboard.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(String message) {
        super ( message );
    }
}
