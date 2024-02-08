package com.artostapyshyn.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {
    private String firstName;
    private String lastName;
    private String city;
    private String photoBytes;
}
