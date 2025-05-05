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
import jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto;
import jp.kouto.fuyuki.akiba.todo_application.service.LoginService;
import jp.kouto.fuyuki.akiba.todo_application.service.RegisterService;
import jp.kouto.fuyuki.akiba.todo_application.util.MyBatisUtil;

public class LoginServlet extends HttpServlet {
	
	LoginService loginService = new LoginService();
	RegisterService registerService = new RegisterService();

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    // HttpSessionの確認(falseでセッションがない場合は新規作成しない)
		HttpSession session = req.getSession(false);
		// セッションが無い場合
		if(session==null) {
			// 問答無用でログインページへ
			includePage(req, res, TodoConstant.LOGIN_PAGE);
			return;
		}
		
		// ログインしている場合
		if(session.getAttribute("isUser") != null) {
			// メインのページにフォワード
			forwardPage(req, res, TodoConstant.TODO_LIST);
			return;
		}
		SqlSession sqlSession = MyBatisUtil.getSqlSession(req, getServletContext()); 
		if(req.getParameter("parm") != null) {
			// ログイン
			if(req.getParameter("parm").equals("cert")) {
				doCertificate(req, res, sqlSession);
				
			// 新規会員登録
			} else if(req.getParameter("parm").equals("new")) {
				forwardPage(req, res, TodoConstant.REGISTER_PAGE);
				
			// 仮登録
			} else if(req.getParameter("parm").equals("temporary")) {
				doTemporaryRegister(req, res, sqlSession);
				
			// 本登録
			} else if(req.getParameter("parm").equals("register")) {
				doRegister(req, res, sqlSession);
				
			// その他のパターン
			} else {
				includePage(req, res, TodoConstant.LOGIN_PAGE);
			}
			
		// ログイン画面初期表示
		} else {
			includePage(req, res, TodoConstant.LOGIN_PAGE);
		}
	}
	
	/**
	 * 認証
	 * @param req 
	 * @param res
	 * @param sqlSession
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doCertificate(HttpServletRequest req, HttpServletResponse res, SqlSession sqlSession) throws ServletException, IOException {
		req = loginService.certificate(req, sqlSession);
		sqlSession.close();
		boolean isGrant = (boolean)req.getAttribute("isGlant");
		HttpSession session = req.getSession();
		session.setAttribute("isGrant", isGrant);
		// OKならHttpSessionを作成してメイン画面へ
		if(isGrant) {
			UsersDto user = (UsersDto) req.getAttribute("user");
			session.setMaxInactiveInterval(15 * 60); // セッションの有効期限を15分に設定
			session.setAttribute("user", user); // ユーザー情報をセッションに保存

			// 一般ユーザーの場合
			if (user.getRole().equals(TodoConstant.GENERAL_USER_ROLE)) {
				res.sendRedirect(req.getContextPath() + TodoConstant.TODO_LIST);
				
			// 管理ユーザーの場合
			} else if (user.getRole().equals(TodoConstant.ADMIN_USER_ROLE)) {
				res.sendRedirect(req.getContextPath() + TodoConstant.USER_LIST);
			}
		} else {
			// NGならログイン画面へ
			res.sendRedirect(req.getContextPath() +TodoConstant.LOGIN_PAGE);
		}
	}
	
	/**
	 * 仮登録
	 * @param req
	 * @param res
	 * @param sqlSession
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doTemporaryRegister(HttpServletRequest req, HttpServletResponse res, SqlSession sqlSession) throws ServletException, IOException{
		registerService.temporaryRegister(req, sqlSession);
		sqlSession.close();
		includePage(req, res, TodoConstant.VERIFY_CODE_PAGE);
	}
	
	/**
	 * 登録
	 * @param req
	 * @param res
	 * @param sqlSession
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doRegister(HttpServletRequest req, HttpServletResponse res, SqlSession sqlSession) throws ServletException, IOException{
		// 2段階認証
		req = registerService.multiFactorVerification(req, sqlSession);
		// 2段階認証成功の場合
		if((Boolean) req.getAttribute("MFV")) {
			req.removeAttribute("MFV");
			registerService.fetchTempData(req, sqlSession);
			registerService.register(req, sqlSession);
			sqlSession.close();
			req.setAttribute("completeFlg", true);
			includePage(req, res, TodoConstant.REGISTER_PAGE);
		// 2段階認証失敗の場合
		} else {
			sqlSession.close();
			req.removeAttribute("MFV");
			req.setAttribute("errorMessage", "認証コードが異なります。");
			includePage(req, res, TodoConstant.VERIFY_CODE_PAGE);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req, res);
	}

	/**
	 * インクルード
	 * (概略図)
	 * View → ServletA ⇔ SerlvetB(JSP)
	 * 　　　　　↓
	 * View ← ServletA ⇔ ServletC(JSP)
	 * ServletB(JSP)から処理が戻ってきたら、処理が続行されるためページ内をコンポーネント化することも可能
	 * @param req
	 * @param res
	 * @param page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void includePage(HttpServletRequest req, HttpServletResponse res, String page) throws ServletException, IOException {
		RequestDispatcher dispatcher =  req.getRequestDispatcher(page);
		dispatcher.include(req, res);
	}
	
	/**
	 * フォワード
	 * (概略図)
	 * View → ServletA
	 * 　　　　　↓
	 * View ← ServletB(JSP)
	 * @param req
	 * @param res
	 * @param page
	 * @throws ServletException
	 * @throws IOException
	 */
	public void forwardPage(HttpServletRequest req, HttpServletResponse res, String page) throws ServletException, IOException {
		RequestDispatcher dispatcher =  req.getRequestDispatcher(page);
		dispatcher.forward(req, res);
	}

}
