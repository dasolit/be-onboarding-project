package com.fc.core.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private String description;

  // 설문에 속한 질문들
  @Builder.Default
  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Question> questions = new ArrayList<>();

  // 설문에 대한 응답들
  @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Answer> answers = new ArrayList<>();

  public void updateSurvey(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void addQuestion(Question question) {
    this.questions.add(question);
    question.setSurvey(this);
  }

  public void addAnswer(Answer answer) {
    this.answers.add(answer);
    answer.setSurvey(this);
  }
}
