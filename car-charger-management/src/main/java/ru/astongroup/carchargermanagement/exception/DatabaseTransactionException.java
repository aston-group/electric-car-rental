package ru.astongroup.carchargermanagement.exception;

public class DatabaseTransactionException extends RuntimeException {
  public DatabaseTransactionException(String message) {
    super(message);
  }
}
