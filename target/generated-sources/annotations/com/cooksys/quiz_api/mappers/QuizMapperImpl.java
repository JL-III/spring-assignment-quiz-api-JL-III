package com.cooksys.quiz_api.mappers;

import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Quiz;
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
public class QuizMapperImpl implements QuizMapper {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public QuizResponseDto entityToDto(Quiz entity) {
        if ( entity == null ) {
            return null;
        }

        QuizResponseDto quizResponseDto = new QuizResponseDto();

        quizResponseDto.setId( entity.getId() );
        quizResponseDto.setName( entity.getName() );
        quizResponseDto.setQuestions( questionMapper.entitiesToDtos( entity.getQuestions() ) );

        return quizResponseDto;
    }

    @Override
    public List<QuizResponseDto> entitiesToDtos(List<Quiz> entities) {
        if ( entities == null ) {
            return null;
        }

        List<QuizResponseDto> list = new ArrayList<QuizResponseDto>( entities.size() );
        for ( Quiz quiz : entities ) {
            list.add( entityToDto( quiz ) );
        }

        return list;
    }

    @Override
    public Quiz requestDtoToEntity(QuizRequestDto quizRequestDto) {
        if ( quizRequestDto == null ) {
            return null;
        }

        Quiz quiz = new Quiz();

        quiz.setName( quizRequestDto.getName() );
        quiz.setQuestions( questionMapper.questionDtosToEntities( quizRequestDto.getQuestions() ) );

        return quiz;
    }
}
