# 내가 진행 중인 미션 목록 조회 API 구현 완료

## API 개요
- **API PATH**: `GET /api/members/{memberId}/missions`
- **설명**: 특정 회원이 현재 진행 중인 미션 목록을 페이징하여 조회합니다.
- **응답 코드**: `MISSION200_8` - "내가 진행 중인 미션 목록 조회에 성공했습니다."

---

## 구현 순서 및 작성한 코드

### 1. MemberMissionRepository 생성
**파일 경로**: `domain/member/repository/MemberMissionRepository.java`

```java
package com.example.umc_9th_final_5th.domain.member.repository;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    /**
     * 특정 회원의 진행 중인 미션 목록 조회 (페이징)
     */
    Page<MemberMission> findByMemberId(Long memberId, Pageable pageable);

    /**
     * 특정 회원이 특정 미션을 이미 도전 중인지 확인
     */
    boolean existsByMemberIdAndMissionId(Long memberId, Long missionId);
}
```

**역할**:
- `MemberMission` 엔티티에 대한 데이터 접근 계층
- 회원 ID로 미션 목록을 페이징 조회하는 메서드 제공
- 중복 도전 방지를 위한 존재 여부 확인 메서드 제공 (향후 사용 예정)

---

### 2. MissionResDTO - 응답 DTO 추가
**파일 경로**: `domain/mission/dto/res/MissionResDTO.java`

**추가된 내부 클래스**:

#### (1) MyMissionPageDTO
```java
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public static class MyMissionPageDTO {
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageSize;
    private Boolean hasNext;
    private Boolean hasPrevious;
    private List<MyMissionDTO> missions;

    public static MyMissionPageDTO of(Page<MyMissionDTO> missionPage) {
        return MyMissionPageDTO.builder()
                .currentPage(missionPage.getNumber())
                .totalPages(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .pageSize(missionPage.getSize())
                .hasNext(missionPage.hasNext())
                .hasPrevious(missionPage.hasPrevious())
                .missions(missionPage.getContent())
                .build();
    }
}
```

**역할**: 페이징된 미션 목록 전체 정보를 담는 래퍼 DTO

#### (2) MyMissionDTO
```java
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public static class MyMissionDTO {
    private Long memberMissionId;      // 회원-미션 매핑 ID
    private Long missionId;             // 미션 ID
    private String storeName;           // 가게 이름
    private String conditional;         // 미션 조건
    private Integer point;              // 보상 포인트
    private LocalDate deadline;         // 마감일
    private Boolean isComplete;         // 완료 여부
    private LocalDateTime startedAt;    // 시작 시간
}
```

**역할**: 개별 미션 정보를 담는 DTO (회원이 진행 중인 미션 정보)

---

### 3. MissionConverter - 변환 메서드 추가
**파일 경로**: `domain/mission/converter/MissionConverter.java`

**추가된 메서드**:

#### (1) toMyMissionDTO
```java
public static MissionResDTO.MyMissionDTO toMyMissionDTO(MemberMission memberMission) {
    Mission mission = memberMission.getMission();
    
    return MissionResDTO.MyMissionDTO.builder()
            .memberMissionId(memberMission.getId())
            .missionId(mission.getId())
            .storeName(mission.getStore().getName())
            .conditional(mission.getConditional())
            .point(mission.getPoint())
            .deadline(mission.getDeadline())
            .isComplete(memberMission.getIsComplete())
            .startedAt(memberMission.getCreatedAt())
            .build();
}
```

**역할**: `MemberMission` 엔티티를 `MyMissionDTO`로 변환

#### (2) toMyMissionPageDTO
```java
public static MissionResDTO.MyMissionPageDTO toMyMissionPageDTO(Page<MemberMission> memberMissionPage) {
    Page<MissionResDTO.MyMissionDTO> myMissionDTOPage = 
        memberMissionPage.map(MissionConverter::toMyMissionDTO);
    return MissionResDTO.MyMissionPageDTO.of(myMissionDTOPage);
}
```

**역할**: `Page<MemberMission>`을 `MyMissionPageDTO`로 변환

---

### 4. MissionQueryService - 인터페이스에 메서드 추가
**파일 경로**: `domain/mission/service/query/MissionQueryService.java`

**추가된 메서드**:
```java
/**
 * 회원이 진행 중인 미션 목록 조회 (페이징)
 * @param memberId 회원 ID
 * @param page 페이지 번호 (0부터 시작)
 * @param size 페이지 크기
 * @return 페이징된 MemberMission 목록
 */
Page<MemberMission> getMyMissions(Long memberId, Integer page, Integer size);
```

**역할**: 회원이 진행 중인 미션을 조회하는 서비스 인터페이스 정의

---

### 5. MissionQueryServiceImpl - 서비스 구현
**파일 경로**: `domain/mission/service/query/MissionQueryServiceImpl.java`

**추가된 의존성**:
```java
private final MemberRepository memberRepository;
private final MemberMissionRepository memberMissionRepository;
```

