package com.cooksys.quiz_api.mappers;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.entities.Question;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-28T18:24:38-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public QuestionResponseDto entityToDto(Question entity) {
        if ( entity == null ) {
            return null;
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        questionResponseDto.setId( entity.getId() );
        questionResponseDto.setText( entity.getText() );
        questionResponseDto.setAnswers( answerMapper.entitiesToDtos( entity.getAnswers() ) );

        return questionResponseDto;
    }

    @Override
    public List<QuestionResponseDto> entitiesToDtos(List<Question> entities) {
        if ( entities == null ) {
            return null;
        }

        List<QuestionResponseDto> list = new ArrayList<QuestionResponseDto>( entities.size() );
        for ( Question question : entities ) {
            list.add( entityToDto( question ) );
        }

        return list;
    }

    @Override
    public Question requestDtoToEntity(QuestionRequestDto questionRequestDto) {
        if ( questionRequestDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setText( questionRequestDto.getText() );
        question.setAnswers( answerMapper.answerRequestDtosToEntities( questionRequestDto.getAnswers() ) );

        return question;
    }

    @Override
    public List<Question> questionDtosToEntities(List<QuestionRequestDto> questionRequestDto) {
        if ( questionRequestDto == null ) {
            return null;
        }

        List<Question> list = new ArrayList<Question>( questionRequestDto.size() );
        for ( QuestionRequestDto questionRequestDto1 : questionRequestDto ) {
            list.add( requestDtoToEntity( questionRequestDto1 ) );
        }

        return list;
    }
}
