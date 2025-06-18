# 🍴 GoodPlace 서비스 프로젝트

## 개요



- ***프로젝트명***: GoodPlace
- ***프로젝트 기간***: 2025-06-12 ~ 2025-06-16
- ***팀원:*** 이혜빈, 이채희, 이주희, 임현우, 김지환
- ***참고 사이트*** : 다이닝코드 (https://www.diningcode.com/)

## **프로젝트 설명**



🔍 회식 장소 고르기, 더 이상 고민하지 마세요!

🎯 카테고리와 위치만 정하면, 랜덤으로 딱 맞는 맛집을 추천해드립니다.

🚀 마이크로서비스 아키텍처로 빠르고 유연하게, 회식의 스트레스를 줄여보세요!

## **목표 및 범위**



이 프로젝트의 목표는 스프링 부트 구조를 이해하고, MSA를 통해 확장성과 유지보수성이 뛰어난
서비스를 구현하는 것이 목표입니다. 

RESTful API를 통해 **일관된 HTTP 기반으로한 인터페이스 사용을 이해하고,** Swagger와 Postman으로 API 명세 자동화 → **개발자 협업 및 테스트 용이성 확보한다**

## **타겟 사용자**



**🧑‍💼 사장님 🧑‍🏫회사원👤관리자**

## **주요 기능 목록**



- **회원 관리 :**  회원 가입, 회원 정보 수정, 회원 탈퇴, 로그인, 리프레시 토큰, 로그 아웃, 인증 및 인가
- **스케줄 관리 :** 회식 생성, 날짜, 확정장소 수정, 리뷰 추가, 회식삭제, 기타 조회
- **장소 관리 :** 회식 장소 관리 기능(추가/조회/수정/삭제)과 스케줄 기반 장소 조회 및 랜덤 선택 기능을 제공
- **리뷰 관리 :** 로그인해서 인증된 사용자의 리뷰 수정, 삭제, 조회, 등록 기능

## **기술 스택**

|  | 사용 기능 |  
| --- | --- |
| 서버 구성 | Spring Boot, Spring Security, JPA, Eureka, Gateway |
| 데이터베이스 | MySQL |
| 개발 도구 | IntelliJ IDEA, Postman |
| API 문서화 도구 | springdoc-openapi 2.8.9(Swagger) |

## 담당 기능


| **이름** | **담당 서비스** | **주요 기능 예시** |
| --- | --- | --- |
| 이채희 | 사용자 관리 (user-management) | - 사용자 정보 생성, 조회, 수정, 삭제 |
| 김지환 | Login & JWT 토큰 | - 로그인/로그아웃, jwt토큰 발급, 검증, 인가 관리 |
| 이주희 | 장소 서비스(Location-service) | - 장소 정보 등록, 조회, 수정, 삭제 |
| 이혜빈 | 일정 서비스(Schedule-service) | - 회식 일정 생성, 조회, 수정, 삭제 |
| 임현우 | 리뷰 서비스(Review-service) | - 리뷰(코멘트) 작성, 조회, 수정, 삭제 |

## 주요 API

<details>
<summary><b>1. 회원가입 API</b></summary>

## 📌 사용자 회원가입 API

새로운 사용자를 회원가입시키는 API입니다.

이미 존재하는 아이디로 요청 시 오류 메시지가 반환됩니다.

---

### ✅ API 개요

- **URL**: `POST /goodplace/user-management/user/register`
- **설명**: 사용자 회원가입 처리
- **요청 형식**: `application/json`
- **응답 형식**: `application/json`

---

### 📨 요청 (Request)

- **Endpoint**: `http://localhost:8000/goodplace/user-management/user/register`
- **HTTP Method**: `POST`

### 📄 Request Body 예시

```json
{
  "username": "user44",
  "password": "pass44",
  "name": "전봉준"
}

```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| username | string | O | 사용자 ID |
| password | string | O | 비밀번호 |
| name | string | O | 사용자 이름 |

---

### 📥 응답 (Response)

### ✅ 회원가입 성공 (201 Created)

```json
{
  "success": true,
  "data": null,
  "errorCode": null,
  "message": null,
  "timestamp": "2025-06-16T15:48:16.9441607"
}

```

- 설명: 회원가입이 정상적으로 완료된 경우 반환됩니다.
- **HTTP Status**: `201 Created`

---

### ❌ 회원가입 실패 (예시 - 중복 ID)

```json
{
  "success": false,
  "data": null,
  "errorCode": "E001",
  "message": "이미 존재하는 아이디입니다.",
  "timestamp": "2025-06-16T16:01:37.17218"
}

```

- 설명: 이미 존재하는 아이디로 회원가입을 시도한 경우 반환됩니다.

---

### 📝 참고 사항

- 성공 시: `success: true`, `HTTP 201 Created`
- 실패 시: `success: false`, 에러 코드(`errorCode`) 및 메시지(`message`) 포함
- 모든 응답에는 `timestamp` 필드가 포함됩니다.

</details>
<details>
<summary><b>2. 로그인 API</b></summary>
  
## 📌 사용자 로그인 API

사용자 로그인을 처리하는 API입니다.

정상적으로 로그인 시 액세스 토큰과 리프레시 토큰이 반환됩니다.

---

## ✅ API 개요

- **URL**: `POST /goodplace/user-management/user/login`
- **설명**: 사용자 로그인 처리
- **요청 형식**: `application/json`
- **응답 형식**: `application/json`

---

## 📨 요청 (Request)

- **Endpoint**: `http://localhost:8000/goodplace/user-management/user/login`
- **HTTP Method**: `POST`

## 📄 Request Body 예시

```json
{
  "username": "user00",
  "password": "pass00"
}
```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| username | string | O | 사용자 ID |
| password | string | O | 비밀번호 |

---

## 📥 응답 (Response)

## ✅ 로그인 성공 (201 Created)

```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
  },
  "errorCode": null,
  "message": null,
  "timestamp": "2025-06-16T14:16:17.2331935"
}
```

- 설명: 로그인에 성공하면 액세스 토큰과 리프레시 토큰이 반환됩니다.
- **HTTP Status**: `201 Created`

---

## ❌ 로그인 실패 예시

```json
{
  "success": false,
  "data": null,
  "errorCode": "E002",
  "message": "아이디 또는 비밀번호가 올바르지 않습니다.",
  "timestamp": "2025-06-16T14:18:20.123456"
}
```

- 설명: 아이디 또는 비밀번호가 올바르지 않은 경우 반환됩니다.

---

## **📝 참고 사항**

- 성공 시: **`success: true`**, **`HTTP 201 Created`**, 토큰 정보 포함
- 실패 시: **`success: false`**, 에러 코드(**`errorCode`**) 및 메시지(**`message`**) 포함
- 모든 응답에는 **`timestamp`** 필드가 포함됩니다.

</details>
<details>
<summary><b>3. 회식 일정 생성 API</b></summary>

## 📌 회식 생성 API

회식 정보를 등록하는 API입니다.

회식을 주최할 사장 ID, 참여자 목록, 장소 후보, 날짜는 필수 항목입니다.

---

### ✅ API 개요

- **URL**: `POST /goodplace/schedule-service/gatherings`
- **설명**: 새로운 회식 정보를 생성합니다.
- **요청 형식**: `application/json`
- **응답 형식**: `application/json`

---

### 📨 요청 (Request)

- **Endpoint**: `http://localhost:8000/goodplace/schedule-service/gatherings`
- **HTTP Method**: `POST`

### 📄 Request Body 예시

```json
{
  "bossId": "회식을 주최하는 사장 ID는 필수입니다.",
  "participantIds": "참여자 ID 목록은 비어있을 수 없습니다.",
  "candidateLocationIds": "회식 장소 후보 ID 목록은 비어있을 수 없습니다.",
  "Date": "회식 날짜는 필수입니다."
}

```

> ⚠️ 위 예시는 유효성 메시지를 보여주는 형식입니다. 실제 사용 시에는 다음과 같은 형태로 전달해야 합니다:
> 

```json
{
  "bossId": 2,
  "participantIds": [101, 102, 103],
  "candidateLocationIds": [201, 202],
  "date": "2025-06-16"
}

```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| bossId | number | O | 회식을 주최하는 사장의 ID |
| participantIds | number[] | O | 참여자들의 사용자 ID 목록 |
| candidateLocationIds | number[] | O | 회식 장소 후보 ID 목록 |
| date | string (YYYY-MM-DD) | O | 회식 날짜 |

---

### 📥 응답 (Response)

### ✅ 성공 (201 Created)

```json
[
  {
    "id": 1,
    "gatheringName": "플레이데이터 백엔드9기 2팀 회식",
    "bossId": 2,
    "confirmedLocationId": 3,
    "participantIds": [101, 102, 103],
    "candidateLocationIds": [201, 202],
    "reviewIds": [301, 302, 100],
    "date": "2025-06-16"
  },
  {
    "id": 2,
    "gatheringName": "플레이데이터 백엔드9기 종강 회식",
    "bossId": 2,
    "confirmedLocationId": null,
    "participantIds": [101, 102, 103],
    "candidateLocationIds": [201, 202],
    "reviewIds": [301, 302, 100],
    "date": "2025-09-10"
  }
]

```

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | number | 생성된 회식의 고유 ID |
| gatheringName | string | 회식 이름 |
| bossId | number | 회식을 주최한 사장의 ID |
| confirmedLocationId | number/null | 최종 확정된 장소 ID (없을 수 있음) |
| participantIds | number[] | 참여자 ID 목록 |
| candidateLocationIds | number[] | 장소 후보 ID 목록 |
| reviewIds | number[] | 리뷰 ID 목록 |
| date | string | 회식 날짜 (YYYY-MM-DD) |

---

### 📝 참고 사항

- 모든 필드는 필수 입력이며, 누락 시 오류가 발생할 수 있습니다.
- 성공적으로 회식이 생성되면 HTTP **201 Created** 응답과 함께 회식 목록이 반환됩니다.
- `confirmedLocationId`는 아직 장소가 확정되지 않았을 경우 `null`로 반환됩니다.

</details>
<details>
<summary><b>4. 리뷰 생성/수정 API</b></summary>

## 📝 리뷰 등록 API

회식 장소에 대한 리뷰를 등록하는 API입니다.

---

### ✅ API 개요

- **URL**: `POST /goodplace/user-management/user/reviews`
- **설명**: 사용자가 회식 일정에 대한 리뷰를 작성합니다.
- **요청 형식**: `application/json`
- **응답 형식**: `application/json`

---

### 📨 요청 (Request)

- **Endpoint**: `http://localhost:8000/goodplace/user-management/user/reviews`
- **HTTP Method**: `POST`

### 📄 Request Body 예시

```json
{
  "reviewId": 101,
  "scheduleId": 202,
  "comment": "분위기 좋고 음식이 맛있어요."
}

```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| reviewId | number | O | 리뷰 고유 ID |
| scheduleId | number | O | 리뷰가 연결된 회식 일정 ID |
| comment | string | O | 리뷰 내용 |

> ⚠️ 참고: 실제 API 사용 시 reviewId는 서버에서 자동 생성하는 구조일 수도 있으므로, API 동작 방식에 따라 클라이언트 입력 여부는 확인이 필요합니다.
> 

---

### 📥 응답 (Response)

### ✅ 성공 (201 Created)

```json
{
  "reviewId": 101,
  "scheduleId": 202,
  "comment": "분위기 좋고 음식이 맛있어요."
}

```

- 설명: 리뷰가 정상적으로 등록되었을 경우 반환됩니다.
- **HTTP Status**: `201 Created`

---

### 📝 참고 사항

- 모든 필드는 필수이며, 누락 시 서버에서 오류가 발생할 수 있습니다.
- 성공 시 등록된 리뷰 정보가 그대로 응답으로 반환됩니다.

</details>

<details>
<summary><b>5.  회식 장소 랜덤 선택 API</b></summary>
  
## 🎲 회식 장소 랜덤 선택 API

지정된 회식 일정(`scheduleId`)에 대해 후보 장소 중 무작위로 하나를 선택합니다.

### ✅ API 개요

- **URL**: `POST /goodplace/location-service/schedule/{scheduleId}/random`
- **설명**: 특정 회식 일정에 대한 랜덤 장소를 선택하여 반환합니다.
- **요청 형식**: `None` (Request Body 없음)
- **응답 형식**: `application/json`


### 📥 요청 (Request)

- **Endpoint**: `http://localhost:8000/goodplace/location-service/schedule/{scheduleId}/random`
- **HTTP Method**: `POST`
- **Path Parameter**:
    - `scheduleId` — 회식 일정의 ID (필수)


### 📤 응답 (Response)

### ✅ 성공 (201 Created)

```json
{
  "id": 11,
  "name": "밥버거",
  "address": "서울시 서초구",
  "priceRange": null,
  "description": null,
  "scheduleId": 1,
  "registeredBy": null,
  "createdAt": "2025-06-16T00:45:30.970684"
}

```

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | number | 장소 고유 ID |
| name | string | 장소 이름 |
| address | string | 장소 주소 |
| priceRange | string/null | 가격대 (없을 수 있음) |
| description | string/null | 장소 설명 (없을 수 있음) |
| scheduleId | number | 연결된 회식 일정 ID |
| registeredBy | string/null | 등록자 ID (없을 수 있음) |
| createdAt | string (datetime) | 생성 시간 |


### 📝 참고 사항

- 요청 본문은 필요하지 않습니다.
- 성공적으로 랜덤 장소가 선택되면 해당 장소 정보가 반환되며, **HTTP 201 Created** 상태 코드가 함께 응답됩니다.
- `null` 필드는 아직 입력되지 않은 값일 수 있습니다.
</details>

## 세부 기능 설명(인터페이스 설계서)



https://docs.google.com/spreadsheets/d/1gH0ItXDzfwnVsz0WWKF3XGTTH1kJHopoUA3AwJfX3WM/edit?gid=0#gid=0



## 회고록 (개발 후)

- 이채희 : 이번 스프링, JPA, RESTful 서버 프로젝트를 진행하면서 코드 작성 자체는 수업시간에 했던 내용과 거의 동일해서 어렵지 않았지만, gateway와 연동하는 부분, 또 각종 서버 오류와 DB 연동 오류를 해결하는 데 시간이 많이 걸렸던 것 같다. 또 Security 설정도 아직 나에겐 복잡한 개념들이 있어서, 추가로 공부할 필요가 있을 것 같다. 그래도 앞으로 웹 서비스를 만드는 데 있어서, 또 최종 파이널 프로젝트를 개발하는데 있어서 가장 기본적이고 중요한 회원 관리 시스템을 맡았던 만큼, 계속 연습해서 파이널 때, 그리고 앞으로 잘 활용해야겠다.

- 이주희 : 이번 프로젝트를 진행하며 JPA와 Lombok, 예외 처리에 대한 이해가 깊어졌습니다. 또한 MSA에서의 요청 흐름을 직접 확인하고 구조를 이해할 수 있었습니다. 다른 서버에서 받은 엔티티의 ID 리스트를 저장하려 할 때 JPA가 원시 컬렉션 타입을 직접 매핑하지 못한다는 점을 깨달았습니다.@ElementCollection을 사용해 별도 테이블로 매핑하는 방법으로 문제를 해결할 수 있었습니다. 또한, @Builder 사용 시 생성자 유무에 따라 빌더 동작 방식이 달라지는 점과, @NoArgsConstructor만 있으면 @Builder를 사용할 때에 에러가 발생하는 이유도 이해하게 되었습니다. 에러 메시지가 너무 길어지는 문제를 개선하기 위해 GlobalExceptionHandler를 추가했고, 필드명과 메시지를 쌍으로 응답하도록 예외 처리를 개선했습니다. Eureka 서버를 통해 서비스들을 등록하고, Gateway를 통해 요청을 라우팅하는 방식으로 서비스 간 통신을 구성하면서, MSA에 대해 배울 수 있었습니다.

- 이혜빈 : 이번 프로젝트를 진행하며 JPA와 Lombok, 예외 처리에 대한 이해가 깊어졌습니다. 또한 MSA에서의 요청 흐름을 직접 확인하고 구조를 이해할 수 있었습니다. 다른 서버에서 받은 엔티티의 ID 리스트를 저장하려 할 때 JPA가 원시 컬렉션 타입을 직접 매핑하지 못한다는 점을 깨달았습니다.@ElementCollection을 사용해 별도 테이블로 매핑하는 방법으로 문제를 해결할 수 있었습니다. 또한, @Builder 사용 시 생성자 유무에 따라 빌더 동작 방식이 달라지는 점과, @NoArgsConstructor만 있으면 @Builder를 사용할 때에 에러가 발생하는 이유도 이해하게 되었습니다. 에러 메시지가 너무 길어지는 문제를 개선하기 위해 GlobalExceptionHandler를 추가했고, 필드명과 메시지를 쌍으로 응답하도록 예외 처리를 개선했습니다. Eureka 서버를 통해 서비스들을 등록하고, Gateway를 통해 요청을 라우팅하는 방식으로 서비스 간 통신을 구성하면서, MSA에 대해 배울 수 있었습니다.

- 임현우 : 이번 서버 프로젝트를 진행하면서 코드를 작성하는 데에는 간단한 CRUD를 이용한 코드라 어렵지 않았지만, Gateway를 이용해 서버를 연결하는 부분에서 시간을 많이 썼다. 특정 코드들만 맞춰주는 간단한 작업이라고 생각했는데, 제대로 이해하지 못해서 그런지 시간이 많이 소요된 것 같다. 그리고 이번 프로젝트에서 중요한 권한 부분을 직접 작성해보지 않아서, 따로 공부해봐야겠다는 생각이 들었다. 이번에도 다사다난한 프로젝트였지만, 팀원들과 함께 소통하며 잘 해결한 것 같아 뜻깊은 경험이었다.

- 김지환 : 이번 스프링, JPA 등을 이용한 백엔드 개발 프로젝트를 하며 주제를 정하고 만드는 것까지 색다른 경험이였고, 무엇보다 시큐리티 부분에서 인가 및 인증 설정 부분에서 생각과는 다르게 기능마다 토큰이 생성, 재발급, 삭제되는 과정을 팀원과의 소통에서 보다 명확하게 배우고 알게 되었습니다. 미숙한 CRUD에서 시간을 많이 쓴 것이 다소 아쉬웠던 부분이였습니다. 테스트까지 완료하였던 것이 지난 프로젝트와는 다르게 마무리까지 할 수 있어 만족스러웠습니다.


## 트러블슈팅 요약

| 항목 번호 | 이슈 제목 | 문제 내용 | 현상 / 추정 원인 | 조치 사항 / 결과 |
|-----------|-----------|-----------|------------------|------------------|
| 1 | DB 연결 오류 | DB 연결이 되지 않음 | DB 연결 불가 | 문제 해결 후 기능 정상 작동 |
| 2 | 회원가입 오류 | 회원가입 시 요청 실패 | 예외 발생 or 응답 없음 | 원인 파악 중, 추가 디버깅 예정 |
| 3 | Entity 연동 오류 | Entity 요소 추가 후 팀원 간 테이블 연동 시 오류 발생 | 관계 설정 오류, 필드 불일치, 모델-DB 스키마 미일치 가능성 | Entity 구조 재정의 및 테이블 매핑 재검토 필요 |
| 4 | 인증/인가 로직 누락 | 토큰 없이 요청이 서버를 통과함 | 인증 필터 또는 미들웨어 작동 안함 | 인증 로직 재점검, 보안 설정 강화 필요 |
| 5 | Develop merge 후 로깅 설정 충돌 | 팀 협업 과정에서 서로 다른 로깅 설정이 충돌하여 운영에 부적합한 설정이 포함됨 | develop에서 pull 받은 과정에서 예상치 못한 DEBUG 로그와 management endpoints가 추가됨 | 보안상 불필요한 management endpoints 제거, DEBUG → INFO 로그 레벨 변경으로 콘솔 가독성 향상 |
