package jp.kouto.fuyuki.akiba.todo_application.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import jp.kouto.fuyuki.akiba.todo_application.dto.TodoListDto;
import jp.kouto.fuyuki.akiba.todo_application.exceptions.RyzaDBException;

public interface TodoListDao {

	public List<TodoListDto> getListById(long userId, SqlSession session) throws RyzaDBException;
	
	public int insertTask(long userId, String content, Date due_date, SqlSession session) throws RyzaDBException;
}
