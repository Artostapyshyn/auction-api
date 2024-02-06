package com.artostapyshyn.auction.dto;

public record UserEditDto(String firstName, String lastName,
                          String city, byte[] photoBytes) {
}
