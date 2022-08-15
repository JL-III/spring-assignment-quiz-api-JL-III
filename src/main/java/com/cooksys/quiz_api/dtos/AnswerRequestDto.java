package com.cooksys.quiz_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AnswerRequestDto {
	
	private String text;
	
	private boolean correct;

}
