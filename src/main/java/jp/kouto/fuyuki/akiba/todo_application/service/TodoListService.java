package jp.kouto.fuyuki.akiba.todo_application.service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jp.kouto.fuyuki.akiba.todo_application.dao.TodoListDao;
import jp.kouto.fuyuki.akiba.todo_application.dto.TodoListDto;
import jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto;
import jp.kouto.fuyuki.akiba.todo_application.util.DaoFactory;

public class TodoListService {
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
		List<TodoListDto> todoList = dao.getListById(id, sqlSession);
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
		} catch(Exception e) {
			httpSession.setAttribute("errorMessage", "タスク登録に失敗しました。問題が続く場合は管理者にお知らせください。");
			e.printStackTrace();
		}
		return req;
	}
	
	public HttpServletRequest update(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		return req;
	}
	
	public HttpServletRequest delete(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		return req;
	}
	
	public HttpServletRequest updateStatus(HttpServletRequest req, HttpSession httpSession, SqlSession sqlSession) throws ServletException, IOException {
		return req;
	}

}
