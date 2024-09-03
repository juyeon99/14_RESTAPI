package com.juyeon.restapi.section03.valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    /* @Valid - 유효성 검증 */
    /*
    *   @Null : null 만 허용한다.
    *   @NotNull : null 을 허용하지 않습니다. "", " " 는 허용한다.
    *   @NotEmpty : null 과 "" 허용하지 않는다. " " 는 허용한다.
    *   @NotBlank : null, "", " " 모두 허용하지 않는다.
    *   @Email : 이메일 형식을 검사한다. 다만 "" 는 통과시킨다.
    *   @Pattern(regexp = ) : 정규식 검사 시에 사용된다.
    *                         ex) regexp="[a-z1-8]{6-10}", message = "비밀번호는 영어 숫자 포함해서 6~10자리 이내로"
    *   @Size(min=, max=) : 길이를 제한할 때 사용한다.
    *   @Max(value=) : value 이하의 값을 받을 때 사용된다.
    *   @Min(value=) : value 이상의 값을 받을 때 사용된다.
    *   @Positive : 값을 양수로 제한
    *   @PositiveOrZero : 양수와 0만 가능하다.
    *   @Future : 현재보다 미래 날짜
    *   @Past : 현재보다 과거 날짜
    *   @AssertTrue : ture 여부 확인, null 은 체크하지 않음
     *  */

    private int no;

//    @NotNull(message = "아이디는 반드시 입력되어야합니다.")
    @NotBlank(message = "아이디는 비어있거나 공백을 포함할 수 없습니다.")
    private String id;

    private String pwd;

    @Size(min = 2, message = "이름은 두 글자 이상 입력해야 합니다.")
    private String name;

    @Past
    private LocalDate enrollmentDate;
}
