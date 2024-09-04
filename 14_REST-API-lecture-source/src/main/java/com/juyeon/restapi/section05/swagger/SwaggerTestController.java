package com.juyeon.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Tag: API들의 그룹을 짓기 위한 어노테이션
@Tag(name = "Spring Boot Swagger 연동 API (USER 기능)")
@RestController
@RequestMapping("/swagger")
public class SwaggerTestController {
    private List<UserDTO> users;

    public SwaggerTestController() {
        users = new ArrayList<>();

        users.add(new UserDTO(1, "user01", "pass01", "너구리", LocalDate.now()));
        users.add(new UserDTO(2, "user02", "pass02", "코알라", LocalDate.now()));
        users.add(new UserDTO(3, "user03", "pass03", "곰", LocalDate.now()));
    }

    // user 정보 전체 조회
    @Operation(summary = "전체 회원 조회", description = "사이트의 전체 회원 목록 조회" /* description: 무슨 데이터를 필요로 하는지, 권한 정보 등이 들어감 */)
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {
        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType(
                        "application",
                        "json",
                        Charset.forName("UTF-8")
                )
        );

        // Body
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);

        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "조회 성공!",
                responseMap
        );

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @Operation(summary = "회원번호로 회원 조회",
            description = "회원 번호를 통해 회원을 조회한다.",
            parameters = {@Parameter(name = "userNo", description = "사용자 화면에서 넘어오는 user의 pk") /* parameter에 대한 설명을 보여줄 수 있음 */})
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {

        /*
        * 헤더가 꼭 필요하지 않은 간단한 응답에서는 굳이 헤더를 설정할 필요 X
        * 클라이언트에게 추가 정보를 전달해야 하거나 특정 헤더가 요구되는 경우에만 명시적으로 헤더를 설정해도 된다.
        * */
        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType(
                        "application",
                        "json",
                        Charset.forName("UTF-8")
                )
        );

        // body
        // PathVariable로 받은 userNo와 일치하는 회원정보 하나 가져오기
        UserDTO foundUser = users.stream()
                .filter(user -> user.getNo() == userNo).toList().get(0);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);

        // method chaining
        return ResponseEntity.ok()
//                .headers(headers)
                .body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    // 유저 추가
//    @PostMapping("/users")    // 등록시엔 Post 사용
//    public ResponseEntity<?> register(@RequestBody UserDTO newUser) {
//        // 마지막에 위치한 유저 번호
//        int lastUserNo = users.get(users.size() - 1).getNo();
//
//        // 마지막에 위치한 유저 번호 + 1
//        newUser.setNo(lastUserNo + 1);
//
//        // 유저 추가
//        users.add(newUser);
//
//        System.out.println("newUser = " + newUser);
//
//        return ResponseEntity
//                .created(URI.create("/entity/users/" + users.get(users.size() - 1).getNo()))    // 경로
//                .build();
//    }

    // 유저 추가
    @PostMapping("/users")    // 등록시엔 Post 사용
    public ResponseEntity<?> register(@RequestBody UserDTO newUser) {
        int lastUserNo = users.get(users.size() - 1).getNo();
        newUser.setNo(lastUserNo + 1);
        users.add(newUser);

        // service에서 결과만 넘겨주기
        String successMsg = "회원 등록에 성공하였습니다.";

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", successMsg);

        return ResponseEntity
                .ok()
                .body(new ResponseMessage(201, "회원 추가 성공", responseMap));
    }

    // 유저 정보 수정
    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo, @RequestBody UserDTO modifyInfo) {
        // 수정할 유저 정보 찾아오기
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).toList().get(0);

        // 수정할 정보를 RequestBody로 받아서 id, pwd, name 수정
        foundUser.setId(modifyInfo.getId());
        foundUser.setPwd(modifyInfo.getPwd());
        foundUser.setName(modifyInfo.getName());

        return ResponseEntity.created(URI.create("/entity/users/" + userNo)).build();
    }

    // 유저 삭제
    @Operation(summary = "회원 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "회원 정보 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력된 파라미터")
    })
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo) {
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).toList().get(0);
        users.remove(foundUser);

        return ResponseEntity.noContent().build();
    }
}
