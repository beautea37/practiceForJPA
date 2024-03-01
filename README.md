# practiceForJPA
PracticeJPA

클래스명_id로 컬럼명 부여해준 이유는 id만 난사하면 헷갈리기 때문.
FK를 맞추면 헷갈리지 않을 수 있다는 장점이 있다.

##수정
manyTomany는 나중에 변경 요망

Entity단위에서 Setter는 가급적이면 사용하면 안됨. setter가 모두 있는 상황이라면 변경 포인트가 너무 많아서 유지보수가 힘들다.
##의문 그럼 builder 사용은 어떤지 기록해놓기


@@현황
FORM 변경 완료 했고, 현재 DB에 DTYPE에 따른 값 저장까지 완료된 상태.
저장된 것 변경 현재 안 되고 있음. 일요일에 할 예정