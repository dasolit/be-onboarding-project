package com.fc.survey.controller;

import com.fc.survey.dto.ApiResponse;
import com.fc.survey.dto.request.SurveyModDTO;
import com.fc.survey.dto.request.SurveyRegDTO;
import com.fc.survey.dto.response.SurveyModResponse;
import com.fc.survey.dto.response.SurveyRegResponse;
import com.fc.survey.entity.Survey;
import com.fc.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {

  private final SurveyService surveyService;

  @PostMapping
  public ApiResponse<SurveyRegResponse> saveSurvey(@RequestBody SurveyRegDTO request){
    log.info("call saveSurvey: {}", request);
    Survey survey = surveyService.save(request);
    return new ApiResponse<>(
        HttpStatus.CREATED,
        "설문 등록이 완료되었습니다.",
        new SurveyRegResponse(survey.getId(),  survey.getName()));
  }

  @PutMapping
  public ApiResponse<SurveyModResponse> updateSurvey(@RequestBody SurveyModDTO request){
    log.info("call updateSurvey: {}", request);
    Survey survey = surveyService.update(request);
    return new ApiResponse<>(
        HttpStatus.CREATED,
        "설문 수정이 완료되었습니다.",
        new SurveyModResponse(survey.getId(), survey.getName(), survey.getDescription(), survey.getQuestions())
    );
  }


}
