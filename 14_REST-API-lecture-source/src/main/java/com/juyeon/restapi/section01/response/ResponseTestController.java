package com.juyeon.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@ResponseBody
//@Controller
@RestController     // = @ResponseBody + @Controller
@RequestMapping("/response")
public class ResponseTestController {
    // 문자열 응답 test
    @GetMapping("/hello")
    public String helloWorld() {
        System.out.println("hello world!");
        return "hello world!";
    }

    // 기본 자료형 test
    @GetMapping("/random")
    public int getRandomNumber(){
        return (int)(Math.random() * 10) + 1;
    }

    // Object 타입 응답 test
    @GetMapping("/message")
    public Message getMessage() {
        return new Message(200, "정상 응답 완료!");
    }

    // List 타입 응답 test
    @GetMapping("/list")
    public List<String> getList() {
        return List.of(new String[] {"햄버거","피자","닭가슴살"});
    }

    // Map 타입 응답 test
    @GetMapping("/map")
    public Map<Integer, String> getMap() {
        Map<Integer, String> messageMap = new HashMap<>();
        messageMap.put(200,"정상 응답 완료!");
        messageMap.put(404,"페이지를 찾을 수 없음...");
        messageMap.put(500,"서버 내부 요류 => 개발자 잘못");

        return messageMap;
    }

    /*
    * Image Response
    *   produces 설정을 해주지 않으면 이미지가 텍스트로 변환된 형태 그대로 출력됨
    *   produces를 통해 response header의 content-type(MIME TYPE)에 대한 설정 가능
    * */
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {   // 이미지는 byte로 변환되어 전송
        return getClass().getResourceAsStream("/images/rest-api.png").readAllBytes();
    }

    // ResponseEntity를 이용한 응답
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity() {
        return ResponseEntity.ok(new Message(200, "정상 응답 완료!"));
    }
}
