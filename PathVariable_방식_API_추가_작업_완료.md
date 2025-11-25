# PathVariable 방식 API 엔드포인트 추가 작업 완료

## 📅 작업 일자
2025년 11월 20일

## 🎯 작업 목표
기존 QueryParameter 방식(`/api/reviews?memberId=11`)에 추가로 PathVariable 방식(`/api/reviews/members/11`)을 제공하여 더 RESTful하고 직관적인 API 사용을 지원합니다.

---

## ✅ 추가된 엔드포인트

### 1️⃣ PathVariable 방식 (NEW)
```
GET /api/reviews/members/{memberId}
```

**예시**:
```bash
# 기본 조회
GET http://localhost:8080/api/reviews/members/11

# 페이징
GET http://localhost:8080/api/reviews/members/11?page=0&size=10

# 가게 이름 필터링
GET http://localhost:8080/api/reviews/members/11?storeName=맛집

# 별점 필터링
GET http://localhost:8080/api/reviews/members/11?star=5

# 복합 필터링 + 페이징
GET http://localhost:8080/api/reviews/members/11?storeName=맛집&star=5&page=0&size=10
```

### 2️⃣ QueryParameter 방식 (기존 유지)
```
GET /api/reviews?memberId={memberId}
```

**예시**:
```bash
# 기본 조회
GET http://localhost:8080/api/reviews?memberId=11

# 필터링 + 페이징
GET http://localhost:8080/api/reviews?memberId=11&storeName=맛집&star=5&page=0&size=10
```

---

## 📝 수정된 파일

### 1️⃣ ReviewControllerDocs.java ✅
**위치**: `domain/review/controller/ReviewControllerDocs.java`

**추가 내용**:
```java
@Operation(
    summary = "내가 작성한 리뷰 목록 조회 API (Path Variable)",
    description = "회원 ID를 경로 변수로 받아 해당 회원이 작성한 리뷰 목록을 페이징 처리하여 조회합니다."
)
ApiResponse<ReviewResDTO.ReviewPageDto> getMyReviewsByPath(
    @Parameter(description = "회원 ID", required = true, example = "11")
    Long memberId,
    
    @Parameter(description = "가게 이름 필터 (부분 일치)", example = "맛집")
    String storeName,
    
    @Parameter(description = "별점 필터 (1~5)", example = "5")
    Integer star,
    
    @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
    Pageable pageable
);
```

### 2️⃣ ReviewController.java ✅
**위치**: `domain/review/controller/ReviewController.java`

**추가 내용**:
```java
/**
 * 내가 작성한 리뷰 목록 조회 API (PathVariable 방식)
 */
@GetMapping("/members/{memberId}")
@Override
public ApiResponse<ReviewResDTO.ReviewPageDto> getMyReviewsByPath(
        @PathVariable Long memberId,
        @RequestParam(required = false) String storeName,
        @RequestParam(required = false) Integer star,
        @PageableDefault(size = 10, sort = "createdAt", 
                       direction = Sort.Direction.DESC) Pageable pageable
) {
    Page<Review> reviewPage = reviewQueryService.getMyReviews(memberId, storeName, star, pageable);
    ReviewResDTO.ReviewPageDto response = ReviewResDTO.ReviewPageDto.of(reviewPage);
    return ApiResponse.onSuccess(ReviewSuccessCode.MY_REVIEW_LIST_FOUND, response);
}
```

---

## 🔍 두 방식의 차이점

| 구분 | QueryParameter 방식 | PathVariable 방식 |
|------|-------------------|------------------|
| **URL** | `/api/reviews?memberId=11` | `/api/reviews/members/11` |
| **memberId 위치** | Query String | URL Path |
| **가독성** | 보통 | 높음 ⭐ |
| **RESTful** | 보통 | 높음 ⭐ |
| **추천 용도** | 프로그래밍적 조회 | 일반적인 조회 |

---

## 💡 어느 것을 사용해야 하나요?

### PathVariable 방식 추천 ⭐
```
GET /api/reviews/members/11?page=0&size=10
```
- URL이 더 직관적이고 RESTful
- "회원 11번의 리뷰 목록"이라는 의미가 명확
- 브라우저 주소창에서 쉽게 테스트 가능

### QueryParameter 방식
```
GET /api/reviews?memberId=11&page=0&size=10
```
- 기존 코드와의 호환성 유지
- 여러 조건을 동등하게 취급할 때 유용

---

## 📊 API 명세

### 공통 사항
- **인증**: 추후 JWT 토큰으로 변경 예정
- **기본 페이지 크기**: 10개
- **정렬**: 최신순 (createdAt DESC)

### 요청 파라미터

| 파라미터 | 타입 | 필수 | 기본값 | 설명 |
|---------|------|------|--------|------|
| memberId | Long | ✅ | - | 회원 ID (PathVariable 또는 QueryParam) |
| storeName | String | ❌ | - | 가게 이름 필터 (부분 일치) |
| star | Integer | ❌ | - | 별점 필터 (1~5) |
| page | Integer | ❌ | 0 | 페이지 번호 (0부터 시작) |
| size | Integer | ❌ | 10 | 페이지당 리뷰 개수 |
| sort | String | ❌ | createdAt,DESC | 정렬 기준 |

### 응답 형식 (공통)
```json
{
  "code": "REVIEW200_5",
  "message": "내가 작성한 리뷰 목록 조회에 성공했습니다.",
  "result": {
    "currentPage": 0,
    "totalPages": 3,
    "totalElements": 25,
    "pageSize": 10,
    "hasNext": true,
    "hasPrevious": false,
    "reviews": [
      {
        "reviewId": 1,
        "storeName": "맛있는 식당",
        "star": 4.5,
        "content": "정말 맛있었어요!",
        "createdAt": "2025-11-20T14:30:00",
        "photoCount": 3,
        "replyCount": 2
      }
    ]
  }
}
```

