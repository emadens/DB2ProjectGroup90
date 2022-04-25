package it.polimi.example.db2test.EJB.Exceptions;

public class CredentialsException extends Exception {
    private static final long serialVersionUID = 1L;

    public CredentialsException(String message) {
        super(message);
    }
}
