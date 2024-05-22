package com.pjt.core.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateReplyReqDto {
    @Schema(description="댓글 아이디")
    @NotNull(message = "댓글 id는 필수입니다")
    private int replyId;

    @Schema(description="게시판 ID")
    @NotNull(message = "게시판 ID 필수입니다.")
    private int boardId;

    @Schema(description="댓글 내용")
    @NotBlank(message = "댓글 내용 필수입니다.")
    private String replyComment;

    @Schema(description="등록일자")
    private String regDt;

    @Schema(description="수정일자")
    private String updateDt;

    @Schema(description="삭제 여부 Y/N")
    @NotBlank(message = "삭제 여부 필수입니다.")
    private String useYn;
}
