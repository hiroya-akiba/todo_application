package jp.kouto.fuyuki.akiba.todo_application.controller;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.kouto.fuyuki.akiba.todo_application.common.TodoConstant;
import jp.kouto.fuyuki.akiba.todo_application.util.MyBatisUtil;

public class TopServlet extends HttpServlet {

	final static Logger logger = LoggerFactory.getLogger(TopServlet.class); 

	@Override
	public void init() throws ServletException{
		logger.info("### Start application ###");
		try {
		    // SqlSession取得用
		    SqlSessionFactory sqlSsnFc = MyBatisUtil.getSqlSessionFactory(getServletContext());
		} catch(Exception e){
			logger.error("MyBatisの設定ファイル読み込みに失敗しました。", e);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.debug("doPost");
		includePage(req, res, TodoConstant.INDEX_PAGE);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		logger.debug("doGet");
		doPost(req, res);
	}

	public void includePage(HttpServletRequest req, HttpServletResponse res, String page) throws ServletException, IOException {
		logger.debug("includePage");
		RequestDispatcher dispatcher =  req.getRequestDispatcher(page);
		dispatcher.include(req, res);
	}
	
	public void forwardPage(HttpServletRequest req, HttpServletResponse res, String page) throws ServletException, IOException {
		logger.debug("forwardPage");
		RequestDispatcher dispatcher =  req.getRequestDispatcher(page);
		dispatcher.forward(req, res);
	}

}
