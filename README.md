# [IC2_BE] 최다솔 - 설문조사 서비스 과제

# API 주소

[바로가기](http://localhost:12345/swagger-ui/index.html)

```text
http://localhost:12345/swagger-ui/index.html
```

# Jar

[다운로드](https://drive.google.com/file/d/17a-RaT7cnZzKTQGK79m7zO85JdolVZjR/view?usp=sharing)


## 2. 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 구현

- **대규모 데이터 분산**:  
  설문 응답(`Answer`)이 기하급수적으로 늘어날 수 있으므로, 테이블 **파티셔닝**(예: 날짜 기준 파티션) 또는 **샤딩**(수평 분할) 전략을 고려했습니다. 이를 통해 특정 파티션/샤드에만 쿼리를 수행하여 조회·저장 성능을 향상

- **인덱스(Index) 최적화**:  
  `survey_id`, `question_id` 필드 등에 필수 인덱스를 설정하여, 설문·질문·응답 간 조인을 빠르게 처리합니다. 또한, **카디널리티(Cardinality) 분석**을 통해 불필요한 인덱스는 제거하고, 필요한 인덱스만 유지하여 DB 오버헤드 감소

- **캐싱(Caching) & 무중단 스케일링**:  
  Redis 등 **인메모리 캐시**를 도입하여 자주 조회되는 설문조사 정보를 캐싱하며, 응답 제출 시 캐시 무효화(Invalidate) 정책을 적용합니다. Docker/Kubernetes 환경에서 컨테이너 수를 유연하게 조절해 **서비스 무중단 확장**도 고려


## 3. 다수의 서버, 인스턴스에서 동작할 수 있음을 염두에 둔 구현

- **동시성 및 분산 락 고려**:  
  여러 서버에서 동시에 같은 설문에 응답이 몰릴 수 있으므로, **Optimistic Lock**(버전 필드) 또는 **Pessimistic Lock**(`for update`) 적용
  필요 시 **Redis 기반 분산 락**(Redisson 등)을 사용해 응답 중복 처리나 데이터 정합성 이슈를 해결할 수 있음
