# 🍃 스프링 MVC 활용 - 상품관리 프로젝트
스프링 MVC에 관한 자세한 내용은 [여기](https://thkim610.tistory.com/category/Spring/SpringMVC) 을 참고해주세요.
## 🖥️ Versions

- JDK : Java 11
- Spring Boot : 2.7.15
- BootStrap : 5.0.2

## 📢 Dependancy

- Lombok
- Thymleaf
- Validation

## 🖨요구사항 정의서

- 회원 기능
    - 회원 가입
    - 로그인 / 로그아웃
- 상품 기능
    - 상품 등록
    - 상품 수정
    - 상품 조회
- 기타 요구사항
    - 상품관리는 사용자가 로그인을 해야 사용할 수 있다.
    - 상품의 종류는 도서, 음식, 기타가 있다.
    - 상품을 카테고리로 구분할 수 있다.
    - 상품 주문 시 배송 정보를 입력할 수 있다.

## 📄도메인 모델

### 상품 도메인

| 컬럼명 | 데이터 타입 | 설명 |
| --- | --- | --- |
| id | Long | 상품 번호 |
| itemName | String | 상품 명 |
| price | Integer | 상품 가격 |
| quantity | Integer | 상품 수량 |
| open | boolean | 판매 여부 |
| regions | List<String> | 등록 지역(참조) |
| itemType | ItemType | 상품 종류 |
| deliveryCode | String | 배송 방식 |

### 사용자 도메인

| 컬럼명 | 데이터 타입 | 설명 |
| --- | --- | --- |
| id | Long | 사용자 번호 |
| loginId | String | 로그인 ID |
| password | String | 비밀번호 |
| name | String | 사용자 이름 |

### 배송방식 도메인

| 컬럼명 | 데이터 타입 | 설명 |
| --- | --- | --- |
| code | String | - FAST 
- NOMAL
- SLOW |
| displayName | String | - 빠른 배송(Fast)
- 보통 배송(Normal)
- 느린 배송(Slow) |

### 상품 종류 도메인 (ENUM)

| 설명 | 데이터 타입 |
| --- | --- |
| 도서 | BOOK |
| 음식 | FOOD |
| 기타 | ETC |

## ✒ 목차

### 상품관리 서비스 기획 및 API 만들기

- 도메인 구성
- 레포지토리 구성
- 상품 등록 API
- 상품 목록 조회 API
- 상품 상세 조회 API
- 상품 수정 API

### 상품 관리 화면 구성

- 상품 등록
- 상품 목록
- 상품 상세 조회
- 상품 수정

### 메시지, 국제화 처리

- 뷰에 표시되는 상품 관련 메시지 처리
    - Thymleaf와 messages.properties 활용
- 영어권 사용자를 위한 국제화 처리

### 데이터 유효성 처리

- Validation을 활용하여 데이터 유효성 처리

### 로그인 / 로그아웃 구현

### 파일 업로드, 다운로드

- MultipartFile을 통한 파일 업로드 구현
- 고유의 UUID를 통해 업로드한 파일명을 변환하여 서버에서 고유의 파일명으로 파일관리
- UrlResource를 활용하여 파일 이미지 출력



