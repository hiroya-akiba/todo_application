package jp.kouto.fuyuki.akiba.todo_application.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jp.kouto.fuyuki.akiba.todo_application.common.MyBatisConstant;

public class MyBatisUtil {
	private static SqlSessionFactory factory;
	
	public static SqlSessionFactory getSqlSessionFactory(ServletContext context) throws RuntimeException, IOException {
		if(factory == null) {
			try {
				// MyBatis - Resources - クラスローダーに関するAPI
				// Servletにもクラスローダーがあり、競合してしまうため、MyBatisの自動設定される
				// クラスローダーではなくServletのクラスローダーを使用することを明示的に示す。
				
				// 1. MyBatisのクラスローダー
				InputStream is = Resources.getResourceAsStream(MyBatisConstant.MYBATIS_CONFIG);
				
				//Resources.setDefaultClassLoader(Thread.currentThread().getContextClassLoader());
				
				//ClassLoader cl = Thread.currentThread().getContextClassLoader();
				//InputStream is = Resources.getResourceAsStream(cl, MyBatisConstant.MYBATIS_CONFIG);
				
				//InputStream is = cl.getResourceAsStream(MyBatisConstant.MYBATIS_CONFIG);
				//InputStream is = new FileInputStream("C:\\pleiades\\2023-12\\workspace\\todo_application\\target\\todo_application\\WEB-INF\\classes\\mybatis-config.xml");
				//InputStream is = context.getResourceAsStream(MyBatisConstant.MYBATIS_CONFIG);
				if (is == null) {
				    throw new RuntimeException("mybatis-config.xml not found");
				}
				factory = new SqlSessionFactoryBuilder().build(is);
			} catch(RuntimeException e) {
				throw new RuntimeException("MyBatisの設定ファイルの読み込みに失敗しました", e);
			}
		}
		return factory;
	}

	/**
	 * リクエストにSqlSessionが存在しない場合に作成して返却する
	 * @param req
	 * @return
	 */
	public static SqlSession getSqlSession(HttpServletRequest req, ServletContext context) throws IOException {
		SqlSession sqlSession = (SqlSession) req.getAttribute("sqlSession");
		if(sqlSession == null) {
			if(factory == null) factory = getSqlSessionFactory(context);
			sqlSession = factory.openSession();
			req.getSession().setAttribute("sqlSession", sqlSession);
		}
		return sqlSession;
	}

}
