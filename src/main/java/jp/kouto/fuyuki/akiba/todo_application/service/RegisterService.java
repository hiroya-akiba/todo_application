package jp.kouto.fuyuki.akiba.todo_application.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jp.kouto.fuyuki.akiba.todo_application.common.TodoConstant;
import jp.kouto.fuyuki.akiba.todo_application.dao.UsersDao;
import jp.kouto.fuyuki.akiba.todo_application.exceptions.EmailSenderException;
import jp.kouto.fuyuki.akiba.todo_application.exceptions.RyzaDBException;
import jp.kouto.fuyuki.akiba.todo_application.util.CertificationUtil;
import jp.kouto.fuyuki.akiba.todo_application.util.DaoFactory;
import jp.kouto.fuyuki.akiba.todo_application.util.EmailSender;

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
		Long registeredId = 0L; 
		try {
			registeredId = dao.insertUser(userName, email, password_hash, sqlSession);
		} catch(RyzaDBException e) {
			// エラーページへ飛ばす
			e.printStackTrace();
		}
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
		try {
			dao.insertTempUser(verifyCode, reciptCode, userName, email, password_hash, sqlSession);
		} catch(RyzaDBException e) {
			// エラーページへ飛ばす
			e.printStackTrace();
		}
		req.setAttribute("receipt_code", reciptCode);
		try {
			EmailSender sender = new EmailSender();
			sender.sendMail(email, TodoConstant.MAIL_TITLE, sendSuccess(verifyCode));
		} catch(EmailSenderException e) {
			req.setAttribute("errorMessage", sendFailure());
		}
		return req;
	}

	/**
	 * 成功時メッセージ
	 * @param varifyCode
	 * @return
	 */
	private String sendSuccess (int varifyCode) {
		return """
				以下の認証コードを入力してください：
				
				認証コード：""" + varifyCode
				
				+ """
				
				※このコードは10分間有効です。
				※心当たりがない場合は、このメールを破棄してください。
				""";
	}

	/**
	 * 失敗時メッセージ
	 * @return 
	 */
	private String sendFailure() {
		return "メール送信に失敗しました。メールが届かない場合、お手数ですが再度登録画面より登録ください。";
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
		Integer storedVerifyCode = 0;
		try {
			storedVerifyCode = dao.selectVerifyCode(receiptCode, sqlSession);
		} catch(RyzaDBException e) {
			// エラーページへ飛ばす
			e.printStackTrace();
		}
		if(storedVerifyCode != 0 && storedVerifyCode==verifyCode) {
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
		Map<String, Object> record = new HashMap<>();
		try {
			record = dao.selectTempUser(receiptCode, sqlSession);
		} catch(RyzaDBException e) {
			// エラーページへ飛ばす
			e.printStackTrace();
		}
		String username = (String) record.get("username");
		String email = (String) record.get("email");
		String password_hash = (String) record.get("password_hash");
		req.setAttribute("username", username);
		req.setAttribute("email", email);
		req.setAttribute("password_hash", password_hash);
		return req;
	}
}

