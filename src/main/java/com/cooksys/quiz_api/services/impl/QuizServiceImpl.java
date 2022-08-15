package com.cooksys.quiz_api.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.exceptions.BadRequestException;
import com.cooksys.quiz_api.exceptions.NotFoundException;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;
	private final QuestionRepository questionRepository;
	private final QuestionMapper questionMapper;
	private final QuizMapper quizMapper;

	public Quiz getQuizById(Long id) {
		Optional<Quiz> quiz = quizRepository.findByIdAndDeletedFlagFalse(id);
		if (quiz.isEmpty()) {
			throw new NotFoundException("Quiz not found with id: " + id);
		}
		List<Question> questions = quiz.get().getQuestions();
		questions.removeIf(Question::isDeletedFlag);

		Quiz quizToReturn = quiz.get();
		quizToReturn.setQuestions(questions);
		return quizToReturn;
	}

	private void validateQuizRequest(QuizRequestDto quizRequestDto) {
		Quiz quiz = quizMapper.requestDtoToEntity(quizRequestDto);
		for (Question question : quiz.getQuestions()) {
			if (question.getText() == null) {
				throw new BadRequestException("All your questions must contain text, Check questions on Quiz: " + quiz.getName());
			}
			List<Answer>  answers = question.getAnswers();
			if (answers == null) {
				throw new BadRequestException("Your questions must have a set of answers, Check answers for question: " + question.getText());
			}
			for (Answer answer : answers) {
				if (answer.getText() == null) {
					throw new BadRequestException("All answers must have text, check answers inside of Question: " + question.getText());
				}
			}
		}
		if (quizRequestDto.getName() == null || quiz.getQuestions() == null) {
			throw new BadRequestException("All fields must contain values, Check your Quiz Name or check your questions to make sure they were provided.");
		}
	}

	public Question getQuestionById(Long id) {
		Optional<Question> question = questionRepository.findByIdAndDeletedFlagFalse(id);
		if (question.isEmpty()) {
			throw new NotFoundException("Question not found with id: " + id);
		}
		return question.get();
	}

	private int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}


	@Override
	public List<QuizResponseDto> getAllQuizzes() {
		List<Quiz> quizzes = quizRepository.findAllByDeletedFlagFalse();
		for (Quiz quiz : quizzes) {
			List<Question> questions = quiz.getQuestions();
			questions.removeIf(Question::isDeletedFlag);
			quiz.setQuestions(questions);
		}
		return quizMapper.entitiesToDtos(quizzes);
	}

	@Override
	public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
		validateQuizRequest(quizRequestDto);
		Quiz quiz = quizMapper.requestDtoToEntity(quizRequestDto);
		for (Question question : quiz.getQuestions()) {
			question.setQuiz(quiz);
			for (Answer answer : question.getAnswers()) {
				answer.setQuestion(question);
			}
		}
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));
	}

	@Override
	public QuizResponseDto deleteQuiz(Long id) {
		Quiz quizToDelete = getQuizById(id);
		quizToDelete.setDeletedFlag(true);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToDelete));
	}

	@Override
	public QuizResponseDto renameQuiz(Long id, String newName) {
		Quiz quiz = getQuizById(id);
		quiz.setName(newName);
		quizRepository.saveAndFlush(quiz);
		return quizMapper.entityToDto(quiz);
	}

	@Override
	public QuestionResponseDto randomQuestion(Long id) {
		Quiz quiz = getQuizById(id);
		Collections.shuffle(quiz.getQuestions());
		return questionMapper.entityToDto(quiz.getQuestions().get(getRandomNumber(0, quiz.getQuestions().size())));
	}

	@Override
	public QuizResponseDto modifyQuestion(Long id, QuestionRequestDto questionRequestDto) {
		Quiz quiz = getQuizById(id);
		List<Question> questions = quiz.getQuestions();
		Question question = questionMapper.requestDtoToEntity(questionRequestDto);
		
		for (Answer answer : question.getAnswers()) {
			answer.setQuestion(question);
		}
		question.setQuiz(quiz);
		questions.add(question);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quiz));
	}
	
	@Override
	public QuestionResponseDto deleteQuestion(Long id, Long questionID) {
		Quiz quiz = getQuizById(id);
		List<Question> questions = quiz.getQuestions();
		Question questionForClientResponse = getQuestionById(questionID);	
		
		for (Question question : questions) {
			if (question.getId() == questionID) {
				question.setQuiz(quiz);
				question.setDeletedFlag(true);
			}
		}
		quiz.setQuestions(questions);
		quizRepository.saveAndFlush(quiz);
		return questionMapper.entityToDto(questionForClientResponse);
	}

}
