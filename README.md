# Cafer

## 개요

Cafer는 사용자의 위치를 기반으로 적절한 카페를 추천하는 웹 애플리케이션입니다. 다양한 원두(산미), 공부하기 좋은 카페(프랜차이즈), 테이크 아웃이 빠른 카페(프랜차이즈), 대형 카페 등의 조건을 고려하여 카페를 큐레이팅합니다. SEO를 통해 웹 트래픽을 수집하고, 각 카페에 대한 상세 페이지를 제공하여 사용자에게 더욱 세세한 큐레이팅을 제공합니다.

## 핵심 기능

- **위치 기반 카페 추천**: 사용자의 위치를 기반으로 적절한 카페를 추천합니다.
- **노출 요건**:
  - 다양한 원두(산미)
  - 공부하기 좋은 카페(프랜차이즈)
  - 테이크 아웃이 빠른 카페(프랜차이즈)
  - 대형 카페
- **노출 퍼널**: SEO를 통해 트래픽을 수집합니다.
- **카페 페이지 생성**: 각 카페에 대한 상세 페이지를 만들어줍니다.
- **데이터 수집**: 네이버 지도를 크롤링하여 데이터를 수집합니다.

## 세부 기능

- **클라이언트 사이드**:
  - 위치 기반 검색
  - 수정 요청 기능
- **API 기능**:
  - 지속 가능한 네이버 지도 크롤링
  - 리뷰 및 데이터 AI 학습
  - 태그 생성 기능 (산미, 대형카페 등)

## 기능 요건

- **무중단 배포**
- **컨테이너 오케스트레이션**
- **CI/CD**
- **ElasticSearch를 통한 저지연 검색**

## 필요 인프라

- **클라이언트 사이드 API**: ElasticSearch와 소통 (GraphQL, Spring Boot)
- **크롤링 API**:
  - 하나의 IP로 크롤링할 경우 네이버에서 막기 때문에 AWS Lambda를 통해 수집
  - API는 트리거 역할
  - 수집한 데이터를 AI 학습 후 태그 생성하여 DB에 업데이트 (세부정보, 메뉴, 리뷰 기반)
- ~~**ElasticSearch**: AWS OpenSearch~~ (비용 이슈)
- ~~**컨테이너 오케스트레이션**: AWS EKS~~ (비용 이슈)
- ~~**CI/CD**: Jenkins, AWS CodeBuild, Helm Charts~~ (비용 이슈)
- **RDS** : MySQL
- **컨테이너 오케스트레이션**: Docker Compose
- **CI/CD**: GitHub Actions

## 7/11

- Tracing은 인프라 셋팅 이후에 셋팅할 것.
- 생각보다 AWS 비용이 많이 나올 것으로 예상되어 로컬에서 인프라 구성할 것
- data.seoul.go.kr을 통해 카페 데이터 가져오기 완료.
