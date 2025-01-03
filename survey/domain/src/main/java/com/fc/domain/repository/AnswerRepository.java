package com.fc.domain.repository;

import com.fc.domain.entity.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  List<Answer> findAllBySurveyId(Long id);
}
