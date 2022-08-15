package com.cooksys.quiz_api.mappers;

import com.cooksys.quiz_api.dtos.AnswerRequestDto;
import com.cooksys.quiz_api.dtos.AnswerResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-28T18:24:38-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public AnswerResponseDto entityToDto(Answer entity) {
        if ( entity == null ) {
            return null;
        }

        AnswerResponseDto answerResponseDto = new AnswerResponseDto();

        answerResponseDto.setId( entity.getId() );
        answerResponseDto.setText( entity.getText() );

        return answerResponseDto;
    }

    @Override
    public List<AnswerResponseDto> entitiesToDtos(List<Answer> entities) {
        if ( entities == null ) {
            return null;
        }

        List<AnswerResponseDto> list = new ArrayList<AnswerResponseDto>( entities.size() );
        for ( Answer answer : entities ) {
            list.add( entityToDto( answer ) );
        }

        return list;
    }

    @Override
    public Answer responseDtoToEntity(AnswerResponseDto answerResposneDto) {
        if ( answerResposneDto == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setId( answerResposneDto.getId() );
        answer.setText( answerResposneDto.getText() );

        return answer;
    }

    @Override
    public List<Answer> answerRequestDtosToEntities(List<AnswerRequestDto> answers) {
        if ( answers == null ) {
            return null;
        }

        List<Answer> list = new ArrayList<Answer>( answers.size() );
        for ( AnswerRequestDto answerRequestDto : answers ) {
            list.add( answerRequestDtoToAnswer( answerRequestDto ) );
        }

        return list;
    }

    protected Answer answerRequestDtoToAnswer(AnswerRequestDto answerRequestDto) {
        if ( answerRequestDto == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setText( answerRequestDto.getText() );
        answer.setCorrect( answerRequestDto.isCorrect() );

        return answer;
    }
}
