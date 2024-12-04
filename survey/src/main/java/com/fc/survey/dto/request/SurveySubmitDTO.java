package com.fc.survey.dto.request;

import com.fc.survey.entity.Answer;
import com.fc.survey.entity.AnswerQuestion;
import java.util.List;
import lombok.Getter;

@Getter
public class SurveySubmitDTO {

  private Long id;
  private String name;
  private List<QuestionSubmitDTO> questions;

  public Answer toEntity() {
    Answer answer = Answer.builder()
        .name(name)
        .build();
    List<AnswerQuestion> answerQuestions = answerQuestionDTOToEntity(answer);
    answer.setResponses(answerQuestions);
    return answer;
  }
  private List<AnswerQuestion> answerQuestionDTOToEntity(Answer answer) {
    return questions.stream().map(question -> AnswerQuestion.builder()
          .answer(answer)
          .response(question.getResponse())
          .build())
        .toList();
  }
}
