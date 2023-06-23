# ☕️ 카페 키오스크 프로젝트

![](https://img.shields.io/badge/Last_Upadate-2023--06--22-blue)

![](https://img.shields.io/badge/Sprint-1-green)

[![Build Status](https://img.shields.io/endpoint.svg?url=https%3A%2F%2Factions-badge.atrox.dev%2F%2Fcodesquad-gwanaksan%2Fkiosk-max%2Fbadge%3Fref%3Ddev&style=flat)](https://actions-badge.atrox.dev//codesquad-gwanaksan/kiosk-max/goto?ref=dev)

# ☕️ 카페 키오스크 프로젝트

- 2023 코드스쿼드 마스터즈 Max에서 진행한 "카페 키오스크"를 구현하는 그룹 프로젝트

- 미션 기간: 2023-06-19 ~ 2023-06-30

- 프로젝트 Wiki → <a href="https://github.com/codesquad-gwanaksan/kiosk-max/wiki"><img src = "https://img.shields.io/badge/Kiosk_Home-black?logo=Wikipedia"></a>

<br/>

## 🧑🏻‍💻팀원 소개

| 프론트엔드 | 프론트엔드 | 백엔드 | 백엔드 | 백엔드 | 백엔드 |

| :---------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------: | --- | --- |

| <a href="https://github.com/qkdflrgs"><img src = "https://avatars.githubusercontent.com/qkdflrgs" width="120px;"> | <a href="https://github.com/youzysu"><img src = "https://avatars.githubusercontent.com/youzysu" width="120px;"> | <a href="https://github.com/jinny-l"><img src = "https://avatars.githubusercontent.com/jinny-l" width="120px;"> | <a href="https://github.com/won4885"><img src = "https://avatars.githubusercontent.com/won4885" width="120px;"> | <a href="https://github.com/HyowonSin"><img src = "https://avatars.githubusercontent.com/HyowonSin" width="120px;"> | <a href="https://github.com/yonghwankim-dev"><img src = "https://avatars.githubusercontent.com/yonghwankim-dev" width="120px;"> | | |

| [**litae**](https://github.com/qkdflrgs) | [**조이**](https://github.com/youzysu) | [**Jinny**](https://github.com/jinny-l) | [**Sully**](https://github.com/won4885) | [**TOMMY**](https://github.com/HyowonSin) | [**네모네모**](https://github.com/yonghwankim-dev) |

<br/>

## 🖥️ 동작 화면

> https://kiosk-team-03.web.app/

<br/>

## 🔧️ 개발 환경

### Front-End

![](https://img.shields.io/badge/-ReactJs-61DAFB?logo=react&logoColor=white&style=flat)

![](https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white)

![](https://camo.githubusercontent.com/a91f29fbfde227665b0cd5a447c0b035180e8a285bfef1ec8d91c8ba80fcaa20/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f547970657363726970742d3331373843363f7374796c653d666c6174266c6f676f3d54797065536372697074266c6f676f436f6c6f723d7768697465)

![](https://camo.githubusercontent.com/e3883202fdd9cb44fd6a62f35730342d5cd477c3d76a2140aa38aa87eac6b224/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f2d56697375616c25323053747564696f253230436f64652d3030374143433f7374796c653d666c6174266c6f676f3d56697375616c25323053747564696f253230436f6465266c6f676f436f6c6f723d7768697465)

- CRA (Create React App)

- CSS Modules

- Typescript: `^5.0.2`

- @testing-library/jest-dom: `^5.14.1`

- @testing-library/react: `^13.0.0`

- @testing-library/user-event: `^13.2.1`

- @types/jest: `^29.5.2`

- @types/node: `^20.3.1`

- @types/react: `^18.2.13`

- @types/react-dom: `^18.2.6`

- react: `^18.2.0`

- react-dom: `^18.2.0`

- react-scripts: `5.0.1`

### Back-End

![](https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white)

![](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)

![](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white)

![](https://img.shields.io/badge/Amazon_AWS-232F3E?style=flat&logo=amazonaws)

![](https://img.shields.io/badge/GitActions-2088FF?style=flat&logo=githubactions&logoColor=white)

- Java: `JDK 11`

- SpringBoot: `ver. 2.7.12`

- MySQL: `ver. 8.0.32`

- Amazon AWS: `EC2`, `S3`, `RDS`, `CodeDeploy`

<br/>

## ⚙️ 인프라 구조

### Back-End

![Kiosk-Architecture](https://github.com/codesquad-gwanaksan/kiosk-max/assets/108214590/e858b08d-8ae9-49b2-a015-2c84c1dd658c)

<br/>

## 💾 ERD

![](https://github.com/codesquad-gwanaksan/kiosk-max/assets/108214590/aa77333f-274f-4c2d-9cf7-a90e99989977)

<br/>

## 🌎 API 명세서

| 기능 | HTTP Method | URL |

| :------------- | :---------- | :----------------------------- |

| 메뉴 조회 | `GET` | /menus |

| 카드 결제 요청 | `POST` | /api/payment/card |

| 현금 결제 요청 | `POST` | /api/payment/cash |

| 영수증 조회 | `GET` | /api/receipt?orderId={orderId} |

<br/>

## 🤝 협업 전략

- [그라운드 룰]()

- [커밋 컨밴션]()

- PR 컨밴션

- [브랜치 전략]()
