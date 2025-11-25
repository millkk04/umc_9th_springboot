# 특정 가게 미션 목록 조회 API 구현 완료

## 📋 작업 개요
- **작업일**: 2025년 11월 20일
- **API 엔드포인트**: `GET /api/stores/{storeId}/missions`
- **기능**: 특정 가게의 미션 목록을 페이징 처리하여 조회

---

## 🔧 구현한 컴포넌트

### 1️⃣ MissionResDTO - 응답 DTO 추가
**파일 경로**: `domain/mission/dto/res/MissionResDTO.java`

#### 추가된 DTO 클래스

##### 1. MissionPageDTO (페이징 응답)
```java
@Getter
@Builder
public static class MissionPageDTO {
    private Integer currentPage;        // 현재 페이지 번호
    private Integer totalPages;         // 총 페이지 수
    private Long totalElements;         // 총 미션 개수
    private Integer pageSize;           // 페이지 크기
    private Boolean hasNext;            // 다음 페이지 존재 여부
    private Boolean hasPrevious;        // 이전 페이지 존재 여부
    private List<MissionDTO> missions;  // 미션 목록
}
```

##### 2. MissionDTO (개별 미션 정보)
```java
@Getter
@Builder
public static class MissionDTO {
    private Long missionId;            // 미션 ID
    private String conditional;        // 미션 조건
    private Integer point;             // 포인트
    private LocalDate deadline;        // 마감일
    private LocalDateTime createdAt;   // 생성일
    private Long participantCount;     // 도전 중인 인원 수
}
```

**특징**:
- `Page<Mission>` 엔티티를 `MissionPageDTO`로 자동 변환하는 정적 팩토리 메서드 제공
- 개별 미션의 도전 인원 수(`participantCount`) 포함
- 페이징 관련 메타데이터 모두 포함

---

### 2️⃣ MissionRepository - 조회 메서드 추가
**파일 경로**: `domain/mission/repository/MissionRepository.java`

```java
/**
 * 특정 가게의 미션 목록 조회 (페이징)
 */
Page<Mission> findByStoreId(Long storeId, Pageable pageable);
```

**특징**:
- Spring Data JPA의 쿼리 메서드 방식 사용
- `Pageable` 파라미터로 페이징 및 정렬 처리
- 자동으로 `WHERE store_id = ?` 쿼리 생성

---

### 3️⃣ MissionQueryService - 인터페이스 추가
**파일 경로**: `domain/mission/service/query/MissionQueryService.java`

```java
/**
 * 특정 가게의 미션 목록 조회 (페이징)
 */
Page<Mission> getMissionsByStore(Long storeId, Integer page, Integer size);
```

**역할**:
- 조회 관련 비즈니스 로직을 담당하는 서비스 인터페이스
- Command(생성/수정/삭제)와 Query(조회)를 분리하는 CQRS 패턴 적용

---

### 4️⃣ MissionQueryServiceImpl - 구현체
**파일 경로**: `domain/mission/service/query/MissionQueryServiceImpl.java`

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    @Override
    public Page<Mission> getMissionsByStore(Long storeId, Integer page, Integer size) {
        // 1. 가게 존재 여부 확인
        if (!storeRepository.existsById(storeId)) {
            throw new StoreException(StoreErrorCode.STORE_NOT_FOUND);
        }

        // 2. 페이징 정보 생성 (최신순 정렬)
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by(Sort.Direction.DESC, "createdAt"));

        // 3. 미션 목록 조회 및 반환
        return missionRepository.findByStoreId(storeId, pageable);
    }
}
```

**주요 로직**:
1. **가게 존재 여부 검증**: 존재하지 않으면 `StoreException` 발생
2. **페이징 설정**: 최신순 정렬 (`createdAt DESC`)
3. **미션 조회**: Repository를 통한 데이터 조회

**특징**:
- `@Transactional(readOnly = true)`: 읽기 전용 트랜잭션으로 성능 최적화
- 명확한 에러 핸들링

---

### 5️⃣ MissionConverter - 변환 메서드 추가
**파일 경로**: `domain/mission/converter/MissionConverter.java`

```java
/**
 * Page<Mission> -> MissionPageDTO 변환
 */
public static MissionResDTO.MissionPageDTO toMissionPageDTO(Page<Mission> missionPage) {
    return MissionResDTO.MissionPageDTO.of(missionPage);
}
```

**역할**:
- 엔티티와 DTO 간의 변환을 담당
- DTO 내부의 정적 팩토리 메서드(`of`)를 활용

---

### 6️⃣ MissionController - GET 엔드포인트 추가
**파일 경로**: `domain/mission/controller/MissionController.java`

```java
/**
 * 특정 가게의 미션 목록 조회 API (페이징)
 */
