package com.pjt.core.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pjt.core.board.dto.BoardDto;
import com.pjt.core.board.dto.CreateBoardReqDto;
import com.pjt.core.board.dto.CreateBoardResDto;
import com.pjt.core.board.dto.ReadBoardDtlReqDto;
import com.pjt.core.board.dto.ReadBoardDtlResDto;
import com.pjt.core.board.dto.ReadBoardListReqDto;
import com.pjt.core.board.dto.UpdateBoardReqDto;
import com.pjt.core.board.dto.CreateBoardImgReqDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api")
@Tag(name = "BoardController", description = "게시판 API")
public class BoardController {

	@Autowired
	BoardService boardSevice;

	/**
	 * <pre>
	 * 게시판 리스트
	 * </pre>
	 *
	 * @author KangMinJi (kmj0701@coremethod.co.kr)
	 * @param ReadBoardListReqDto
	 * @return
	 */
	@GetMapping("/board")
	@Operation(summary = "게시판 조회", description = "게시판 리스트가 조회됩니다.")
	public List<BoardDto> getBoardList(ReadBoardListReqDto boardReqDto) {
		return boardSevice.getBoardList(boardReqDto);
	}

	/**
	 * <pre>
	 * 게시판 등록
	 * </pre>
	 *
	 * @author KangMinJi (kmj0701@coremethod.co.kr)
	 * @param CreateBoardReqDto
	 * @return CreateBoardResDto
	 */
	@PostMapping("/board")
	@Operation(summary = "게시판 등록", description = "게시판 등록합니다.")
	//@RequestMapping(value="/", method = {RequestMethod.POST})
	public CreateBoardResDto insertBoard(@RequestBody @Validated CreateBoardReqDto boardReqDto)
			throws Exception {
		return boardSevice.insertBoard(boardReqDto);
	}



	/**
	 * <pre>
	 * 게시판 상세
	 * </pre>
	 *
	 * @author KangMinJi (kmj0701@coremethod.co.kr)
	 * @param ReadBoardDtlReqDto
	 * @return ReadBoardDtlResDto
	 */
	@GetMapping("/board/{boardId}")
	@Operation(summary = "게시판 상세 조회", description = "게시판 상세 조회됩니다.")
	public ReadBoardDtlResDto getBoard(/*@RequestParam(value="boardId", required=true) int boardId , */ReadBoardDtlReqDto  boardReqDto, @PathVariable(value="boardId") Integer boardId) {
		//ReadBoardDtlReqDto boardReqDto = new ReadBoardDtlReqDto();
		return boardSevice.getBoardDtl(boardReqDto);
	}

	/**
	 * <pre>
	 * 게시판 수정
	 * </pre>
	 *
	 * @author KangMinJi (kmj0701@coremethod.co.kr)
	 * @param UpdateBoardReqDto
	 * @return CreateBoardResDto
	 */
	@PutMapping("/board")
	@Operation(summary="게시판 수정", description ="게시판 수정이 됩니다")
	public CreateBoardResDto updateBoard(@RequestBody  UpdateBoardReqDto updateBoardReqDto) {
//응용
		return boardSevice.updateBoard(updateBoardReqDto);



	}
	/**
	 * <pre>
	 * 게시판 삭제
	 * </pre>
	 *
	 * @author KangMinJi (kmj0701@coremethod.co.kr)
	 * @param UpdateBoardReqDto
	 * @return CreateBoardResDto
	 */
	@DeleteMapping("/board")
	@Operation(summary="게시판 삭제", description ="게시판 삭제가 됩니다")
	public CreateBoardResDto deleteBoard(@RequestBody  UpdateBoardReqDto updateBoardReqDto) {
		return boardSevice.deleteBoard(updateBoardReqDto);

	}






}
