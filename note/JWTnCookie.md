### 1. JWT를 관리하는 여러가지 방법

- 서버 측에서 JWT를 생성하고 관리합니다. 서버에서는 JWT를 발급하고 검증하여 사용자의 인증 및 권한 부여를 처리합니다. 이를 위해 서버는 JWT 라이브러리를 사용하여 토큰을 생성하고 검증합니다.
- 클라이언트 측에서 JWT를 관리합니다. 클라이언트는 로그인 시에 서버에서 받은 JWT를 저장하고, 인증이 필요한 요청을 보낼 때마다 저장된 JWT를 요청에 포함시켜야 합니다. 이를 위해 주로 웹 스토리지(localStorage 또는 sessionStorage)를 사용합니다.

### 2. JWT를 보내고 관리하는 여러가지 방법

- HTTP 헤더에 JWT를 포함하여 요청을 보냅니다. 일반적으로 "Authorization" 헤더에 Bearer 스킴을 사용하여 JWT를 전송합니다
- 쿠키에 JWT를 저장하여 요청을 보냅니다. 서버에서는 클라이언트로부터 받은 JWT를 쿠키에 저장하고, 클라이언트는 쿠키를 자동으로 요청에 포함시켜 서버로 보냅니다. 이를 위해 서버는 Secure 및
  HttpOnly 속성이 설정된 쿠키를 사용합니다.

### 3. Cookie와 Storage

- 쿠키는 클라이언트와 서버 간의 상태를 유지하고 사용자 인증을 처리하는 데 사용됩니다. 주로 서버에서 생성되며, 브라우저가 자동으로 요청에 쿠키를 포함하여 서버로 전송합니다.
- 웹 스토리지는 클라이언트 측에서 데이터를 저장하는 데 사용됩니다. 주로 localStorage와 sessionStorage가 있으며, 브라우저의 세션 또는 영구 저장소에 데이터를 저장합니다. 일반적으로 사용자
  인증 토큰(JWT 등)이나 클라이언트 측에서 관리해야 하는 기타 데이터를 저장하는 데 사용됩니다.