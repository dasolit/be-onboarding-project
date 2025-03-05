package com.fc.core.entity;

import com.fc.core.constant.QuestionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 어떤 "응답(Answer)"에 속한 항목인지
  @ManyToOne
  @JoinColumn(name = "answer_id", nullable = false)
  private Answer answer;

  // 어떤 "질문(Question)"에 대한 응답인지
  @ManyToOne
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;

  @Column(nullable = false)
  private String response;      // 실제 답변

  @Enumerated(EnumType.STRING)
  private QuestionType recordedType; // 응답 당시 질문 타입 (기존 응답 유지)
}
