# [IC2_BE] 최다솔 - 설문조사 서비스 과제
# API 요청 & JSON 예시 (단일 블록)

## (1) 설문조사 생성 [POST] /api/v1/survey
```json
{
  "name": "고객 만족도 조사",
  "description": "서비스 이용 고객들의 만족도 파악용 설문입니다.",
  "items": [
    {
      "id": null,
      "name": "이름",
      "description": "사용자의 이름을 입력해주세요.",
      "isRequired": true,
      "type": "SHORT",
      "options": []
    },
    {
      "id": null,
      "name": "가장 만족스러운 기능은?",
      "description": "서비스 기능 중 가장 마음에 드는 것을 선택해주세요.",
      "isRequired": false,
      "type": "SINGLE",
      "options": [
        "속도",
        "가격",
        "디자인"
      ]
    }
  ]
}
```

## (2) 설문조사 수정 [PUT] /api/v1/survey/{surveyId}
```json
{
  "name": "고객 만족도 조사 (수정됨)",
  "description": "조사 설명이 변경되었습니다.",
  "items": [
    {
      "id": 1,
      "name": "이름(수정)",
      "description": "이름 질문을 수정했습니다.",
      "isRequired": true,
      "type": "SHORT",
      "options": []
    },
    {
      "id": null,
      "name": "가격 만족도",
      "description": "새로운 질문을 추가했습니다.",
      "isRequired": false,
      "type": "MULTI",
      "options": [
        "비싸다",
        "적절하다",
        "싸다"
      ]
    }
  ]
}
```
## (3-1) 설문 응답 제출 #1 [POST] /api/v1/answer/{surveyId}
```json
{
  "surveyId": 1,
  "answerItemList": [
    {
      "itemId": 1,
      "type": "SHORT",
      "answer": "홍길동",
      "option": null,
      "optionList": null
    },
    {
      "itemId": 2,
      "type": "SINGLE",
      "answer": null,
      "option": "디자인",
      "optionList": null
    }
  ]
}
```
## (3-2) 설문 응답 제출 #2 [POST] /api/v1/answer/{surveyId}
```json
{
  "surveyId": 1,
  "answerItemList": [
    {
      "itemId": 1,
      "type": "SHORT",
      "answer": "김철수",
      "option": null,
      "optionList": null
    },
    {
      "itemId": 3,
      "type": "MULTI",
      "answer": null,
      "option": null,
      "optionList": [
        "적절하다",
        "싸다"
      ]
    }
  ]
}
```
## (4) 설문 응답 조회 [GET] /api/v1/answer/{surveyId}
```json
[
  {
    "answerId": 10,
    "surveyId": 1,
    "answerItemList": [
      {
        "itemId": 1,
        "type": "SHORT",
        "answer": "홍길동",
        "option": null,
        "optionList": null
      },
      {
        "itemId": 2,
        "type": "SINGLE",
        "answer": null,
        "option": "디자인",
        "optionList": null
      }
    ]
  },
  {
    "answerId": 11,
    "surveyId": 1,
    "answerItemList": [
      {
        "itemId": 1,
        "type": "SHORT",
        "answer": "김철수",
        "option": null,
        "optionList": null
      },
      {
        "itemId": 3,
        "type": "MULTI",
        "answer": null,
        "option": null,
        "optionList": [
          "적절하다",
          "싸다"
        ]
      }
    ]
  }
]
```

# Jar

[다운로드](https://drive.google.com/file/d/1lU3XsaXOGsfxZ_4c9vgLcKVX4KbXEsyB/view?usp=sharing)


## 2. 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 구현

- **대규모 데이터 분산**:  
  설문 응답(`Answer`)이 기하급수적으로 늘어날 수 있으므로, 테이블 **파티셔닝**(예: 날짜 기준 파티션) 또는 **샤딩**(수평 분할) 전략을 고려했습니다. 이를 통해 특정 파티션/샤드에만 쿼리를 수행하여 조회·저장 성능을 향상

- **인덱스(Index) 최적화**:  
  `survey_id`, `question_id` 필드 등에 필수 인덱스를 설정하여, 설문·질문·응답 간 조인을 빠르게 처리, 또한, **카디널리티(Cardinality) 분석**을 통해 불필요한 인덱스는 제거하고, 필요한 인덱스만 유지하여 DB 오버헤드 감소

- **캐싱(Caching) & 무중단 스케일링**:  
  Redis 등 **인메모리 캐시**를 도입하여 자주 조회되는 설문조사 정보를 캐싱하며, 응답 제출 시 캐시 무효화(Invalidate) 정책을 적용, Docker/Kubernetes 환경에서 컨테이너 수를 유연하게 조절해 **서비스 무중단 확장**도 고려


## 3. 다수의 서버, 인스턴스에서 동작할 수 있음을 염두에 둔 구현

- **동시성 및 분산 락 고려**:  
  여러 서버에서 동시에 같은 설문에 응답이 몰릴 수 있으므로, **Optimistic Lock**(버전 필드) 또는 **Pessimistic Lock**(`for update`) 적용
  필요 시 **Redis 기반 분산 락**(Redisson 등)을 사용해 응답 중복 처리나 데이터 정합성 이슈를 해결할 수 있음
