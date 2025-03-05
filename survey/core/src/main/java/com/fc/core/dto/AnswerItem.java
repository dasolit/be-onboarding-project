package com.fc.core.dto;

import com.fc.core.constant.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerItem {
  private Long itemId;          // Question ID
  private QuestionType type;    // SHORT, LONG, SINGLE, MULTI

  // 실제 응답값 (단답형/장문형이라면 answer, 단일선택은 하나, 다중선택은 여러개)
  private String answer;        // SHORT, LONG 경우
  private String option;        // SINGLE 경우
  private List<String> optionList; // MULTI 경우
}
