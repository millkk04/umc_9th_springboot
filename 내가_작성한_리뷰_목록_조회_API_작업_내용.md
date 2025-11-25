# 내가 작성한 리뷰 목록 조회 API 구현

## 📌 개요
사용자가 자신이 작성한 리뷰 목록을 페이징 처리하여 조회할 수 있는 API를 구현했습니다.

## 🔗 API 엔드포인트
```
GET /api/reviews?memberId={memberId}&page={page}&size={size}
```

### 요청 파라미터
| 파라미터 | 타입 | 필수 | 기본값 | 설명 |
|---------|------|------|--------|------|
| memberId | Long | ✅ | - | 조회할 회원 ID (추후 인증 토큰으로 대체 예정) |
| page | Integer | ❌ | 0 | 페이지 번호 (0부터 시작) |
| size | Integer | ❌ | 10 | 페이지당 리뷰 개수 |
| sort | String | ❌ | createdAt,DESC | 정렬 기준 |

### 응답 예시
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
        "createdAt": "2025-01-15T14:30:00",
        "photoCount": 3,
        "replyCount": 2
      },
      {
        "reviewId": 2,
        "storeName": "분위기 좋은 카페",
        "star": 5.0,
        "content": "커피가 진짜 맛있습니다.",
        "createdAt": "2025-01-14T10:20:00",
        "photoCount": 1,
        "replyCount": 0
      }
      // ... 나머지 8개 리뷰
    ]
  }
}
```

## 📝 수정 및 추가된 파일

### 1. ReviewControllerDocs.java ✨ 완성
**위치**: `domain/review/controller/ReviewControllerDocs.java`

**변경 내용**:
- 기존 미완성 코드를 완전히 재작성
- Swagger 문서화를 위한 어노테이션 추가
- `getMyReviews` 메서드 시그니처 정의

**주요 코드**:
```java
@Tag(name = "Review", description = "리뷰 관련 API")
public interface ReviewControllerDocs {
    
    @Operation(
            summary = "내가 작성한 리뷰 목록 조회 API",
            description = "로그인한 사용자가 작성한 리뷰 목록을 페이징 처리하여 조회합니다. 한 페이지당 10개씩 조회됩니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "내가 작성한 리뷰 목록 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "회원을 찾을 수 없음"
            )
    })
    ApiResponse<ReviewResDTO.ReviewPageDto> getMyReviews(
            @Parameter(description = "회원 ID", required = true) Long memberId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    );
}
```

### 2. ReviewController.java ✅
**위치**: `domain/review/controller/ReviewController.java`

**변경 내용**:
- `ReviewQueryService` 의존성 추가
- `ReviewControllerDocs` 인터페이스 구현
- `GET /api/reviews` 엔드포인트 추가
- Pageable을 사용한 페이징 처리 구현

**주요 코드**:
```java
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewControllerDocs {

    private final ReviewQueryService reviewQueryService;

    /**
     * 내가 작성한 리뷰 목록 조회 API (페이징)
     */
    @GetMapping
    @Override
    public ApiResponse<ReviewResDTO.ReviewPageDto> getMyReviews(
            @RequestParam Long memberId,
            @PageableDefault(size = 10, sort = "createdAt", 
                           direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Review> reviewPage = reviewQueryService.getMyReviews(memberId, pageable);
        ReviewResDTO.ReviewPageDto response = ReviewResDTO.ReviewPageDto.of(reviewPage);
        return ApiResponse.onSuccess(ReviewSuccessCode.MY_REVIEW_LIST_FOUND, response);
    }
}
```

### 3. ReviewResDTO.java ✅
**위치**: `domain/review/dto/res/ReviewResDTO.java`

**변경 내용**:
- `ReviewPageDto` 클래스 추가 (페이징 정보 포함)
- Page 객체를 DTO로 변환하는 정적 팩토리 메서드 구현

**주요 코드**:
```java
/**
 * 페이징 처리된 리뷰 목록 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public static class ReviewPageDto {
    private Integer currentPage;        // 현재 페이지 번호
    private Integer totalPages;         // 전체 페이지 수
    private Long totalElements;         // 전체 리뷰 개수
    private Integer pageSize;           // 페이지당 리뷰 개수
    private Boolean hasNext;            // 다음 페이지 존재 여부
    private Boolean hasPrevious;        // 이전 페이지 존재 여부
    private List<ReviewDto> reviews;    // 리뷰 목록

    public static ReviewPageDto of(Page<Review> reviewPage) {
        return ReviewPageDto.builder()
                .currentPage(reviewPage.getNumber())
                .totalPages(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .pageSize(reviewPage.getSize())
                .hasNext(reviewPage.hasNext())
                .hasPrevious(reviewPage.hasPrevious())
                .reviews(reviewPage.getContent().stream()
                        .map(ReviewDto::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
```

**ReviewDto 필드** (필수 응답값 포함):
- ✅ `storeName`: 가게 이름
- ✅ `star`: 별점
- ✅ `content`: 리뷰 내용
- ✅ `createdAt`: 작성 일시
- `reviewId`: 리뷰 ID (추가)
- `photoCount`: 사진 개수 (추가)
- `replyCount`: 댓글 개수 (추가)

### 4. ReviewSuccessCode.java ✅
**위치**: `domain/review/exception/code/ReviewSuccessCode.java`

**변경 내용**:
- `MY_REVIEW_LIST_FOUND` 성공 코드 추가

**주요 코드**:
```java
MY_REVIEW_LIST_FOUND(HttpStatus.OK,
        "REVIEW200_5",
        "내가 작성한 리뷰 목록 조회에 성공했습니다."),
```

### 5. ReviewQueryService.java ✅
**위치**: `domain/review/service/query/ReviewQueryService.java`

**변경 내용**:
- `getMyReviews` 메서드 시그니처 추가

**주요 코드**:
```java
public interface ReviewQueryService {
    /**
     * 특정 회원이 작성한 리뷰 목록을 페이징 처리하여 조회
     * @param memberId 회원 ID
     * @param pageable 페이징 정보
     * @return 페이징 처리된 리뷰 목록
     */
    Page<Review> getMyReviews(Long memberId, Pageable pageable);
}
```

### 6. ReviewQueryServiceImpl.java ✅
**위치**: `domain/review/service/query/ReviewQueryServiceImpl.java`

**변경 내용**:
- `ReviewQueryService` 인터페이스 구현
- 회원 존재 여부 검증 로직 추가
- 페이징 처리된 리뷰 조회 로직 구현

**주요 코드**:
```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    public Page<Review> getMyReviews(Long memberId, Pageable pageable) {
        // 회원 존재 여부 확인
        memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ReviewErrorCode.MEMBER_NOT_FOUND));

        // 해당 회원이 작성한 리뷰를 페이징 처리하여 조회
        return reviewRepository.findByWriterId(memberId, pageable);
    }
}
```

### 7. ReviewRepository.java ✅
**위치**: `domain/review/repository/ReviewRepository.java`

**변경 내용**:
- 페이징 처리를 위한 `findByWriterId` 메서드 추가

**주요 코드**:
```java
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByWriter_IdOrderByCreatedAtDesc(Long memberId);
    
    Page<Review> findByWriterId(Long memberId, Pageable pageable);
}
```

## 🔧 기술 스택 및 주요 개념

### Spring Data JPA Pagination
- **Pageable**: 페이징 정보를 담는 인터페이스
  - `page`: 페이지 번호 (0부터 시작)
  - `size`: 페이지당 데이터 개수
  - `sort`: 정렬 기준 및 방향

- **Page<T>**: 페이징 결과를 담는 객체
  - `getNumber()`: 현재 페이지 번호
  - `getTotalPages()`: 전체 페이지 수
  - `getTotalElements()`: 전체 데이터 개수
  - `hasNext()`: 다음 페이지 존재 여부
  - `hasPrevious()`: 이전 페이지 존재 여부
  - `getContent()`: 실제 데이터 리스트

### @PageableDefault 어노테이션
```java
@PageableDefault(
    size = 10,                              // 기본 페이지 크기: 10개
    sort = "createdAt",                     // 정렬 기준: 생성일시
    direction = Sort.Direction.DESC         // 정렬 방향: 내림차순 (최신순)
)
```

## 🧪 테스트 방법

### 1. Swagger UI 사용
```
http://localhost:8080/swagger-ui/index.html
```

### 2. 직접 HTTP 요청
```bash
# 첫 번째 페이지 (10개)
GET http://localhost:8080/api/reviews?memberId=1&page=0&size=10

