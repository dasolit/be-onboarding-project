package com.fc.survey.service;

import com.fc.survey.dto.request.QuestionSubmitDTO;
import com.fc.survey.dto.request.SurveySubmitDTO;
import com.fc.survey.entity.Answer;
import com.fc.survey.entity.AnswerQuestion;
import com.fc.survey.entity.Question;
import com.fc.survey.entity.Survey;
import com.fc.survey.repository.AnswerRepository;
import com.fc.survey.repository.QuestionRepository;
import com.fc.survey.repository.SurveyRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final SurveyRepository surveyRepository;
  private final QuestionRepository questionRepository;

  @Transactional
  public void save(SurveySubmitDTO request) {
    Survey survey = surveyRepository.findById(request.getId()).orElseThrow(
        () -> new IllegalArgumentException("Id를 찾을 수 없습니다."));

    Answer answer = Answer.builder()
        .name(request.getName())
        .survey(survey)
        .build();

    List<AnswerQuestion> questions = new ArrayList<>();
    for(QuestionSubmitDTO question :request.getQuestions()) {
      Question find = questionRepository.findById(question.getId()).orElseThrow(() -> new IllegalArgumentException("id를 찾을 수 없습니다."));
      AnswerQuestion answerQuestion = AnswerQuestion.builder()
          .answer(answer)
          .question(find)
          .response(question.getResponse())
          .build();
      questions.add(answerQuestion);
    }
    answer.setResponses(questions);
    answerRepository.save(answer);
  }
}
