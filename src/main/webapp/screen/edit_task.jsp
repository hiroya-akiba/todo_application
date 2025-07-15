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
        <div class="card">
          <form action="<%= request.getContextPath() %>/todo_list?parm=update" method="POST" class="form">
            <input type="hidden" name="id" value="<%= task.getId() %>">
            <input type="hidden" name="userId" value="<%= task.getUserId() %>">

            <label for="content_<%= task.getId() %>">内容</label>
            <input type="text" id="content_<%= task.getId() %>" name="content" value="<%= task.getContent() %>" required>

            <label for="status_<%= task.getId() %>">ステータス</label>
            <select id="status_<%= task.getId() %>" name="status">
              <option value="未着手" <%= "未着手".equals(task.getStatus()) ? "selected" : "" %>>未着手</option>
              <option value="進行中" <%= "進行中".equals(task.getStatus()) ? "selected" : "" %>>進行中</option>
              <option value="完了" <%= "完了".equals(task.getStatus()) ? "selected" : "" %>>完了</option>
            </select>

            <label for="due_<%= task.getId() %>">締切日</label>
            <input type="date" id="due_<%= task.getId() %>" name="dueDate" value="<%= task.getDueDate() %>" required>

            <button type="submit">更新</button>
          </form>
        </div>
      <% } %>
    <% } else { %>
      <p>編集対象のタスクはありません。</p>
    <% } %>
  </div>
</body>
</html>