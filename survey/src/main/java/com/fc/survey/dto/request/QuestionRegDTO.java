package com.fc.survey.dto.request;


import com.fc.survey.entity.QuestionType;
import lombok.Data;

@Data
public class QuestionRegDTO {

  private String name;

  private String description;

  private QuestionType type;

  private String selectValue;

  private boolean isRequired;

  private boolean isDelete;

}
