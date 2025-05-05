package jp.kouto.fuyuki.akiba.todo_application.dto;

public class UsersDto {
	/**
	 * ユーザーID
	 */
	private long id;
	
	/**
	 * ユーザー名
	 */
	private String username;
	
	/**
	 * メールアドレス
	 */
	private String email;
	
	/**
	 * ハッシュ化パスワード
	 */
	private String password_hash;
	
	/**
	 * 権限
	 */
	private String role;
	
	/**
	 * 更新日
	 */
	private String updateDate;
	
	/**
	 * 作成日
	 */
	private String createDate;
	
	/**
	 * 論理削除日付
	 */
	private String deletedDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return password_hash;
	}

	public void setPasswordHash(String password_hash) {
		this.password_hash = password_hash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(String deletedDate) {
		this.deletedDate = deletedDate;
	}
	
}
