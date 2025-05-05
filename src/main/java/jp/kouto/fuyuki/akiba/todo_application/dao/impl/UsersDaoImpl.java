package jp.kouto.fuyuki.akiba.todo_application.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import jp.kouto.fuyuki.akiba.todo_application.common.TodoConstant;
import jp.kouto.fuyuki.akiba.todo_application.dao.UsersDao;
import jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto;

public class UsersDaoImpl implements UsersDao {
	
	@Override
	/**
	 * 実装側説明
	 * SqlSessionに対しての責任はDaoを呼び出す側に持たせることで、
	 * サービスクラスとの依存度を下げた。
	 * また、MyBatisはDaoインターフェースに直接アノテーションを書くことも可能だが、
	 * あえてそうせず、実装側でxmlリソースを指定することで、Daoインターフェースは
	 * 機能だけを用意し、どのXMLかという点に無関心とした。後からインターフェースを使いやすくなるはず。
	 */
	 public UsersDto getUser(long userId, SqlSession session) {
		 UsersDto result = new UsersDto();
		 Map<String, Object> params = new HashMap<>();
		 params.put("userId", userId);
		 result = session.selectOne("jp.kouto.fuyuki.akiba.todo_application.mapper.UserMapper.getUserById", params);
		 return result;
	 }
	
	@Override
	public Long insertUser(String username, String email, String password_hash, SqlSession sqlSession) {
		int result = 0;
		Map<String, Object> params = new HashMap<>();
		params.put("username", username);
		params.put("email", email);
		params.put("password_hash", password_hash);
		params.put("role", TodoConstant.GENERAL_USER_ROLE);
		sqlSession.insert("jp.kouto.fuyuki.akiba.todo_application.mapper.UserMapper.insertUser", params);
		sqlSession.commit();
		return Long.parseLong(params.get("id").toString().trim());
	}
	
	@Override
	public void insertTempUser(int verifyCode, String receiptCode, String username,
			 String email, String password_hash, SqlSession sqlSession) {
		Map<String, Object> params = new HashMap<>();
		params.put("verify_code", verifyCode);
		params.put("receipt_number", receiptCode);
		params.put("username", username);
		params.put("email", email);
		params.put("password_hash", password_hash);
		sqlSession.insert("jp.kouto.fuyuki.akiba.todo_application.mapper.UserMapper.insertTempUser", params);
		sqlSession.commit();
	}
	
	@Override
	public int selectVerifyCode(String receiptCode, SqlSession sqlSession) {
		Map<String, Object> params = new HashMap<>();
		params.put("receipt_number", receiptCode);
		Integer result = sqlSession.selectOne("jp.kouto.fuyuki.akiba.todo_application.mapper.UserMapper.getVerifyCode", params);
		return result;
	}
	
	@Override
	public Map<String, Object> selectTempUser(String receiptCode, SqlSession sqlSession) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		params.put("receipt_number", receiptCode);
		result = sqlSession.selectOne("jp.kouto.fuyuki.akiba.todo_application.mapper.UserMapper.getTempUser", params);
		return result;
	}
	 
	@Override
	 public List<UsersDto> getUserList(SqlSession sqlSession) {
		 List<UsersDto> resultList = new ArrayList<>();
		 resultList = sqlSession.selectList("jp.kouto.fuyuki.akiba.todo_application.mapper.UserMapper.getUserList");
		 return resultList;
	 }
	 
	@Override
	 public int deleteUser(long id, SqlSession session) {
		 int resultCnt = 0;
		 
		 return resultCnt;
	 }

}
