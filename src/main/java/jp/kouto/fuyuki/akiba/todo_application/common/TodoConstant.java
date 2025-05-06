package jp.kouto.fuyuki.akiba.todo_application.common;

public class TodoConstant {
	
	// URL関連
	/**
	 * index.jspのUrl
	 */
	public static final String INDEX_PAGE = "/index.jsp";
	
	/**
	 * メインページ
	 */
	public static final String MAIN_PAGE = "/screen/todo_list.jsp";
	
	/**
	 * ログインページUrl
	 */
	public static final String LOGIN_PAGE = "/screen/login.jsp";

	/**
	 * 会員登録ページUrl
	 */
	public static final String REGISTER_PAGE = "/screen/register.jsp";
	
	/**
	 * 仮登録ページUrl
	 */
	public static final String VERIFY_CODE_PAGE = "/screen/verify_code.jsp";
	
	/**
	 * 新規タスク登録ページUrl
	 */
	public static final String ADD_NEW_TASK = "/screen/add_task.jsp";
	
	/**
	 * ログアウトページUrl
	 */
	public static final String LOGOUT_PAGE = "/logout.jsp";
	
	/**
	 * ユーザー一覧Url
	 */
	public static final String ADMIN_MAIN_PAGE = "/admin/user_list.jsp";
	
	// サーブレットのUrl
	/**
	 * Todoリスト一覧
	 */
	public static final String TODO_LIST = "/todo_list";
	
	/**
	 * ログイン
	 */
	public static final String LOGIN = "/login";
	/**
	 * ユーザー登録
	 */
	public static final String REGISTER = "/register";
	
	/**
	 * ログアウト
	 */
	public static final String LOGOUT = "/logout";
	
	/**
	 * ユーザー一覧
	 */
	public static final String USER_LIST = "/user_list";
	
	// parm変数一覧
	/*
	 * セッションエラー
	 */
	public static final String LOGOUT_SESSION_ERROR = "?parm=session_error";
	
	
	/**
	 * 新規タスク登録
	 */
	public static final String TODO_LIST_NEW = "?parm=new";
	
	
	// 権限関連
	/**
	 * 一般権限
	 */
	public static final String GENERAL_USER_ROLE = "general";
	
	/**
	 * 管理者権限
	 */
	public static final String ADMIN_USER_ROLE = "admin";
	
	// 定数
	/**
	 * DB登録日付型
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
}
