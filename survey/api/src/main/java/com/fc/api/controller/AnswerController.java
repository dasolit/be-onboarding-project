package com.fc.api.controller;

import com.fc.core.dto.AnswerFacade.AnswerRequest;
import com.fc.core.dto.AnswerFacade.AnswerResponse;
import com.fc.core.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {

  private final AnswerService answerService;

  // 설문 응답 제출
  @PostMapping("/{surveyId}")
  public ResponseEntity<Long> submitAnswer(@PathVariable Long surveyId, @RequestBody AnswerRequest request) {
    Long answerId = answerService.submitAnswer(surveyId, request);
    return ResponseEntity.ok(answerId);
  }

  // 설문 응답 조회
  @GetMapping("/{surveyId}")
  public ResponseEntity<List<AnswerResponse>> getSurveyAnswers(@PathVariable Long surveyId) {
    return ResponseEntity.ok(answerService.getSurveyAnswers(surveyId));
  }
}
