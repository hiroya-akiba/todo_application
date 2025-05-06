<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ユーザー一覧（管理者用）</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
</head>
<body>
<div class="l-container">
    <header class="l-header nav">
        <h1 class="text-bold">Todo Task Manager</h1>
        <ul class="nav-list">
                <li class="nav-item"><a href="<%= request.getContextPath()%>/user_list" class="nav-link">ユーザー一覧</a></li>
                <li class="nav-item"><a href="<%= request.getContextPath()%>/logout" class="nav-link">ログアウト</a></li>
            </ul>
    </header>

    <main class="l-main">
        <div class="card">
            <h2 class="card-title">ユーザー管理</h2>
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>ユーザー名</th>
                    <th>メールアドレス</th>
                    <th>権限</th>
                    <th>作成日</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<UsersDto> userList = (List<UsersDto>) request.getAttribute("userList");
                    if (userList != null) {
                        for (UsersDto user : userList) {
                %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getUsername() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getRole() %></td>
                    <td><%= user.getCreateDate() %></td>
                    <td>
                        <!-- 編集 -->
                        <a href="admin_user_edit.jsp?id=<%= user.getId() %>" class="action-icon" title="編集">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                                <path d="M12.146.854a.5.5 0 0 1 .708 0l2.292 2.292a.5.5 0 0 1 0 .708l-10 10A.5.5 0 0 1 4.5 14H2a1 1 0 0 1-1-1v-2.5a.5.5 0 0 1 .146-.354l10-10zM11.207 2L13 3.793 14.793 2 13 0.207 11.207 2zM1.5 13.5V12h1.5l9-9L10.5 4.5l-9 9H1.5z"/>
                            </svg>
                        </a>

                        <!-- 削除 -->
                        <a href="AdminUserServlet?parm=delete&id=<%= user.getId() %>" class="action-icon delete-icon" title="削除" onclick="return confirm('本当に削除しますか？');">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24">
                                <g stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M3 6h18M9 6V4h6v2"/>
                                    <rect x="5" y="6" width="14" height="16" rx="2"/>
                                    <line x1="9" y1="10" x2="9" y2="18"/>
                                    <line x1="12" y1="10" x2="12" y2="18"/>
                                    <line x1="15" y1="10" x2="15" y2="18"/>
                                </g>
                            </svg>
                        </a>

                        <!-- タスク表示 -->
                        <a href="AdminUserServlet?parm=taskList&userId=<%= user.getId() %>" class="action-icon" title="タスク一覧">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M2 12.5a.5.5 0 0 1 .5-.5H13a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5H13a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5H13a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5z"/>
                            </svg>
                        </a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5">ユーザー情報が取得できませんでした。</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <div class="card-footer">
                <a href="admin_user_edit.jsp" class="btn">＋ 新規ユーザー追加</a>
            </div>
        </div>
    </main>

    <footer class="l-footer">
        &copy; 2025 管理者ページ
    </footer>
</div>
</body>
</html>