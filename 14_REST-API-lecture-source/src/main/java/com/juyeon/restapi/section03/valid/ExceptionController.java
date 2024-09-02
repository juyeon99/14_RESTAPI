package com.juyeon.restapi.section03.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// 스프링 부트 어플리케이션 전역적으로 컨트롤러의 에러 핸들링을 할 수 있게 해준다.
public class ExceptionController {
    // 해당 Exception이 발생하면 이 컨트롤러에서 처리
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserFindException(UserNotFoundException e){
        String code = "ERROR_CODE_0001";
        String description = "회원 정보 조회에 실패하였습니다.";
        String detail = e.getMessage();

        return new ResponseEntity<>(
                new ErrorResponse(code, description, detail),
                HttpStatus.NOT_FOUND
        );
    }
}
