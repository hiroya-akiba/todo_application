package jp.kouto.fuyuki.akiba.todo_application.service;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jp.kouto.fuyuki.akiba.todo_application.dao.UsersDao;
import jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto;
import jp.kouto.fuyuki.akiba.todo_application.util.CertificationUtil;
import jp.kouto.fuyuki.akiba.todo_application.util.DaoFactory;

public class LoginService {
	public HttpServletRequest certificate(HttpServletRequest req, SqlSession sqlSession) throws ServletException, IOException{
		// ユーザー情報の確認
		long id = Integer.parseInt(req.getParameter("userId"));
		UsersDao dao = DaoFactory.getUsersDao();
		UsersDto user = dao.getUser(id, sqlSession);
		// DB取得情報が無い場合
		if(user == null) {
			req.setAttribute("isGlant", false);
			return req;
		}
		
		// パスワードの照合
		String registeredPass = user.getPasswordHash().trim();
		String inputPass = req.getParameter("password"); 
		if(!registeredPass.equals(CertificationUtil.hashPassword(inputPass))) {
			req.setAttribute("isGlant", false);
			return req;
		}
		req.setAttribute("user", user);
		req.setAttribute("isGlant", true);
		return req;
	}
}
