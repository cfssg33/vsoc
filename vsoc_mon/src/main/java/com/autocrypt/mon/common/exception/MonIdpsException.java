package com.autocrypt.mon.common.exception;

public class MonIdpsException extends Exception {

    public MonIdpsException() { super(); }

    public MonIdpsException(String message) { super(message); }

    public MonIdpsException(String message, Exception e) { super(message, e); }
}
