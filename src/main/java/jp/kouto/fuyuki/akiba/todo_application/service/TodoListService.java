package jp.kouto.fuyuki.akiba.todo_application.service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.kouto.fuyuki.akiba.todo_application.dao.TodoListDao;
import jp.kouto.fuyuki.akiba.todo_application.dto.ApiResponseDto;
import jp.kouto.fuyuki.akiba.todo_application.dto.SelectResultDto;
import jp.kouto.fuyuki.akiba.todo_application.dto.TodoListDto;
import jp.kouto.fuyuki.akiba.todo_application.dto.UpdateResultDto;
import jp.kouto.fuyuki.akiba.todo_application.dto.UpdateTodoTaskDto;
import jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto;
import jp.kouto.fuyuki.akiba.todo_application.enums.ApiResult;
import jp.kouto.fuyuki.akiba.todo_application.exceptions.RyzaDBException;
import jp.kouto.fuyuki.akiba.todo_application.util.DaoFactory;
import jp.kouto.fuyuki.akiba.todo_application.util.JsonUtil;

public class TodoListService {
	
	final static Logger logger = LoggerFactory.getLogger(TodoListService.class);
	
	/**
	 * 画面表示ロジック
	 * セッション情報内のユーザー情報を用いてリストをDBから選択する。
	 * @param req
	 * @param httpSession
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public HttpServletRequest displayLogic(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		UsersDto user = (UsersDto) httpSession.getAttribute("user");
		long id = user.getId();
		TodoListDao dao = DaoFactory.getTodoListDao();
		List<TodoListDto> todoList = new ArrayList<>();
		try {
			todoList = dao.getListById(id, sqlSession);
		} catch(RyzaDBException e) {
			// エラーページへ飛ばす
			e.printStackTrace();
		}
		req.setAttribute("todoList", todoList);
		return req;
	}
	
	/**
	 * タスク登録ロジック
	 * @param req
	 * @param httpSession
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public HttpServletRequest registerLogic(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		UsersDto user = (UsersDto) httpSession.getAttribute("user");
		long id = user.getId();
		String content = req.getParameter("content");
		Date date = Date.valueOf(req.getParameter("due_date"));
		TodoListDao dao = DaoFactory.getTodoListDao();
		try {
			dao.insertTask(id, content, date, sqlSession);
			httpSession.setAttribute("message", "タスクを1件登録しました。");
		} catch(RyzaDBException e) {
			httpSession.setAttribute("errorMessage", "タスク登録に失敗しました。問題が続く場合は管理者にお知らせください。");
			logger.info("insert error",e);
		}
		return req;
	}

	/**
	 * タスク削除ロジック
	 * @param req
	 * @param httpSession
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public HttpServletRequest logicalDeleteLogic(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		UsersDto user = (UsersDto) httpSession.getAttribute("user");
		long userId = user.getId();
		List<String> contentId = Arrays.asList(req.getParameter("ids").split(","));
		TodoListDao dao = DaoFactory.getTodoListDao();
		try {
			dao.logicalDeleteTask(userId, contentId, sqlSession);
		}  catch(RyzaDBException e) {
			httpSession.setAttribute("errorMessage", "タスク削除に失敗しました。問題が続く場合は管理者にお知らせください。");
			logger.info("delete error",e);
		}
		return req;
	}
	
	/**
	 * 指定タスク取得ロジック
	 * @param req
	 * @param httpSession
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public HttpServletRequest fetchTaskLogic(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		List<TodoListDto> editList = new ArrayList<>();
		UsersDto user = (UsersDto) httpSession.getAttribute("user");
		long userId = user.getId();
		List<String> contentId = Arrays.asList(req.getParameter("ids").split(","));
		TodoListDao dao = DaoFactory.getTodoListDao();
		try {
			editList = dao.fetchTask(userId, contentId, sqlSession);
		}  catch(RyzaDBException e) {
			httpSession.setAttribute("errorMessage", "タスク編集ページ遷移に失敗しました。問題が続く場合は管理者にお知らせください。");
			logger.info("edit error",e);
		}
		req.setAttribute("editList", editList);
		return req;
	}
	
	/**
	 * ステータス更新ロジック
	 * @param req
	 * @param httpSession
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateTaskLogic(HttpServletRequest req, HttpServletResponse res, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		// リクエストDTO
		UpdateTodoTaskDto requestDto = JsonUtil.readJson(req, UpdateTodoTaskDto.class);
		Long userId = Long.parseLong(requestDto.getUserId());
		List<String> contentId = Arrays.asList(requestDto.getId().toString().trim());
		TodoListDao dao = DaoFactory.getTodoListDao();
		try {
			// 更新用マッパーDTO
			UpdateTodoTaskDto updateDto = new UpdateTodoTaskDto();
			List<TodoListDto> fetchedList = dao.fetchTask(userId, contentId, sqlSession);
			
			// 変更があった項目だけを更新する
			boolean changed=false;
			if (!Objects.equals(requestDto.getContent(), fetchedList.get(0).getContent())) {
			    updateDto.setContent(requestDto.getContent());
			    changed=true;
			}
			if (!Objects.equals(requestDto.getStatus(), fetchedList.get(0).getStatus())) {
			    updateDto.setStatus(requestDto.getStatus());
			    changed=true;
			}
			if (!Objects.equals(requestDto.getDueDate(), fetchedList.get(0).getDueDate())) {
			    updateDto.setDueDate(requestDto.getDueDate());
			    changed=true;
			}
			// 変更があればSQL実行
			int result = 0;
			if(changed) {
				updateDto.setUserId(userId.toString());
				updateDto.setId(contentId.get(0));
				result = dao.updateTask(updateDto, sqlSession);
			}
			
			if (result > 0) {
				UpdateResultDto updateResult = new UpdateResultDto(true, LocalDateTime.now().toString());
				ApiResponseDto<UpdateResultDto> responseData = ApiResponseDto.<UpdateResultDto>builder()
						.status(200)
						.result(ApiResult.OK)
						.data(updateResult)
						.build();
				JsonUtil.writeJson(res, responseData);
			}
		} catch(RyzaDBException e) {
			httpSession.setAttribute("errorMessage", "タスク更新に失敗しました。問題が続く場合は管理者にお知らせください。");
			logger.info("update error",e);
		}
	}
	
	/**
	 * タスク送信処理
	 * @param req
	 * @param res
	 * @param httpSession
	 * @param sqlSession
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void sendTaskLogic(HttpServletRequest req, HttpServletResponse res, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		List<TodoListDto> list = (List<TodoListDto>) req.getAttribute("editList");
		SelectResultDto<TodoListDto> selectResult = new SelectResultDto<TodoListDto>(list, list.size());
		ApiResponseDto<SelectResultDto<TodoListDto>> responseData = 
				ApiResponseDto.<SelectResultDto<TodoListDto> >builder()
				.status(200)
				.result(ApiResult.OK)
				.data(selectResult)
				.build();
		JsonUtil.writeJson(res, responseData);
	}
}
