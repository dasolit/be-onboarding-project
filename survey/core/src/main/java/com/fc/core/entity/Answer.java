package com.fc.core.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 어떤 설문에 대한 응답인지
  @ManyToOne
  @JoinColumn(name = "survey_id", nullable = false)
  private Survey survey;

  // 그 설문에 대한 여러 답변 항목들
  @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AnswerItemEntity> answerItems;
}
