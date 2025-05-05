package jp.kouto.fuyuki.akiba.todo_application.util;

import jp.kouto.fuyuki.akiba.todo_application.dao.TodoListDao;
import jp.kouto.fuyuki.akiba.todo_application.dao.UsersDao;
import jp.kouto.fuyuki.akiba.todo_application.dao.impl.TodoListDaoImpl;
import jp.kouto.fuyuki.akiba.todo_application.dao.impl.UsersDaoImpl;

public class DaoFactory {
	public static UsersDao getUsersDao() {
		return new UsersDaoImpl(); // ここでだけ実装をインスタンス化する
	}
	
	public static TodoListDao getTodoListDao() {
		return new TodoListDaoImpl(); // ここでだけ実装をインスタンス化する
	}
	
}
