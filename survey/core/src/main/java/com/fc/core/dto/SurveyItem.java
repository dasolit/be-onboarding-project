package com.fc.core.dto;

import com.fc.core.constant.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Collections;
import java.util.List;

/**
 * [항목 이름], [항목 설명], [항목 입력 형태], [항목 필수 여부], [options(단일/다중 선택)]
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItem {
  private Long id; // 항목 식별자 (수정 시 필요)
  private String name;
  private String description;
  private boolean isRequired;
  private QuestionType type;
  private List<String> options = Collections.emptyList(); // SINGLE, MULTI 일 때만 값 존재
}
