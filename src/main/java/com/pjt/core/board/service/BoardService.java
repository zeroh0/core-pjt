package com.pjt.core.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pjt.core.board.dto.BoardRequestDto;
import com.pjt.core.board.dto.CreateBoardRequestDto;
import com.pjt.core.board.dto.FileResponseDto;
import com.pjt.core.board.dto.ReadBoardImgResponseDto;
import com.pjt.core.board.dto.ReadBoardResponseDto;
import com.pjt.core.board.mapper.BoardMapper;
import com.pjt.core.common.error.exception.NoDataException;
import com.pjt.core.common.error.response.ErrorCode;
import com.pjt.core.common.util.ApiResponse;
import com.pjt.core.common.util.PagingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
	
	private final BoardMapper boardMapper;
	private final FileService fileService;

public ApiResponse<Object>  getBoard(BoardRequestDto boardRequestDto) {
	int count = boardMapper.getBoardTotalCount(boardRequestDto);
	List<ReadBoardResponseDto> list = boardMapper.getBoard(boardRequestDto);
	
	PagingResponse<Object> pagingResponse = new PagingResponse<>();
	pagingResponse.setTotalCount(count);
	pagingResponse.setData(list);
	
	
	return ApiResponse.ok(pagingResponse);
	
	}

public ReadBoardResponseDto getDetail(BoardRequestDto boardRequestDto) throws Exception {
	if(!StringUtils.hasText(boardRequestDto.getBoardId())) {
		// throw new NoDataException(ErrorCode.INVALID_INPUT_VALUE);
		throw new Exception("게시글 ID가 없습니다.");
	}
	
	// 상세 조회
	ReadBoardResponseDto dto =  boardMapper.getDetail(boardRequestDto.getBoardId());
	Optional.ofNullable(dto).orElseThrow(() -> new NoDataException(ErrorCode.NO_DATA));
	
	// 이미지 조회
	List<ReadBoardImgResponseDto> img = boardMapper.getImage(boardRequestDto.getBoardId());
	
	dto.setBoardImg(img);
	
	return dto;
}

public String createBoard(CreateBoardRequestDto dto, List<MultipartFile> files) throws Exception  {

	List<FileResponseDto> savedFiles = new ArrayList<>();
	// 필수값 체크
	if(dto == null) {
		throw new NoDataException(ErrorCode.INTERNAL_SERVER_ERROR);
	} 
	
	// 게시글 저장
	dto.setBoardContent(Jsoup.clean(dto.getBoardContent(), Safelist.basic()));
	
	int result = boardMapper.createBoard(dto);
	
	if(result == 0) {
		throw new Exception("저장 중 오류가 발생했습니다.");
	}
	
	
	if(!CollectionUtils.isEmpty(files)) {
				try {
					// 파일 저장
					savedFiles = fileService.uploadFile(dto.getBoardId(), files);
					
					// DB 파일 저장
					for(FileResponseDto savedFile : savedFiles) {
						boardMapper.createBoardImg(savedFile);
					}
				} catch (IllegalStateException | IOException e) {
					for(FileResponseDto file : savedFiles) {
						// 업로드 실패시 파일 삭제
						new File(file.getUploadFilePath() + file.getSavedName()).delete();
					}
				}
	}

	
	return "저장완료";

}







}
