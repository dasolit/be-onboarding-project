package com.fc.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class AnswerFacade {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AnswerRequest {
    private Long surveyId;
    private List<AnswerItem> answerItemList;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AnswerResponse {
    private Long answerId;
    private Long surveyId;
    private List<AnswerItem> answerItemList;
  }
}
