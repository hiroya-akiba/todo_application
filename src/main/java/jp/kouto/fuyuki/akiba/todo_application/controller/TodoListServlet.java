package jp.kouto.fuyuki.akiba.todo_application.controller;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.kouto.fuyuki.akiba.todo_application.common.TodoConstant;
import jp.kouto.fuyuki.akiba.todo_application.service.TodoListService;
import jp.kouto.fuyuki.akiba.todo_application.util.MyBatisUtil;

public class TodoListServlet extends HttpServlet {
	final static Logger logger = LoggerFactory.getLogger(TopServlet.class);
	TodoListService service = new TodoListService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession httpSession = req.getSession(false);
		SqlSession sqlSession = MyBatisUtil.getSqlSession(req, getServletContext());
		// セッションが無い場合はログアウトページへ
		if (httpSession == null || httpSession.getAttribute("user") == null) {
			forwardPage(req, res, TodoConstant.LOGOUT + TodoConstant.LOGOUT_SESSION_ERROR);
			return;
		}
		// セッションがある場合
		if (req.getParameter("parm") != null) {
			if (req.getParameter("parm").equals("new")) {
				// 新規タスク作成
				addTaskPage(req, res, httpSession, sqlSession);
			} else if (req.getParameter("parm").equals("register")) {
				// 新規タスク追加
				addTask(req, res, httpSession, sqlSession);
			} else if (req.getParameter("parm").equals("edit")) {
				// タスク編集
				editTask(req, res, httpSession, sqlSession);
				return;
			} else if (req.getParameter("parm").equals("delete")) {
				// タスク削除
				deleteTask(req, res, httpSession, sqlSession);
				return;
			} else if (req.getParameter("parm").equals("update")) {
				// ステータス更新
				updateTask(req, res, httpSession, sqlSession);
				return;
			} else {
				// ありえないパターン
				initDisplay(req, res, httpSession, sqlSession);
			}
		} else {
			// 初期表示
			initDisplay(req, res, httpSession, sqlSession);
		}
	}

	/**
	 * 初期表示
	 * @param req
	 * @param res
	 * @param httpsession
	 * @param sqlSession
	 * @throws ServletException
	 * @throws IOException
	 */
	public void initDisplay(
			HttpServletRequest req,
			HttpServletResponse res,
			HttpSession httpSession,
			SqlSession sqlSession) throws ServletException, IOException {
		logger.info("initDisplay start");
		req = service.displayLogic(req, httpSession, sqlSession);
		logger.info("initDisplay end");
		includePage(req, res, TodoConstant.MAIN_PAGE);
	}

	/**
	 * タスク追加ページ表示
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addTaskPage(
			HttpServletRequest req, 
			HttpServletResponse res,
			HttpSession httpSession,
			SqlSession sqlSession) throws ServletException, IOException {
		logger.info("addTaskPage start");
		// 成功時メッセージ
		String message = (String) httpSession.getAttribute("message");
		if (message != null) {
			req.setAttribute("message", message);
			httpSession.removeAttribute("message");
		}
		// 失敗時メッセージ
		String errorMessage = (String) httpSession.getAttribute("errorMessage");
		if (errorMessage != null) {
			req.setAttribute("errorMessage", message);
			httpSession.removeAttribute("errorMessage");
		}
		req.setAttribute("userId", req.getParameter("userId"));
		logger.info("addTaskPage end");
		includePage(req, res, TodoConstant.ADD_NEW_TASK);
	}

	/**
	 * タスク追加ボタン処理
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addTask(
			HttpServletRequest req, 
			HttpServletResponse res,
			HttpSession httpSession,
			SqlSession sqlSession) throws ServletException, IOException {
		logger.info("addTask start");
		req = service.registerLogic(req, httpSession, sqlSession);
		sqlSession.close();
		logger.info("addTaskPage end");
		res.sendRedirect(req.getContextPath() + TodoConstant.TODO_LIST + TodoConstant.TODO_LIST_NEW);
	}

	/**
	 * タスク消去ボタン処理
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteTask(
			HttpServletRequest req, 
			HttpServletResponse res,
			HttpSession httpSession,
			SqlSession sqlSession) throws ServletException, IOException {
		logger.info("deleteTask start");
		req = service.logicalDeleteLogic(req, httpSession, sqlSession); // 論理削除
		sqlSession.close();
		logger.info("deleteTask end");
	}

	/**
	 * タスク編集ページ表示
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editTask(
			HttpServletRequest req, 
			HttpServletResponse res,
			HttpSession httpSession,
			SqlSession sqlSession) throws ServletException, IOException {
		logger.info("editTask start");
		req = service.editTaskLogic(req, httpSession, sqlSession);
		sqlSession.close();
		logger.info("editTask end");
		includePage(req, res, TodoConstant.EDIT_TASK_PAGE);
	}

	/**
	 * タスク更新処理
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateTask(
			HttpServletRequest req,
			HttpServletResponse res,
			HttpSession httpSession,
			SqlSession sqlSession) throws ServletException, IOException {
		logger.info("updateTask start");
		service.updateTaskLogic(req, res, httpSession, sqlSession);
		sqlSession.close();
		logger.info("updateTask end");
	}

	/**
	 * Getアクセス時のデフォルトメソッド
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	/**
	 * インクルード
	 * @param req
	 * @param res
	 * @param page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void includePage(HttpServletRequest req, HttpServletResponse res, String page)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(page);
		dispatcher.include(req, res);
	}

	/**
	 * フォワード
	 * @param req
	 * @param res
	 * @param page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void forwardPage(HttpServletRequest req, HttpServletResponse res, String page)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(page);
		dispatcher.forward(req, res);
	}

}
