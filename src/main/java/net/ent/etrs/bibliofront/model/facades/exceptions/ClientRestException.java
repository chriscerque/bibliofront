package net.ent.etrs.bibliofront.model.facades.exceptions;

public class ClientRestException extends Exception {
    
    public ClientRestException(String message) {
        super(message);
    }
    
    public ClientRestException(String message, Throwable cause) {
        super(message, cause);
    }
}
