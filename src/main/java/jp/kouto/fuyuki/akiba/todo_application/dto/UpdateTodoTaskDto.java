package jp.kouto.fuyuki.akiba.todo_application.dto;

public class UpdateTodoTaskDto {
	/**
	 * ユーザーID
	 */
	private String userId;
	/**
	 * ID
	 */
    private String id;
    /**
     * 内容
     */
    private String content;
    /**
     * ステータス
     */
    private String status;
    /**
     * 期限
     */
    private String dueDate;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
}
