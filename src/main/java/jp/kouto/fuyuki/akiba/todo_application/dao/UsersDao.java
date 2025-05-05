package jp.kouto.fuyuki.akiba.todo_application.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto;

public interface UsersDao {
	
	/**
	 * idからユーザーを取得する
	 * @param id ユーザーID
	 * @return
	 */
	 public UsersDto getUser(long id, SqlSession session);
	 
	 /**
	  * ユーザーをすべて取得する
	  * @param session
	  * @return
	  */
	 public List<UsersDto> getUserList(SqlSession session) ;
	 
	 /**
	  * ユーザー登録
	  * @param userName
	  * @param email
	  * @param password_hash
	  * @param sqlSession
	  * @return
	  */
	 public Long insertUser(String username, String email, String password_hash, SqlSession sqlSession);
	 
	 /**
	  * ユーザー仮登録
	  * @param verifyCode
	  * @param receiptCode
	  * @param userName
	  * @param email
	  * @param password_hash
	  * @param sqlSession
	  */
	 public void insertTempUser(int verifyCode, String receiptCode, String username,
			 String email, String password_hash, SqlSession sqlSession);
	
	 /**
	  * 認証番号検索
	  * @param receiptCode
	  * @param sqlSession
	  * @return
	  */
	 public int selectVerifyCode(String receiptCode, SqlSession sqlSession);
	 
	 /**
	  * 一時保存ユーザー取得
	  * @param receiptCode
	  * @param sqlSession
	  * @return
	  */
	 public Map<String, Object> selectTempUser(String receiptCode, SqlSession sqlSession);
	 
	 /**
	  * idで指定したユーザーを削除する
	  */
	 public int deleteUser(long id, SqlSession session);
	 
	
}
