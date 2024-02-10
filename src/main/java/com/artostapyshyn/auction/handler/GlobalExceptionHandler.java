package com.artostapyshyn.auction.handler;

import com.artostapyshyn.auction.exception.AuctionClosedException;
import com.artostapyshyn.auction.exception.InvalidBidException;
import com.artostapyshyn.auction.exception.UserNotFoundException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidBidException.class)
    public ResponseEntity<Object> handleInvalidBidException(InvalidBidException ex) {
        log.error("Invalid bid", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bid amount must be greater than the current highest bid");
    }

    @ExceptionHandler(AuctionClosedException.class)
    public ResponseEntity<Object> handleAuctionClosedException(AuctionClosedException ex) {
        log.error("Auction is closed", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Auction is closed");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("User not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointer exception", ex);
        return ResponseEntity.internalServerError().body("Authentication error");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex) {
        log.error("JWT error", ex);
        return ResponseEntity.internalServerError().body("JWT error");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException ex) {
        log.error("JWT expired and used again", ex);
        return ResponseEntity.internalServerError().body("JWT expired and used again");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        log.error("Data access error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied error", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied error");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("No handler found for this request", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No handler found for this request");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        log.error("An unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}