---

## 🧪 테스트 가이드

### Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

### cURL 테스트

#### PathVariable 방식
```bash
# 1. 기본 조회 (회원 11번)
curl -X GET "http://localhost:8080/api/reviews/members/11"

# 2. 페이징 (2페이지, 5개씩)
curl -X GET "http://localhost:8080/api/reviews/members/11?page=1&size=5"

# 3. 가게 이름 필터링
curl -X GET "http://localhost:8080/api/reviews/members/11?storeName=맛집"

# 4. 별점 필터링 (5점만)
curl -X GET "http://localhost:8080/api/reviews/members/11?star=5"

# 5. 복합 조건
curl -X GET "http://localhost:8080/api/reviews/members/11?storeName=맛집&star=5&page=0&size=10"
```

#### QueryParameter 방식
```bash
# 동일한 결과를 다른 방식으로
curl -X GET "http://localhost:8080/api/reviews?memberId=11&storeName=맛집&star=5&page=0&size=10"
```

---

## 🎯 개선 효과

### 1. 사용성 향상 ✅
- URL만 보고도 "회원의 리뷰 목록"임을 쉽게 이해
- `/api/reviews/members/11` → 직관적

### 2. RESTful 설계 준수 ✅
- 리소스 중심의 URL 구조
- 계층적 관계 표현 (리뷰 > 회원의 리뷰)

### 3. 유연성 제공 ✅
- 두 가지 방식 모두 지원
- 사용자가 편한 방식 선택 가능

### 4. 테스트 편의성 ✅
- 브라우저 주소창에서 바로 테스트 가능
- Postman 없이도 간단히 확인 가능

---

## 🔧 기술 구현

### PathVariable vs QueryParameter

```java
// PathVariable: URL 경로의 일부
@GetMapping("/members/{memberId}")
public ApiResponse<...> getMyReviewsByPath(@PathVariable Long memberId) { ... }

// QueryParameter: URL 쿼리 스트링
@GetMapping
public ApiResponse<...> getMyReviews(@RequestParam Long memberId) { ... }
```

### Spring MVC 매핑 우선순위
1. **구체적인 경로 우선**: `/members/{memberId}` 
2. **루트 경로 후순위**: `/`

따라서 두 엔드포인트가 충돌 없이 공존할 수 있습니다.

---

## 📈 사용 시나리오

### 시나리오 1: 간단한 테스트
```
브라우저 주소창에 입력:
http://localhost:8080/api/reviews/members/11

→ PathVariable 방식이 편리!
```

### 시나리오 2: 프로그래밍적 호출
```javascript
// JavaScript에서 동적으로 생성
const params = new URLSearchParams({
  memberId: 11,
  storeName: '맛집',
  star: 5
});
fetch(`/api/reviews?${params}`);

→ QueryParameter 방식이 편리!
```

### 시나리오 3: 북마크/공유
```
사용자가 URL을 저장하거나 공유할 때:
http://localhost:8080/api/reviews/members/11?star=5

→ PathVariable 방식이 더 명확!
```

---

## 📚 REST API 설계 모범 사례

### ✅ 좋은 예 (PathVariable)
```
GET /api/reviews/members/11        # 회원 11의 리뷰 목록
GET /api/stores/5/reviews          # 가게 5의 리뷰 목록
GET /api/reviews/123               # 리뷰 123 상세 조회
```

### ⚠️ 사용 가능 (QueryParameter)
```
GET /api/reviews?memberId=11       # 동작은 하지만 덜 RESTful
GET /api/reviews?filter=member&id=11
```

---

## ✅ 작업 체크리스트

- [x] ReviewControllerDocs에 PathVariable 방식 메서드 추가
- [x] ReviewController에 `/members/{memberId}` 엔드포인트 구현
- [x] Swagger 문서화 완료
- [x] 기존 QueryParameter 방식과 병행 지원
- [x] 에러 검증 완료
- [x] 작업 문서 작성

---

## 🎉 작업 완료!

**작업 일시**: 2025년 11월 20일  
**작업 결과**: 성공 ✅

이제 두 가지 방식으로 리뷰 목록을 조회할 수 있습니다:

### 🌟 PathVariable 방식 (NEW)
```
http://localhost:8080/api/reviews/members/11
```

### 📋 QueryParameter 방식 (기존)
```
http://localhost:8080/api/reviews?memberId=11
```

**추천**: PathVariable 방식을 기본으로 사용하되, 필요에 따라 QueryParameter 방식도 활용하세요!

---

## 🔮 향후 개선 방향

### 1. 인증 적용
```java
@GetMapping("/my-reviews")  // 토큰에서 memberId 자동 추출
public ApiResponse<...> getMyReviews(@AuthenticationPrincipal User user) {
    return reviewQueryService.getMyReviews(user.getId(), ...);
}
```

### 2. 추가 PathVariable 패턴
```java
GET /api/stores/{storeId}/reviews        # 특정 가게의 리뷰
GET /api/reviews/{reviewId}              # 특정 리뷰 상세
GET /api/members/{memberId}/reviews      # 특정 회원의 리뷰 (별도 컨트롤러)
```

### 3. HATEOAS 적용
```json
{
  "reviews": [...],
  "_links": {
    "self": "/api/reviews/members/11?page=0",
    "next": "/api/reviews/members/11?page=1",
    "prev": null
  }
}
```

