package com.juyeon.restapi.section03.valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/valid")
public class ValidTestController {
    // 에러 상황을 만들어서 에러처리 가능한 컨트롤러를 따로 생성

    @GetMapping("/users/{userNo}")
    public ResponseEntity<?> findUserByNo() throws UserNotFoundException{
        // 항상 UserNotFoundException을 던지게 함
        boolean check = true;
        if (check) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok().build();
    }
}
