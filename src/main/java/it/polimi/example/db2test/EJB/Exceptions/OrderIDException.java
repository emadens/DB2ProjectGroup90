package it.polimi.example.db2test.EJB.Exceptions;

public class OrderIDException extends Exception{
    private static final long serialVersionUID = 1L;

    public OrderIDException(String message) {
        super(message);
    }
}
