package com.autocrypt.host.exception;

public class HostIdpsException extends Exception {

    public HostIdpsException () { super(); }

    public HostIdpsException (String message) { super(message); }

    public HostIdpsException (String message, Exception e) { super(message, e); }
}