@GetMapping("/api/stores/{storeId}/missions")
public ApiResponse<MissionResDTO.MissionPageDTO> getMissionsByStore(
        @PathVariable Long storeId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size
) {
    Page<Mission> missionPage = missionQueryService.getMissionsByStore(storeId, page, size);
    MissionResDTO.MissionPageDTO response = MissionConverter.toMissionPageDTO(missionPage);
    return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_RETRIEVED, response);
}
```

**파라미터**:
- `storeId` (PathVariable): 조회할 가게의 ID
- `page` (QueryParam): 페이지 번호 (기본값: 0)
- `size` (QueryParam): 페이지 크기 (기본값: 10)

**처리 흐름**:
1. 쿼리 서비스를 통해 미션 목록 조회
2. Converter를 통해 DTO로 변환
3. 성공 응답 반환

---

### 7️⃣ MissionSuccessCode - 성공 코드 추가
**파일 경로**: `domain/mission/exception/code/MissionSuccessCode.java`

```java
MISSION_LIST_RETRIEVED(HttpStatus.OK,
        "MISSION200_7",
        "특정 가게의 미션 목록 조회에 성공했습니다."),
```

**용도**: API 응답의 성공 메시지 및 코드 제공

---

## 📡 API 사용 방법

### 요청 예시

#### 1. 기본 요청 (기본 페이징)
```http
GET /api/stores/1/missions
```

#### 2. 페이징 파라미터 지정
```http
GET /api/stores/1/missions?page=0&size=5
```

#### 3. 두 번째 페이지 조회
```http
GET /api/stores/1/missions?page=1&size=10
```

---

### 응답 예시

#### ✅ 성공 응답 (200 OK)
```json
{
  "isSuccess": true,
  "code": "MISSION200_7",
  "message": "특정 가게의 미션 목록 조회에 성공했습니다.",
  "result": {
    "currentPage": 0,
    "totalPages": 3,
    "totalElements": 25,
    "pageSize": 10,
    "hasNext": true,
    "hasPrevious": false,
    "missions": [
      {
        "missionId": 25,
        "conditional": "3회 이상 방문",
        "point": 3000,
        "deadline": "2025-12-31",
        "createdAt": "2025-11-20T15:30:00",
        "participantCount": 12
      },
      {
        "missionId": 24,
        "conditional": "5만원 이상 주문",
        "point": 5000,
        "deadline": "2025-12-15",
        "createdAt": "2025-11-19T10:20:00",
        "participantCount": 8
      }
      // ... 나머지 미션들
    ]
  }
}
```

#### ❌ 에러 응답 - 가게를 찾을 수 없음 (404 Not Found)
```json
{
  "isSuccess": false,
  "code": "STORE_4004",
  "message": "존재하지 않는 가게입니다.",
  "result": null
}
```

---

## 🔍 응답 필드 설명

### 페이징 메타데이터
| 필드 | 타입 | 설명 |
|-----|------|------|
| `currentPage` | Integer | 현재 페이지 번호 (0부터 시작) |
| `totalPages` | Integer | 전체 페이지 수 |
| `totalElements` | Long | 전체 미션 개수 |
| `pageSize` | Integer | 페이지당 미션 개수 |
| `hasNext` | Boolean | 다음 페이지 존재 여부 |
| `hasPrevious` | Boolean | 이전 페이지 존재 여부 |

### 미션 정보 (missions[])
| 필드 | 타입 | 설명 |
|-----|------|------|
| `missionId` | Long | 미션 ID |
| `conditional` | String | 미션 조건 (예: "3회 이상 방문") |
| `point` | Integer | 미션 완료 시 획득 포인트 |
| `deadline` | LocalDate | 미션 마감일 |
| `createdAt` | LocalDateTime | 미션 생성일시 |
| `participantCount` | Long | 현재 도전 중인 회원 수 |

---

## 🎯 주요 기능

### 1. 페이징 처리
- 기본값: `page=0`, `size=10`
- 쿼리 파라미터로 커스터마이징 가능
- 페이징 메타데이터 포함 (다음/이전 페이지 여부 등)

### 2. 정렬
- 최신순 정렬 (생성일 기준 내림차순)
- `ORDER BY created_at DESC`

### 3. 도전 인원 수 표시
- 각 미션에 도전 중인 회원 수를 함께 반환
- `MemberMission` 관계를 통해 집계

### 4. 예외 처리
- 존재하지 않는 가게 ID 입력 시: `StoreException` 발생
- 적절한 에러 메시지와 상태 코드 반환

---

## 🧪 테스트 시나리오

### 1. 정상 케이스
```bash
# 가게 ID 1의 미션 목록 조회
curl -X GET "http://localhost:8080/api/stores/1/missions"
```

### 2. 페이징 커스터마이징
```bash
# 페이지 크기 5로 설정
curl -X GET "http://localhost:8080/api/stores/1/missions?page=0&size=5"
```

### 3. 두 번째 페이지 조회
```bash
curl -X GET "http://localhost:8080/api/stores/1/missions?page=1&size=10"
```

### 4. 존재하지 않는 가게
```bash
# 404 에러 발생 예상
curl -X GET "http://localhost:8080/api/stores/999999/missions"
```

---

## 📊 데이터 흐름

```
1. Client
   ↓ GET /api/stores/{storeId}/missions?page=0&size=10
   
