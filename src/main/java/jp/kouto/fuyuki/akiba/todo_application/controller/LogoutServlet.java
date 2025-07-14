package jp.kouto.fuyuki.akiba.todo_application.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.kouto.fuyuki.akiba.todo_application.common.TodoConstant;

public class LogoutServlet extends HttpServlet {
	
	final static Logger logger = LoggerFactory.getLogger(LogoutServlet.class);
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    // HttpSessionの確認(falseでセッションがない場合は新規作成しない)
		HttpSession session = req.getSession(false);
		if(req.getParameter("parm")==null) {
			if(session.getAttribute("user")!=null) {
			// 正常にログアウトした場合
				// セッションを破棄
				logger.info("Logout succeeded");
				session.invalidate();
				res.sendRedirect(req.getContextPath() + TodoConstant.LOGOUT_PAGE);
			} else {
			// 戻るボタンなどを押してセッションを持っていなかった場合
				// メッセージを付与してログアウト画面へ
				logger.info("Can't find session");
				res.sendRedirect(req.getContextPath() + TodoConstant.LOGOUT_PAGE);
			}
		} else {
			if (req.getParameter("parm").equals("session_error")) {
			// セッションエラー
				logger.info("Forbidden action");
				req.setAttribute("errorTitle", "セッションエラー");
				req.setAttribute("errorMessage", "不正な操作、または15分以上操作が無かったためログアウトされました。");
				includePage(req, res, TodoConstant.LOGOUT_PAGE);
			}
		}
		
	}
	
	@Override
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