# 두 번째 페이지 (10개)
GET http://localhost:8080/api/reviews?memberId=1&page=1&size=10

# 페이지당 5개씩
GET http://localhost:8080/api/reviews?memberId=1&page=0&size=5
```

## 💡 향후 개선 사항

1. **인증/인가 적용**
   ```java
   // 현재
   @RequestParam Long memberId
   
   // 변경 예정
   @AuthenticationPrincipal UserDetails userDetails
   ```

2. **필터링 기능 추가**
   - 기간별 조회 (startDate, endDate)
   - 별점 필터링 (minStar, maxStar)
   - 가게별 필터링 (storeId)

3. **정렬 옵션 확장**
   - 별점순
   - 댓글 많은 순
   - 사진 많은 순

4. **캐싱 적용**
   ```java
   @Cacheable(value = "myReviews", key = "#memberId + '_' + #pageable.pageNumber")
   ```

## 📊 데이터 흐름

```
Client (Swagger/Postman)
    ↓
Controller (ReviewController)
    ↓ 요청 검증 & 페이징 파라미터 전달
Service (ReviewQueryServiceImpl)
    ↓ 회원 존재 여부 확인
    ↓ 페이징 처리된 리뷰 조회
Repository (ReviewRepository)
    ↓ JPA Query 실행
Database
    ↓ 결과 반환
Service
    ↓ Page<Review> 반환
Controller
    ↓ ReviewPageDto 변환
    ↓ ApiResponse 래핑
Client (응답 수신)
```

## ✅ 체크리스트

- [x] ReviewControllerDocs 완성
- [x] ReviewController에 GET /api/reviews 추가
- [x] ReviewResDTO에 ReviewPageDto 추가
- [x] ReviewSuccessCode에 MY_REVIEW_LIST_FOUND 추가
- [x] ReviewQueryService 인터페이스 정의
- [x] ReviewQueryServiceImpl 구현
- [x] ReviewRepository에 페이징 메서드 추가
- [x] 필수 응답값 포함 확인 (storeName, star, content, createdAt)
- [x] 페이징 처리 (한 페이지당 10개)
- [x] 에러 처리 (회원 없을 경우)
- [x] MD 파일 작성

## 🎉 완료!

내가 작성한 리뷰 목록 조회 API가 성공적으로 구현되었습니다!

