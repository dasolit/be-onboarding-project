package com.fc.core.repository;

import com.fc.core.entity.Answer;
import com.fc.core.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
  List<Answer> findBySurvey(Survey survey);
}
