package com.fc.survey.controller;

import com.fc.survey.dto.ApiResponse;
import com.fc.survey.dto.request.SurveySubmitDTO;
import com.fc.survey.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {

  private final AnswerService answerService;

  @PostMapping
  public ApiResponse<Object> saveAnswer(@RequestBody SurveySubmitDTO request){
    log.info("call saveAnswer: {}", request);
    answerService.save(request);
    return new ApiResponse<>(
        HttpStatus.CREATED,
        "설문 등록이 완료되었습니다.",
        null);
  }
}
