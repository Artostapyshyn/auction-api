package com.artostapyshyn.auction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BidNotFoundException extends RuntimeException {
    public BidNotFoundException() {
        super("Bid not found");
    }
}