2. MissionController
   ↓ getMissionsByStore(storeId, page, size)
   
3. MissionQueryService
   ↓ 가게 존재 확인 → 페이징 설정 → 미션 조회
   
4. MissionRepository
   ↓ findByStoreId(storeId, pageable)
   
5. Database
   ↓ SELECT * FROM mission WHERE store_id = ? ORDER BY created_at DESC
   
6. Page<Mission> 반환
   ↓ MissionConverter.toMissionPageDTO()
   
7. MissionPageDTO 생성
   ↓ ApiResponse.onSuccess()
   
8. Client에게 JSON 응답
```

---

## 🔄 CQRS 패턴 적용

이번 구현에서 **CQRS (Command Query Responsibility Segregation)** 패턴을 적용했습니다:

### Command (명령)
- **MissionCommandService**: 생성, 수정, 삭제 등 데이터 변경 작업
- 예: `createMission()`, `challengeMission()`

### Query (조회)
- **MissionQueryService**: 데이터 조회 작업
- 예: `getMissionsByStore()`
- `@Transactional(readOnly = true)`로 최적화

**장점**:
- 명확한 책임 분리
- 조회 성능 최적화 가능
- 코드 가독성 향상

---

## 🎨 설계 원칙

### 1. RESTful API 설계
- 리소스 계층 구조: `/stores/{storeId}/missions`
- HTTP 메서드 의미: `GET` = 조회
- 기존 미션 생성 API(`POST`)와 일관성 유지

### 2. DTO 변환 패턴
- 엔티티를 직접 반환하지 않음
- DTO를 통한 응답 데이터 제어
- 필요한 정보만 선택적으로 노출

### 3. 예외 처리
- 도메인별 커스텀 예외 사용 (`StoreException`)
- 명확한 에러 메시지 제공

### 4. 페이징 처리
- 대량 데이터 조회 시 성능 최적화
- 클라이언트 친화적인 메타데이터 제공

---

## 📝 추가 개선 가능 사항

### 1. 필터링 기능 (선택적 구현)
- 진행 중/마감된 미션 구분
- 포인트 범위 필터링
- 마감일 기준 필터링

예시:
```http
GET /api/stores/1/missions?status=ACTIVE&minPoint=1000
```

### 2. 정렬 옵션
- 포인트 높은 순/낮은 순
- 마감일 임박 순
- 인기 순 (참여자 수 기준)

예시:
```http
GET /api/stores/1/missions?sort=point,desc
```

### 3. 검색 기능
- 미션 조건 키워드 검색
- 포인트 범위 검색

---

## ✅ 체크리스트

- [x] MissionResDTO에 목록 응답 DTO 추가
- [x] MissionRepository에 조회 메서드 추가
- [x] MissionQueryService 인터페이스 작성
- [x] MissionQueryServiceImpl 구현
- [x] MissionConverter에 변환 메서드 추가
- [x] MissionController에 GET 메서드 추가
- [x] MissionSuccessCode에 성공 코드 추가
- [x] 페이징 처리 구현
- [x] 에러 핸들링 구현
- [x] 문서 작성

---

## 🎉 구현 완료!

특정 가게의 미션 목록 조회 API가 성공적으로 구현되었습니다. 
페이징, 정렬, 예외 처리 등 모든 기능이 포함되어 있으며, RESTful 설계 원칙을 준수했습니다.

