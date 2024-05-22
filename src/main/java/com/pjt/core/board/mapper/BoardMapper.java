package com.pjt.core.board.mapper;

import java.util.List;
import java.util.Optional;

import com.pjt.core.board.dto.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

	// 리스트 조회
	List<ReadBoardResponseDto> getBoard(String category);

	// 상세 조회
	ReadBoardResponseDto getDetail(String boardId);

	// 이미지 조회
	List<ReadBoardImgResponseDto> getImage(String boardId);

	// 게시글 등록
	int createBoard(CreateBoardRequestDto dto);

	// 이미지 등록
	void createBoardImg(FileResponseDto savedFile);

	// 댓글 등록
    void createReply(ReplyRequestDto dto);

	// 댓글 조회
	List<ReplyResponseDto> getReply(String boardId);

	// 게시글 수정을 위한 파일 path 조회
	String getFileUrlPath(String imgNo);

	// 게시글 수정
	boolean updateBoard(CreateBoardRequestDto dto);
}