**추가된 메서드 구현**:
```java
@Override
public Page<MemberMission> getMyMissions(Long memberId, Integer page, Integer size) {
    // 1. 회원 존재 여부 확인
    if (!memberRepository.existsById(memberId)) {
        throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
    }

    // 2. 페이징 정보 생성 (최신순 정렬)
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

    // 3. 회원이 진행 중인 미션 목록 조회 및 반환
    return memberMissionRepository.findByMemberId(memberId, pageable);
}
```

**로직 설명**:
1. **회원 검증**: 존재하지 않는 회원이면 `MemberException` 발생
2. **페이징 설정**: 최신순(createdAt 내림차순) 정렬
3. **미션 조회**: Repository를 통해 회원의 미션 목록 조회

---

### 6. MissionSuccessCode - 성공 코드 추가
**파일 경로**: `domain/mission/exception/code/MissionSuccessCode.java`

**추가된 코드**:
```java
MY_MISSION_LIST_RETRIEVED(HttpStatus.OK,
        "MISSION200_8",
        "내가 진행 중인 미션 목록 조회에 성공했습니다."),
```

**역할**: API 성공 시 반환할 응답 코드 및 메시지 정의

---

### 7. MissionController - 컨트롤러 엔드포인트 추가
**파일 경로**: `domain/mission/controller/MissionController.java`

**추가된 메서드**:
```java
/**
 * 내가 진행 중인 미션 목록 조회 API (페이징)
 * @param memberId 회원 ID (PathVariable)
 * @param page 페이지 번호 (기본값: 0)
 * @param size 페이지 크기 (기본값: 10)
 * @return 페이징된 내 미션 목록
 */
@GetMapping("/api/members/{memberId}/missions")
public ApiResponse<MissionResDTO.MyMissionPageDTO> getMyMissions(
        @PathVariable Long memberId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size
) {
    Page<MemberMission> memberMissionPage = missionQueryService.getMyMissions(memberId, page, size);
    MissionResDTO.MyMissionPageDTO response = MissionConverter.toMyMissionPageDTO(memberMissionPage);
    return ApiResponse.onSuccess(MissionSuccessCode.MY_MISSION_LIST_RETRIEVED, response);
}
```

**역할**: HTTP 요청을 받아 서비스 호출 후 응답 반환

---

## API 사용법

### 요청 예시
```http
GET /api/members/1/missions?page=0&size=10
```

### 쿼리 파라미터
- `page` (선택, 기본값: 0): 페이지 번호 (0부터 시작)
- `size` (선택, 기본값: 10): 한 페이지당 조회할 미션 수

### 응답 예시
```json
{
  "isSuccess": true,
  "code": "MISSION200_8",
  "message": "내가 진행 중인 미션 목록 조회에 성공했습니다.",
  "result": {
    "currentPage": 0,
    "totalPages": 2,
    "totalElements": 15,
    "pageSize": 10,
    "hasNext": true,
    "hasPrevious": false,
    "missions": [
      {
        "memberMissionId": 1,
        "missionId": 5,
        "storeName": "맛있는 떡볶이",
        "conditional": "떡볶이 3회 구매",
        "point": 1000,
        "deadline": "2025-12-31",
        "isComplete": false,
        "startedAt": "2025-11-20T10:30:00"
      },
      {
        "memberMissionId": 2,
        "missionId": 8,
        "storeName": "치킨마루",
        "conditional": "치킨 5회 구매",
        "point": 2000,
        "deadline": "2025-12-25",
        "isComplete": false,
        "startedAt": "2025-11-18T14:20:00"
      }
      // ...더 많은 미션
    ]
  }
}
```

---

## 주요 특징

1. **페이징 지원**: 대량의 미션 데이터를 효율적으로 조회
2. **최신순 정렬**: 최근에 시작한 미션부터 조회
3. **상세 정보 제공**: 가게 이름, 미션 조건, 보상, 완료 여부 등 포함
4. **에러 처리**: 존재하지 않는 회원에 대한 예외 처리
5. **유연한 페이지 크기**: 쿼리 파라미터로 조정 가능

---

## 데이터 흐름

```
Client 
  → MissionController.getMyMissions()
    → MissionQueryService.getMyMissions()
      → MemberRepository.existsById() (회원 검증)
      → MemberMissionRepository.findByMemberId() (미션 조회)
    → MissionConverter.toMyMissionPageDTO() (DTO 변환)
  → ApiResponse 반환
```

---

## 구현 완료 체크리스트
- ✅ MemberMissionRepository 생성
- ✅ MyMissionPageDTO, MyMissionDTO 추가
- ✅ MissionConverter에 변환 메서드 추가
- ✅ MissionQueryService 인터페이스 확장
- ✅ MissionQueryServiceImpl 구현
- ✅ MissionSuccessCode 추가
- ✅ MissionController에 엔드포인트 추가
- ✅ 에러 검증 완료

---

## 향후 개선 가능 사항
1. **필터링 기능**: 완료/미완료 미션 구분 조회
2. **정렬 옵션**: 마감일순, 포인트순 등 다양한 정렬 기준
3. **검색 기능**: 가게 이름이나 미션 조건으로 검색
4. **통계 정보**: 완료율, 총 획득 포인트 등 추가

