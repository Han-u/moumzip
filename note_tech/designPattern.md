배경 - spring security를 직접 구현하자  
목적 - provider 별로 말고 공통으로 만들어서 써보자!  
의문 - 관련 자료 찾아봤을 때 사람마다 쓰는 패턴이 다양했음. 어떤 패턴을 쓰지???


1. Static 메서드만 있는 클래스  
장점: 메모리 절약, 성능, 간단  
단점: 확장성.유연성 부족, 테스트 어려움, 중복코드 가능  

2. 팩토리 패턴  
장점: 객체지향적, 확장성, 다형성, 유지보수, 테스트용이  
단점: 객체생성비용, 조건문 증가  

3. 전략 패턴  
장점: 동적 교체 가능성, 유지보수성  

4. Composit 패턴
장점: 개별 객체랑 복합 객체 동일하게 다루어 복잡한 트리구조 단순하게.  
객체의 트리 구조, 클라이언트가 단일 객체와 복합 객체를 구분하지 않을 때  

5. 매니저   
특정 작업 또는 자원을 관리하고 조정하는 역할
리소스 관리, 비즈니스 로직 처리, 외부 서비스 통합, 상태 관리, 예외 처리, 보안 및 권한 관리 등  
==> 매니저는 싱글톤 패턴인가??

고민은 됐지만 결국 Provider별로 클래스륾 만들고 싶지 않았어서... Manager를 두고 provider에 대해 공통적으로 처리해보기로 함


