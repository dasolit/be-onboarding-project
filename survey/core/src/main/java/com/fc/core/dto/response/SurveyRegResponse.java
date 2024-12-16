package com.fc.core.dto.response;

import lombok.Getter;

@Getter
public class SurveyRegResponse {

  private final Long id;
  private final String name;

  public SurveyRegResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
