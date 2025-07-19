package jp.kouto.fuyuki.akiba.todo_application.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import jp.kouto.fuyuki.akiba.todo_application.dto.TodoListDto;
import jp.kouto.fuyuki.akiba.todo_application.exceptions.RyzaDBException;

public interface TodoListDao {

	/**
	 * ユーザーIDからユーザー情報を取得する
	 * @param userId
	 * @param session
	 * @return
	 * @throws RyzaDBException
	 */
	public List<TodoListDto> getListById(long userId, SqlSession session) throws RyzaDBException;
	
	/**
	 * タスクを追加する
	 * @param userId
	 * @param content
	 * @param due_date
	 * @param session
	 * @return
	 * @throws RyzaDBException
	 */
	public int insertTask(long userId, String content, Date due_date, SqlSession session) throws RyzaDBException;
	
	
	/**
	 * タスクを論理削除する
	 * @param userId
	 * @param contentId
	 * @throws RyzaDBException
	 */
	public void logicalDeleteTask(long userId, String contentId, SqlSession session) throws RyzaDBException;
	
	
	/**
	 * タスクを編集する
	 * @param userId
	 * @param contentId
	 * @param session
	 * @throws RyzaDBException
	 */
	public List<TodoListDto> editTask(long userId, List<String> contentId, SqlSession session) throws RyzaDBException;
	
}
