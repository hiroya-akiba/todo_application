<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.kouto.fuyuki.akiba.todo_application.dto.TodoListDto" %>
<%@ page import="jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto" %>

<%
    List<TodoListDto> taskList = (List<TodoListDto>) request.getAttribute("editList");
    UsersDto user = (UsersDto) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>タスク編集</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <style>
    .card { margin-bottom: 1.5rem; padding: 1rem; border: 1px solid #ccc; border-radius: 8px; }
    .form label { display: block; margin-top: 0.5rem; }
    .form input, .form select { width: 100%; padding: 0.5rem; }
    .form button { margin-top: 1rem; }
  </style>
</head>
<body>
  <div class="edit-container">
    <h2 class="text-bold">タスク編集</h2>
    <% if (taskList != null && !taskList.isEmpty()) { %>
      <% for (TodoListDto task : taskList) { %>
        <div id="messageBox" class="message mt-2" style="display: none;"></div>
        <div class="card">
          <div class="form">
            <input type="hidden" id="id_<%= task.getId() %>" value="<%= task.getId() %>">
            <input type="hidden" id="userId_<%= task.getId() %>" value="<%= task.getUserId() %>">
            
            <label for="content_<%= task.getId() %>">内容</label>
            <input type="text" id="content_<%= task.getId() %>" value="<%= task.getContent() %>" required>
            
            <label for="status_<%= task.getId() %>">ステータス</label>
            <select id="status_<%= task.getId() %>">
              <option value="未着手" <%= "未着手".equals(task.getStatus()) ? "selected" : "" %>>未着手</option>
              <option value="進行中" <%= "進行中".equals(task.getStatus()) ? "selected" : "" %>>進行中</option>
              <option value="完了" <%= "完了".equals(task.getStatus()) ? "selected" : "" %>>完了</option>
            </select>
            
            <label for="due_<%= task.getId() %>">締切日</label>
            <input type="date" id="due_<%= task.getId() %>" value="<%= task.getDueDate() %>" required>
            <button type="button" onclick="updateTask(<%= task.getId() %>)">更新</button>
          </div>
        </div>
      <% } %>
    <% } else { %>
      <p>編集対象のタスクはありません。</p>
    <% } %>
  </div>
</body>
<script>

/**
 * 更新ボタン押下処理
 */
function updateTask(taskId) {
  console.log("taskId:", taskId);
  const userId = document.getElementById("userId_" + taskId).value;
  console.log("userId:", userId);
  const content = document.getElementById("content_"+taskId).value;
  console.log("content:", content);
  const status = document.getElementById("status_"+taskId).value;
  console.log("status:", status);
  const dueDate = document.getElementById("due_"+taskId).value;
  console.log("dueDate:", dueDate);

  const jsonData = {
    userId: userId,
    id: taskId,
    content: content,
    status: status,
    dueDate: dueDate
  };

  fetch('<%= request.getContextPath() %>/todo_list?parm=update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json' 
    },
    body: JSON.stringify(jsonData)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('通信失敗');
    }
    return response.json(); // サーバーからJSON返すなら
  })
  .then(data => {
    showMessage('タスクを1件更新しました。', true);
  })
  .catch(err => {
    showMessage('更新に失敗しました', false);
  });
}
/**
 * 更新処理後メッセージ出力処理
 */
function showMessage(text, isSuccess = true) {
  const box = document.getElementById('messageBox');
  box.textContent = text;

  // 一旦クラスをクリア
  box.className = 'message mt-2';

  // 成功 or エラー用クラスを追加
  box.classList.add(isSuccess ? 'success' : 'error');

  // 表示する
  box.style.display = 'block';

  // 数秒後に消すなら下記を追加（任意）
  setTimeout(() => {
    box.style.display = 'none';
  }, 3000);
}
</script>
</html>