package com.anshuit.kanbanhub.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
	private String token;
	private String refreshToken;
}
