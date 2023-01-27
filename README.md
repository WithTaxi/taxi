# Taxi 로그인

# 🚀 기능구현 목록
    - 일반 회원가입, 로그인 [O]
    - 구글 회원가입, 로그인 [O]
    - 페이스북 회원가입, 로그인 [O]
    - 카카오 회원가입, 로그인 [O]
    - 아이디 찾기 [O]
    - 아이디 중복 찾기 [O]
    - 닉네임 중복 찾기 [O]
    - 비밀번호 찾기[0]
    - 임시비밀번호 발급시에 8-16글자, 영문소문자, 영문대문자, 특수문자 포함할 수 있도록 조정[X]
    - 회원정보 수정[X]
    

### 개선사항
    - 회원가입 내용이 너무 많아서 줄일 필요가 있을 것으로 보임
    - OAuth2 로그인을 하고 추가정보를 입력받는 것은 사용자에게 sns로그인의 편리성을
      떨어뜨릴 수 있음
    - OAuth2 로그인 sns 수를 줄일 필요가 있음


# 비밀번호 찾기
    1. 아이디 입력
    2. 이메일 임시비밀번호 발급 (구현코드 있음)
    3. 8-16글자 숫자, 영어, 특수문자 한글자씩

# 비밀번호 변경
    1. 비밀번호 변경