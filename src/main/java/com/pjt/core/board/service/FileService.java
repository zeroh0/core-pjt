package com.pjt.core.board.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pjt.core.board.dto.FileRequestDto;
import com.pjt.core.board.dto.FileResponseDto;
import com.pjt.core.board.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final String rootPath = System.getProperty("user.dir") + "/files/";
//	@Value("${file.dir}")
//    private String rootPath;
	
	private final BoardMapper boardMapper;
	
	public List<FileResponseDto> uploadFile(String category, List<MultipartFile> files) throws IllegalStateException, IOException {
		
		List<FileResponseDto> savedFiles = new ArrayList<FileResponseDto>();
		
		if(!CollectionUtils.isEmpty(files)) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			String date = now.format(formatter);
			
			String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
			
			// 프로젝트 내부에 폴더 생성 (files -> 현재날짜)
			String path = rootPath +  File.separator + date;    


			File saveFile = new File(path);
			
			// 디렉토리 만들기
			if(!saveFile.exists()) {
				saveFile.mkdirs();
			}

			for(MultipartFile file : files) {
				// 원본 파일명
				String originalFileName = file.getOriginalFilename();
				// 파일 type
				String type = file.getContentType();
				
				// 새로운 파일명 생성 (UUID_원본파일명)
				String newFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
				
			
				saveFile = new File(path + File.separator + newFileName);
				file.transferTo(saveFile);
				
				FileResponseDto save = new FileResponseDto();
				save.setUploadFilePath(saveFile.getAbsolutePath());
				save.setSavedName(newFileName);
				save.setImgFileSize(file.getSize());
				save.setEmoticonImgNm(file.getOriginalFilename()); 
				save.setImgExtNm(file.getContentType());

				savedFiles.add(save);
				
				
				// DB 저장
				// TODO 카테고리 코드 따라 분기 (코드값 설정 필요함)
				if("001".equals(category)) {
					
					// boardMapper.insertBoardImg(savedFiles);
				}
				
			}
		}
		
		
		
		
		
		return savedFiles;
	}

}
