package com.scalablecapital.currencyservice.services;

public class ECBException extends Exception
{

    public ECBException(String message) {
        super(message);
    }

    public ECBException(Exception e) {
        super(e);
    }

}
