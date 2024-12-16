package com.fc.domain.service;

import com.fc.domain.dto.request.SurveyModDTO;
import com.fc.domain.dto.request.SurveyRegDTO;
import com.fc.domain.entity.Survey;
import com.fc.domain.repository.QuestionRepository;
import com.fc.domain.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyService {

  private final SurveyRepository surveyRepository;
  private final QuestionRepository questionRepository;

  @Transactional
  public Survey save(SurveyRegDTO requestDTO) {
    return null;
  }

  @Transactional
  public Survey update(SurveyModDTO request) {
    Survey survey = surveyRepository.findById(request.getId()).orElseThrow(() ->
        new IllegalArgumentException("Id를 찾을 수 없음"));
    survey.setName(request.getName());
    survey.setDescription(request.getDescription());

    /*
    for (Question question : request.toEntity().getQuestions()) {
      if(question.getId() == null) {
        question.setSurvey(survey);
        Question saveQuestion = questionRepository.save(question);
        survey.addQuestion(saveQuestion);
      } else {
        for (Question updateQuestion : survey.getQuestions()) {
          if (updateQuestion.getId().equals(question.getId())) {
            updateQuestion.setName(question.getName());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setSelectValue(question.getSelectValue());
            updateQuestion.setType(question.getType());
            updateQuestion.setDelete(question.isDelete());
            updateQuestion.setRequired(question.isRequired());
            survey.addQuestion(updateQuestion);
            break;
          }
        }
      }
    }
    return surveyRepository.save(survey);
    */
    return null;
  }
}
