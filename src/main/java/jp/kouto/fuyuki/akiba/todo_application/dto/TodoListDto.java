package jp.kouto.fuyuki.akiba.todo_application.dto;

public class TodoListDto {
	/**
	 * リストID
	 */
	private long id;
	/**
	 * ユーザーID
	 */
	private long userId;
	/**
	 * 内容 
	 */
	private String content;
	/**
	 * ステータス
	 */
	private String status;
	/**
	 * 締切日
	 */
	private String dueDate;
	/**
	 * 作成日
	 */
	private String createDate;
	/**
	 * 更新日付
	 */
	private String updateDate;
	/**
	 * 削除日
	 */
	private String deletedDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(String deletedDate) {
		this.deletedDate = deletedDate;
	}
	
}
