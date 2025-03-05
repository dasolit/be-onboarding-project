package com.fc.core.service;

import com.fc.core.constant.QuestionType;
import com.fc.core.dto.AnswerFacade.AnswerRequest;
import com.fc.core.dto.AnswerFacade.AnswerResponse;
import com.fc.core.dto.AnswerItem;
import com.fc.core.entity.*;
import com.fc.core.repository.AnswerRepository;
import com.fc.core.repository.QuestionRepository;
import com.fc.core.repository.SurveyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

  private final SurveyRepository surveyRepository;
  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;

  /**
   * 설문 응답 제출
   */
  @Transactional
  public Long submitAnswer(Long surveyId, AnswerRequest request) {
    Survey survey = surveyRepository.findById(surveyId)
        .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

    // 새 응답 생성
    Answer answer = Answer.builder()
        .survey(survey)
        .build();

    // 응답 항목 생성
    List<AnswerItemEntity> answerItems = request.getAnswerItemList().stream()
        .map(item -> createAnswerItem(answer, item))
        .toList();

    answer.setAnswerItems(answerItems);
    answerRepository.save(answer);
    return answer.getId();
  }

  private AnswerItemEntity createAnswerItem(Answer answer, AnswerItem item) {
    Question question = questionRepository.findById(item.getItemId())
        .orElseThrow(() -> new EntityNotFoundException("Question not found"));

    // 응답 당시 질문 타입
    QuestionType recordedType = question.getType();

    // 실제 응답 추출 (SHORT/LONG → answer, SINGLE → option, MULTI → optionList)
    String responseValue = extractResponseValue(item);

    return AnswerItemEntity.builder()
        .answer(answer)
        .question(question)
        .response(responseValue)
        .recordedType(recordedType)
        .build();
  }

  private String extractResponseValue(AnswerItem item) {
    return switch (item.getType()) {
      case SHORT -> item.getAnswer(); // 단답형
      case LONG -> item.getAnswer();  // 장문형
      case SINGLE -> item.getOption(); // 단일 선택
      case MULTI -> String.join(",", item.getOptionList() == null ? List.of() : item.getOptionList());
    };
  }

  /**
   * 설문 응답 조회
   */
  @Transactional(readOnly = true)
  public List<AnswerResponse> getSurveyAnswers(Long surveyId) {
    Survey survey = surveyRepository.findById(surveyId)
        .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

    List<Answer> answers = answerRepository.findBySurvey(survey);

    return answers.stream()
        .map(answer -> new AnswerResponse(
            answer.getId(),
            survey.getId(),
            answer.getAnswerItems().stream().map(this::toAnswerItem).toList()
        ))
        .toList();
  }

  private AnswerItem toAnswerItem(AnswerItemEntity entity) {
    // recordedType에 따라 다른 응답 형식을 가진다
    return switch (entity.getRecordedType()) {
      case SHORT -> new AnswerItem(
          entity.getQuestion().getId(),
          entity.getRecordedType(),
          entity.getResponse(), // answer
          null,                 // option
          null                  // optionList
      );
      case LONG -> new AnswerItem(
          entity.getQuestion().getId(),
          entity.getRecordedType(),
          entity.getResponse(), // answer
          null,
          null
      );
      case SINGLE -> new AnswerItem(
          entity.getQuestion().getId(),
          entity.getRecordedType(),
          null, // answer
          entity.getResponse(), // option
          null
      );
      case MULTI -> new AnswerItem(
          entity.getQuestion().getId(),
          entity.getRecordedType(),
          null,
          null,
          List.of(entity.getResponse().split(","))
      );
    };
  }
}
