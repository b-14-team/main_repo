package com.wolf.workflow.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CardUpdateRequestDto {
    @NotBlank(message = "제목은 필수로 입력해야합니다.")
    @Length( max = 100, message = "최대 100자까지 입력해주세요")
    private String title;
    private String content;
    private LocalDate deadDate;
    private Long assigneeId;
}
