package com.fc.api.controller;

import com.fc.core.dto.SurveyFacade.SurveyRequest;
import com.fc.core.dto.SurveyFacade.SurveyResponse;
import com.fc.core.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {

  private final SurveyService surveyService;

  // 설문조사 생성
  @PostMapping
  public ResponseEntity<Long> createSurvey(@RequestBody SurveyRequest request) {
    Long surveyId = surveyService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(surveyId);
  }

  // 설문조사 수정
  @PutMapping("/{id}")
  public ResponseEntity<Void> updateSurvey(@PathVariable Long id, @RequestBody SurveyRequest request) {
    surveyService.update(id, request);
    return ResponseEntity.ok().build();
  }

  // 설문조사 조회
  @GetMapping("/{id}")
  public ResponseEntity<SurveyResponse> getSurvey(@PathVariable Long id) {
    return ResponseEntity.ok(surveyService.findById(id));
  }
}
