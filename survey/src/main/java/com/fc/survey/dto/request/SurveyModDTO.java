package com.fc.survey.dto.request;

import com.fc.survey.entity.Question;
import com.fc.survey.entity.Survey;
import java.util.List;
import lombok.Data;

@Data
public class SurveyModDTO {

  private Long id;

  private String name;

  private String description;

  private List<QuestionModDTO> questions;

  public Survey toEntity() {
    Survey survey = Survey.builder()
        .id(id)
        .name(name)
        .description(description)
        .build();
    List<Question> questionEntities = questionDTOToEntity(survey);
    survey.setQuestions(questionEntities);
    return survey;
  }

  private List<Question> questionDTOToEntity(Survey survey) {
    return questions.stream().map(questionDTO -> Question.builder()
            .id(questionDTO.getId())
            .name(questionDTO.getName())
            .description(questionDTO.getDescription())
            .type(questionDTO.getType())
            .selectValue(questionDTO.getSelectValue())
            .isRequired(questionDTO.isRequired())
            .isDelete(questionDTO.isDelete())
            .survey(survey) // 부모 Survey 설정
            .build())
        .toList();
  }
}
