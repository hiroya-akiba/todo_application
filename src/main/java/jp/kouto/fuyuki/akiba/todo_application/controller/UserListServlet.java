package jp.kouto.fuyuki.akiba.todo_application.controller;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.kouto.fuyuki.akiba.todo_application.common.TodoConstant;
import jp.kouto.fuyuki.akiba.todo_application.service.UserListService;
import jp.kouto.fuyuki.akiba.todo_application.util.MyBatisUtil;

public class UserListServlet extends HttpServlet {
	
	UserListService service = new UserListService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false); 
		
		// セッションが無い場合はログアウトページへ
		if(session==null || session.getAttribute("user")==null) {
			forwardPage(req, res, TodoConstant.LOGOUT + TodoConstant.LOGOUT_SESSION_ERROR);
			return;
		}
		// セッションがある場合
		if(req.getParameter("parm")!=null) {
			if(req.getParameter("parm").equals("new")){
				// 新規タスク作成
				addTaskPage(req, res);
			} else if (req.getParameter("parm").equals("register")) {
				// 新規タスク追加
				addTask(req, res);
			} else {
				// ありえないパターン
				initDisplay(req, res);
			}
		} else {
			// 初期表示
			initDisplay(req, res);
		}
	}
	
	/**
	 * 初期表示
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void initDisplay(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession httpSession = req.getSession(false);
		SqlSession sqlSession = MyBatisUtil.getSqlSession(req, getServletContext()); 
		req = service.display(req, httpSession,sqlSession);
		includePage(req, res, TodoConstant.ADMIN_MAIN_PAGE);
	}

	/**
	 * タスク追加ページ表示
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addTaskPage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("userId", req.getParameter("userId"));
		includePage(req, res, TodoConstant.ADD_NEW_TASK);
	}

	/**
	 * タスク追加ボタン処理
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession httpSession = req.getSession(false);
		SqlSession sqlSession = MyBatisUtil.getSqlSession(req, getServletContext()); 
		req = service.register(req, httpSession, sqlSession);
		res.sendRedirect(req.getContextPath() + TodoConstant.TODO_LIST);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req, res);
	}

	public void includePage(HttpServletRequest req, HttpServletResponse res, String page) throws ServletException, IOException {
		RequestDispatcher dispatcher =  req.getRequestDispatcher(page);
		dispatcher.include(req, res);
	}
	
	public void forwardPage(HttpServletRequest req, HttpServletResponse res, String page) throws ServletException, IOException {
		RequestDispatcher dispatcher =  req.getRequestDispatcher(page);
		dispatcher.forward(req, res);
	}
	

}
