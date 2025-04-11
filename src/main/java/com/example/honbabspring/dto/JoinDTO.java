package com.example.honbabspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class JoinDTO {

    @NotEmpty(message = "아이디 입력은 필수입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]{2,9}", message = "아이디는 영문, 숫자만 가능하며 2 ~ 10자리까지 가능합니다.")
    private final String userId;

    @NotEmpty(message = "이름 입력은 필수입니다.")
    @Size(min = 2, max = 10, message = "이름은 2~10자리까지 가능합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글 또는 영문만 가능합니다.")
    private final String username;

    @Size(min = 10, max = 50, message = "비밀번호는 10~50자리까지 가능합니다.")
    private final String password;

    @Size(min = 10, max = 50, message = "비밀번호 확인은 10~50자리까지 가능합니다.")
    private final String confirmPassword;

    @NotEmpty(message = "핸드폰 번호 입력은 필수입니다.")
    @Pattern(regexp = "^(010-?\\d{4}-?\\d{4})$", message = "휴대폰 번호는 010-1234-5678 또는 01012345678 형식이어야 합니다.")
    private final String phone;

    @NotEmpty(message = "이메일 입력은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private final String email;

    private final String role;

    public JoinDTO(String userId, String username, String password, String confirmPassword, String phone, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
}