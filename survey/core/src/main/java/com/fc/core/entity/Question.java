package com.fc.core.entity;

import com.fc.core.constant.QuestionType;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;        // 항목 이름

  private String description; // 항목 설명

  @Enumerated(EnumType.STRING)
  private QuestionType type;  // SHORT, LONG, SINGLE, MULTI

  private boolean isRequired; // 필수 여부

  @ElementCollection
  private List<String> options; // 단일/다중 선택 시 선택 후보

  @ManyToOne
  @JoinColumn(name = "survey_id", nullable = false)
  private Survey survey;
}
