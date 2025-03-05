package com.fc.core.service;

import com.fc.core.dto.SurveyFacade.SurveyRequest;
import com.fc.core.dto.SurveyFacade.SurveyResponse;
import com.fc.core.dto.SurveyItem;
import com.fc.core.entity.Question;
import com.fc.core.entity.Survey;
import com.fc.core.repository.SurveyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

  private final SurveyRepository surveyRepository;

  /**
   * 설문조사 생성
   */
  @Transactional
  public Long save(SurveyRequest request) {
    // 유효성 검사(1~10개, SINGLE/MULTI 시 options 필수)
    request.validate();

    Survey survey = request.toSurvey();

    // question 생성
    if (request.getItems() != null) {
      for (SurveyItem item : request.getItems()) {
        Question question = Question.builder()
            .name(item.getName())
            .description(item.getDescription())
            .type(item.getType())
            .isRequired(item.isRequired())
            .options(item.getOptions())
            .build();
        survey.addQuestion(question);
      }
    }
    surveyRepository.save(survey);
    return survey.getId();
  }

  /**
   * 설문조사 수정
   */
  @Transactional
  public void update(Long id, SurveyRequest request) {
    Survey survey = surveyRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

    // 설문조사 기본정보 수정
    survey.updateSurvey(request.getName(), request.getDescription());

    // (기존 응답 유지) → 기존 질문을 삭제하지 않고, 새 질문 추가
    // 단, 이 로직은 "기존 질문 수정" 시 어떻게 할지 결정 필요.
    // 여기서는 "새로운 질문만 추가"하는 것으로 예시
    if (request.getItems() != null) {
      for (SurveyItem item : request.getItems()) {
        Question question = Question.builder()
            .name(item.getName())
            .description(item.getDescription())
            .type(item.getType())
            .isRequired(item.isRequired())
            .options(item.getOptions())
            .survey(survey)
            .build();
        survey.addQuestion(question);
      }
    }
    // 필요한 경우: 기존 질문과 매핑(추가/변경/삭제)을 구현
  }

  /**
   * 설문조사 조회
   */
  @Transactional(readOnly = true)
  public SurveyResponse findById(Long id) {
    Survey survey = surveyRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Survey not found"));

    // DTO 변환
    List<SurveyItem> items = survey.getQuestions().stream()
        .map(q -> new SurveyItem(
            q.getId(),
            q.getName(),
            q.getDescription(),
            q.isRequired(),
            q.getType(),
            q.getOptions()
        ))
        .toList();

    return new SurveyResponse(
        survey.getId(),
        survey.getName(),
        survey.getDescription(),
        items
    );
  }
}
