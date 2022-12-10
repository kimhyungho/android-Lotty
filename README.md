# 로또의민족 - 대한민국 꼴등 로또 어플 

<img src="/lotty-images/app_logo.png" align="left"
width="200" hspace="10" vspace="10">
 
로또의민족을 통해 주변 판매점, 번호조회, 번호생성, QR조회 서비스를 제공합니다.

지도를 이용해 주변의 로또 판매점을 찾을 수 있고, 카카오맵 Scheme을 통해 길안내를 제공합니다.  
최근 당첨번호와 당첨금을 한 눈에 확인할 수 있고, 역대 로또 번호와 당첨금을 검색할 수 있습니다.  
로또 용지와 비슷한 UI로 재미있게 로또번호를 생성할 수 있습니다.  
로또 용지의 QR코드를 촬영하여 당첨여부를 확인할 수 있습니다.  

<p align="left">
<a href="https://play.google.com/store/apps/details?id=com.anseolab.lotty">
    <img alt="Get it on Google Play"
        height="80"
        src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" />
</a>

<img src="/lotty-images/1.png" width="180px" title="1" alt="1"></img>
<img src="/lotty-images/2.png" width="180px" title="2" alt="2"></img>
<img src="/lotty-images/3.png" width="180px" title="3" alt="3"></img>
<img src="/lotty-images/4.png" width="180px" title="4" alt="4"></img>
<img src="/lotty-images/5.png" width="180px" title="5" alt="5"></img>

## 개발환경 / Application Version
- Android Studio @Dolphin 2021.3.1
- minSdkVersion : 23
- targetSdkVersion : 31

## android tech
- language : Kotlin
- architecture : ACC MVVM
- async library : RxJava3, RxBinding
- image library : Glide
- network library : Retrofit2, Gson, OkHttp
- jetpack : Navigation, DataBinding, LiveData, Room..

## 팀원 구성 / 협업 도구
ios1, android1
- Slack

## 특징
- RxJava3를 사용하여 비동기 네트워크 통신을 합니다.
- RxBinding을 사용해 사용자의 입력을 비동기로 처리합니다.
- ACC ViewModel을 사용해 ACC MVVM 디자인 패턴을 구현하려고 노력했습니다.
- Module을 Presentation, Domain, Data, Remote, Local로 나눴습니다.

## APIs
- 동행복권 당첨정보 조회 Api
- Kakao 주소 검색 Api
- Kakao Map 길찾기 Scheme
- Naver Map

## Screenshots

<img src="/lotty-images/Screenshot_1.png" width="180px" title="1" alt="1"></img>
<img src="/lotty-images/Screenshot_2.png" width="180px" title="2" alt="2"></img>
<img src="/lotty-images/Screenshot_3.png" width="180px" title="3" alt="3"></img>
<img src="/lotty-images/Screenshot_4.png" width="180px" title="4" alt="4"></img>
<img src="/lotty-images/Screenshot_5.png" width="180px" title="5" alt="5"></img>
