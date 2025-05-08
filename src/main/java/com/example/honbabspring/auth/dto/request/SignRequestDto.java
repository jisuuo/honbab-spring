package com.example.honbabspring.auth.dto.request;

import com.example.honbabspring.user.entity.User;
import com.example.honbabspring.user.type.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignRequestDto {

    @NotBlank(message = "해당 값은 필수 입력값입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]{2,9}", message = "아이디는 영문, 숫자만 가능하며 2 ~ 10자리까지 가능합니다.")
    @Schema(example = "jisuuuu22")
    private String userId;

    @NotBlank(message = "해당 값은 필수 입력값입니다.")
    @Size(min = 2, max = 10, message = "이름은 2~10자리까지 가능합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글 또는 영문만 가능합니다.")
    @Schema(example = "최혼밥")
    private String username;

    @NotBlank(message = "해당 값은 필수 입력값입니다.")
    @Size(min = 10, max = 50, message = "비밀번호는 10~50자리까지 가능합니다.")
    @Schema(example = "Password123!")
    private String password;

    @NotBlank(message = "해당 값은 필수 입력값입니다.")
    @Size(min = 10, max = 50, message = "비밀번호 확인은 10~50자리까지 가능합니다.")
    @Schema(example = "Password123!")
    private String confirmPassword;

    @NotBlank(message = "해당 값은 필수 입력값입니다.")
    @Pattern(regexp = "^(010-?\\d{4}-?\\d{4})$", message = "휴대폰 번호는 010-1234-5678 또는 01012345678 형식이어야 합니다.")
    @Schema(example = "010-1234-5678")
    private String phone;

    @NotBlank(message = "해당 값은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @Schema(example = "john@example.com")
    private String email;

    @Schema(example = "USER")
    private String role;

    public SignRequestDto(String userId, String username, String password, String confirmPassword, String phone, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public User toEntity(String encodedPassword) {
        return User.builder()
                .userId(this.userId)
                .username(username)
                .email(this.email)
                .password(encodedPassword)
                .confirmPassword(encodedPassword)
                .phone(this.phone)
                .role(Role.valueOf(role))
                .build();
    }
}