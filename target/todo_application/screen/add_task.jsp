<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>タスク追加</title>
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <div class="l-container">
        <header class="l-header nav">
            <h1 class="text-bold">Todo Task Manager</h1>
            <span>
            <% 
                UsersDto user = (UsersDto) session.getAttribute("user");
            %>
            </span>
            <ul class="nav-list">
                <li class="nav-item"><a href="<%= request.getContextPath()%>/todo_list" class="nav-link">ToDo一覧</a></li>
                <li class="nav-item"><a href="<%= request.getContextPath()%>/logout" class="nav-link">ログアウト</a></li>
            </ul>
        </header>
        <main class="l-main">
            <div class="card">
                <h2 class="card-title">新しいタスクを追加</h2>
                
                <%-- エラーメッセージ表示 --%>
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                %>
                <div class="message error mt-2">
                    <p><%= errorMessage%></p>
                </div>
                <%
                    }
                %>
                
                <%-- 正常終了時メッセージ表示 --%>
                <%
                    String message = (String) request.getAttribute("message");
                    if (message != null) {
                %>
                <div class="message success">
                    <p>タスクを1件登録しました。</p>
                </div>
                <%
                    }
                %>

                <div class="card-content">
                    <form action="<%= request.getContextPath() %>/todo_list?parm=register" method="POST" class="form">
                        <label for="content">タスク</label>
                        <input type="text" id="content" name="content" required>
                    
                        <label for="due_date">期限</label>
                        <input type="date" id="due_date" name="due_date" required>
                    
                        <input type="hidden" id="userId" name="userId" value="<%= user.getId() %>">
                        <button type="submit">タスクを追加</button>
                    </form>
                    <span><a href="<%= request.getContextPath()%>/todo_list" >一覧へ戻る</a></span>
                </div>
            </div>
        </main>
        <footer class="l-footer">
            &copy; 2025 hiroya akiba
        </footer>
    </div>
</body>
</html>