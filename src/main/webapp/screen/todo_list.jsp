<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.kouto.fuyuki.akiba.todo_application.dto.TodoListDto" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Todo</title>
  <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css" />
</head>
<body>
    <div class="l-container">
        <header class="l-header nav">
            <h1 class="text-bold">Todo Task Manager</h1>
            <span>
            <% 
                UsersDto user = (UsersDto) session.getAttribute("user");
                if (user != null) {
            %>
            </span>
            <ul class="nav-list">
                <li class="nav-item"><a href="<%= request.getContextPath()%>/todo_list" class="nav-link">ToDo一覧</a></li>
                <li class="nav-item"><a href="<%= request.getContextPath()%>/logout" class="nav-link">ログアウト</a></li>
            </ul>
        </header>
        <main class="l-main">
            <div class="card">
                <h1 class="card-title"><%= user.getUsername() %>のToDoリスト</h1>
                    <div class="card-content">
                    <p>
                        <form action="<%= request.getContextPath()%>/todo_list?parm=new" method="POST" class="form">
                            <input type="hidden" id="userId" name="userId" value="<%= user.getId() %>">
                            <button type="submit" class="btn">新規タスク追加</button>
                        </form>
                    </p>
                    <%
                        List<TodoListDto> todoList = (List<TodoListDto>) request.getAttribute("todoList");
                        if (todoList != null && !todoList.isEmpty()) {
                    %>
                    <table border="1">
                        <tr>
                            <th>ID <button onclick="sortTableByString(0)" class="btn">⇅</button></th>
                            <th>内容<button onclick="sortTableByString(1)" class="btn">⇅</button></th>
                            <th>期限日<button onclick="sortTableByDate(2)" class="btn">⇅</button></th>
                            <th>状態<button onclick="sortTableByString(3)" class="btn">⇅</button></th>
                        </tr>
                        <%
                            for (TodoListDto todo : todoList) {
                        %>
                        <tr>
                            <td><%= todo.getId() %></td>
                            <td><%= todo.getContent() %></td>
                            <td><%= todo.getDueDate() %></td>
                            <td><%= todo.getStatus() %></td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                    <%
                        } else {
                    %>
                        <p>現在、登録されているタスクはないぜ。</p>
                    <%
                        }
                    %>
                </div>
            </div>
            <div class="card">
                <h2 class="card-title">次のコンテンツ</h2>
                <div class="card-content">
                    <table>
                        <tr>
                            <td>
                                なんかコンテンツ
                            </td>
                            <td>
                                <span><a href="<%=request.getContextPath()%>/logout"> ログアウト </a></span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <%
                } else {
            %>
                <span>貴様、どうやってこのページに...？！</span>
                <span><a href="<%=request.getContextPath()%>/login">ログインページ</a></span>
            <%
                }
            %>
        </main>
        <footer class="l-footer">
            &copy; 2025 hiroya akiba
        </footer>
    </div> <%-- l-containerのエンドタグ --%>
    <script language="javascript">
    let isAscending = true;
    
    function sortTableByString(columnIndex) {
        var table = document.querySelector("table");
        var rows = Array.from(table.rows).slice(1); // ヘッダーを除く
    
        // 並び替えのロジック（文字列の場合）
        rows.sort(function(a, b) {
            var cellA = a.cells[columnIndex].textContent;
            var cellB = b.cells[columnIndex].textContent;
    
            if (columnIndex === 1) { // 期限列の並び替えの場合
                cellA = new Date(cellA);
                cellB = new Date(cellB);
            }
    
            if (cellA < cellB) return isAscending ? -1 : 1;
            if (cellA > cellB) return isAscending ? 1 : -1;
            return 0;
        });
    
        // 並び替えた行をテーブルに再追加
        rows.forEach(function(row) {
            table.appendChild(row);
        });
    
        // 並び順の反転
        isAscending = !isAscending;
    }
    
    function sortTableByDate(columnIndex) {
        var table = document.querySelector("table");
        var rows = Array.from(table.rows).slice(1); // ヘッダーを除く行のみ取得
        
        // 日付の並び替え
        rows.sort(function(a, b) {
            var cellA = new Date(a.cells[columnIndex].textContent);  // 例: "2025-05-04"
            var cellB = new Date(b.cells[columnIndex].textContent);
        
            // 昇順か降順かで並び替え
            return isAscending ? cellA - cellB : cellB - cellA;
        });
        
        // 並び替えた行を再配置
        rows.forEach(function(row) {
            table.appendChild(row);
        });
        
        // 並び順を反転（昇順・降順切り替え）
        isAscending = !isAscending;
    }
    </script>
</body>
</html>