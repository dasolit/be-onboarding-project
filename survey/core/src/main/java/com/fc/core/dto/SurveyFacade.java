package com.fc.core.dto;


import com.fc.core.entity.Survey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class SurveyFacade {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SurveyRequest {
    private String name;
    private String description;
    private List<SurveyItem> items;

    // 1~10개 항목 제한
    public void validate() {
      if (items == null || items.isEmpty()) {
        throw new IllegalArgumentException("설문항목은 최소 1개 이상이어야 합니다.");
      }
      if (items.size() > 10) {
        throw new IllegalArgumentException("설문항목은 최대 10개까지 가능합니다.");
      }
      // 단일/다중 선택 시 options 필수 확인
      items.forEach(item -> {
        switch (item.getType()) {
          case SINGLE, MULTI -> {
            if (item.getOptions() == null || item.getOptions().isEmpty()) {
              throw new IllegalArgumentException("[단일/다중 선택 리스트]의 경우 선택 후보가 필요합니다.");
            }
          }
          default -> { /* SHORT, LONG 은 별도 제한 없음 */ }
        }
      });
    }

    public Survey toSurvey() {
      Survey survey = Survey.builder()
          .name(name)
          .description(description)
          .build();
      return survey;
    }
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SurveyResponse {
    private Long id;
    private String name;
    private String description;
    private List<SurveyItem> items; // 질문 목록
  }
}
