package com.juyeon.restapi.section05.swagger;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private int no;
    private String id;
    private String pwd;
    private String name;
    private LocalDate enrollmentDate;
}
