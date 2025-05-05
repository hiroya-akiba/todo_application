package jp.kouto.fuyuki.akiba.todo_application.service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jp.kouto.fuyuki.akiba.todo_application.dao.TodoListDao;
import jp.kouto.fuyuki.akiba.todo_application.dao.UsersDao;
import jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto;
import jp.kouto.fuyuki.akiba.todo_application.util.DaoFactory;

public class UserListService {
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
		UsersDao dao = DaoFactory.getUsersDao();
		List<UsersDto> userList = dao.getUserList(sqlSession);
		req.setAttribute("userList", userList);
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
		dao.insertTask(id, content, date, sqlSession);
		return req;
		
	}

}
