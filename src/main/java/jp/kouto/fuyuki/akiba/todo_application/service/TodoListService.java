package jp.kouto.fuyuki.akiba.todo_application.service;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jp.kouto.fuyuki.akiba.todo_application.dao.TodoListDao;
import jp.kouto.fuyuki.akiba.todo_application.dto.TodoListDto;
import jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto;
import jp.kouto.fuyuki.akiba.todo_application.exceptions.RyzaDBException;
import jp.kouto.fuyuki.akiba.todo_application.util.DaoFactory;

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
	public HttpServletRequest display(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
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
	public HttpServletRequest register(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
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
	
	public HttpServletRequest update(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
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
	public HttpServletRequest logicalDelete(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		UsersDto user = (UsersDto) httpSession.getAttribute("user");
		long userId = user.getId();
		String contentId = req.getParameter("ids");
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
	 * タスク編集ロジック
	 * @param req
	 * @param httpSession
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public HttpServletRequest editTask(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		List<TodoListDto> editList = new ArrayList<>();
		UsersDto user = (UsersDto) httpSession.getAttribute("user");
		long userId = user.getId();
		List<String> contentId = Arrays.asList(req.getParameter("ids").split(","));
		TodoListDao dao = DaoFactory.getTodoListDao();
		try {
			editList = dao.editTask(userId, contentId, sqlSession);
		}  catch(RyzaDBException e) {
			httpSession.setAttribute("errorMessage", "タスク編集に失敗しました。問題が続く場合は管理者にお知らせください。");
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
	public HttpServletRequest updateStatus(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		List<TodoListDto> editList = new ArrayList<>();
		UsersDto user = (UsersDto) httpSession.getAttribute("user");
		long userId = user.getId();
		return req;
	}

}
