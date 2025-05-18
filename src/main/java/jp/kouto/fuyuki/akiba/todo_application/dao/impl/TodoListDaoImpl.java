package jp.kouto.fuyuki.akiba.todo_application.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import jp.kouto.fuyuki.akiba.todo_application.dao.TodoListDao;
import jp.kouto.fuyuki.akiba.todo_application.dto.TodoListDto;
import jp.kouto.fuyuki.akiba.todo_application.exceptions.RyzaDBException;

public class TodoListDaoImpl implements TodoListDao {

	 public List<TodoListDto> getListById(long userId, SqlSession session) throws RyzaDBException {
		 try {
			 List<TodoListDto> resultList = new ArrayList<>();
			 Map<String, Object> params = new HashMap<>();
			 params.put("userId", userId);
			 resultList = session.selectList("jp.kouto.fuyuki.akiba.todo_application.mapper.TodoListMapper.getTodoListByUserId", params);
			 return resultList;
		 }catch (Exception e) {
			 throw new RyzaDBException("DB Error",e);
		 }
	 }

	 public int insertTask(long userId, String content, Date due_date, SqlSession session) throws RyzaDBException {
		 try{
			 int cnt = 0;
		     Map<String ,Object> params = new HashMap<>();
		     params.put("userId", userId);
		     params.put("content", content);
		     params.put("dueDate", due_date);
		     params.put("status", "未完了");
		     cnt = session.insert("jp.kouto.fuyuki.akiba.todo_application.mapper.TodoListMapper.insertTask", params);
		     session.commit();
		     return cnt;
		 } catch(Exception e) {
			 throw new RyzaDBException("DB Error", e);
		 }
	 }
}
