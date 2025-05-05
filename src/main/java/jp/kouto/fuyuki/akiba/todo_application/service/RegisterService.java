package jp.kouto.fuyuki.akiba.todo_application.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jp.kouto.fuyuki.akiba.todo_application.dao.UsersDao;
import jp.kouto.fuyuki.akiba.todo_application.util.CertificationUtil;
import jp.kouto.fuyuki.akiba.todo_application.util.DaoFactory;

public class RegisterService {
	/**
	 * 登録処理
	 * @param req
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 */
	public HttpServletRequest register(HttpServletRequest req, SqlSession sqlSession) throws ServletException {
		String userName = (String) req.getAttribute("username");
		String email = (String) req.getAttribute("email");
		String password_hash = (String) req.getAttribute("password_hash");
		UsersDao dao = DaoFactory.getUsersDao();
		Long registeredId = dao.insertUser(userName, email, password_hash, sqlSession);
		req.setAttribute("userId", registeredId);
		req.setAttribute("completeFlg", registeredId>0 ? "true" : "false");
		return req;
	}
	
	/**
	 * 仮登録処理
	 * @param req
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 */
	public HttpServletRequest temporaryRegister(HttpServletRequest req, SqlSession sqlSession) throws ServletException {
		String userName = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		// emailは同じものが過去に使用されていないか、バリデーションチェック
		// パスワードはハッシュ化しておく
		String password_hash = CertificationUtil.hashPassword(password);
		// ランダムな6桁を生成してランダムな8桁の受付番号と紐づけて仮登録テーブルに保管
		int verifyCode = CertificationUtil.createVerifyCode(); 
		String reciptCode = CertificationUtil.createReceiptCode(); 
		UsersDao dao = DaoFactory.getUsersDao();
		dao.insertTempUser(verifyCode, reciptCode, userName, email, password_hash, sqlSession);
		req.setAttribute("receipt_code", reciptCode);
		
		return req;
	}
	
	/**
	 * 2段階認証
	 * @param req
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 */
	public HttpServletRequest multiFactorVerification (HttpServletRequest req, SqlSession sqlSession) throws ServletException {
		int verifyCode = Integer.parseInt(req.getParameter("authCode"));
		String receiptCode = req.getParameter("receipt_code");
		UsersDao dao = DaoFactory.getUsersDao();
		Integer storedVerifyCode = dao.selectVerifyCode(receiptCode, sqlSession);
		if(storedVerifyCode != null && storedVerifyCode==verifyCode) {
			req.setAttribute("MFV", true);
			return req;
		} else {
			req.setAttribute("receipt_code", receiptCode);
			req.setAttribute("MFV", false);
			return req;
		}
		
	}
	
	/**
	 * 本登録用データ取得
	 * @param req
	 * @param sqlSession
	 * @return
	 * @throws ServletException
	 */
	public HttpServletRequest fetchTempData (HttpServletRequest req, SqlSession sqlSession) throws ServletException {
		String receiptCode = req.getParameter("receipt_code");
		UsersDao dao = DaoFactory.getUsersDao();
		Map<String, Object> record = dao.selectTempUser(receiptCode, sqlSession);
		String username = (String) record.get("username");
		String email = (String) record.get("email");
		String password_hash = (String) record.get("password_hash");
		req.setAttribute("username", username);
		req.setAttribute("email", email);
		req.setAttribute("password_hash", password_hash);
		return req;
	}
}

 