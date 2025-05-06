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
                    
                                <form action="<%= request.getContextPath()%>/todo_list?parm=new" method="POST" class="form">
                                    <input type="hidden" id="userId" name="userId" value="<%= user.getId() %>">
                                    <button type="submit" class="btn">新規タスク追加</button>
                                </form>
                           
                                <div id="action-buttons" style="display: none; text-align: left; margin-bottom: 8px;">
                                            <!-- 編集 -->
                                            <button title="編集" class="action-icon" onclick="submitChecked('edit')" >
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                                                    <path d="M12.146.854a.5.5 0 0 1 .708 0l2.292 2.292a.5.5 0 0 1 0 .708l-10 10A.5.5 0 0 1 4.5 14H2a1 1 0 0 1-1-1v-2.5a.5.5 0 0 1 .146-.354l10-10zM11.207 2L13 3.793 14.793 2 13 0.207 11.207 2zM1.5 13.5V12h1.5l9-9L10.5 4.5l-9 9H1.5z"/>
                                                </svg>
                                            </button>
                                            
                                            <!-- 削除 -->
                                            <button title="削除" class="action-icon delete-icon" onclick="confirm('本当に削除しますか？'); submitChecked('delete')">
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" stroke="currentColor" stroke-width="1.5" viewBox="0 0 24 24">
                                                    <g stroke-linecap="round" stroke-linejoin="round">
                                                        <path d="M3 6h18M9 6V4h6v2"/>
                                                        <rect x="5" y="6" width="14" height="16" rx="2"/>
                                                        <line x1="9" y1="10" x2="9" y2="18"/>
                                                        <line x1="12" y1="10" x2="12" y2="18"/>
                                                        <line x1="15" y1="10" x2="15" y2="18"/>
                                                    </g>
                                                </svg>
                                            </button>
                                            
                                            <!-- ステータス一括変更 -->
                                            <button title="ステータス変更" class="action-icon"  onclick="submitChecked('status')">
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                                                    <path fill-rule="evenodd" d="M2 12.5a.5.5 0 0 1 .5-.5H13a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5H13a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5H13a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5z"/>
                                                </svg>
                                            </button>
                               </div>
                    </table>
                    <%
                        List<TodoListDto> todoList = (List<TodoListDto>) request.getAttribute("todoList");
                        if (todoList != null && !todoList.isEmpty()) {
                    %>
                    <table border="1">
                        <tr>
                            <th><input type="checkbox" id="check-all" onclick="toggleAllByState()"/></th>
                            <th>ID <button onclick="sortTableByString(0)" class="btn">⇅</button></th>
                            <th>内容<button onclick="sortTableByString(1)" class="btn">⇅</button></th>
                            <th>期限日<button onclick="sortTableByDate(2)" class="btn">⇅</button></th>
                            <th>状態<button onclick="sortTableByString(3)" class="btn">⇅</button></th>
                            <th>操作</th>
                        </tr>
                        <%
                            for (TodoListDto todo : todoList) {
                        %>
                        <tr>
                            <td><input type="checkbox" class="row-check" value="<%= todo.getId() %>" onclick="toggleActionButtons()"/></td>
                            <td><%= todo.getId() %></td>
                            <td><%= todo.getContent() %></td>
                            <td><%= todo.getDueDate() %></td>
                            <td class="todo-status">
                                <span class="todo-status-text"><%= todo.getStatus() %></span>
                                <button class="todo-status-btn" onclick="toggleStatus(this)">⮔</button>
                            </td>
                            <td>
                                
                            </td>
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

    /**
    * 文字列をソートする
    */
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

    /**
    * 日付をソートする
    */
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

    /**
    * ステータスを反転させる
    */
    function toggleStatus(button) {
         const statusText = button.closest("td").querySelector(".todo-status-text");
         if (statusText.innerText === "未完了") {
           statusText.innerText = "完了";
         } else {
           statusText.innerText = "未完了";
         }
     }

    /**
    * すべてのチェックボックスにチェックを入れる
    */
    function toggleAllByState() {
        const checkboxes = document.querySelectorAll(".row-check");
        const allChecked = Array.from(checkboxes).every(cb => cb.checked);

        // すべてチェック済み → 全解除、それ以外 → 全チェック
        checkboxes.forEach(cb => cb.checked = !allChecked);

    }

    /**
    * アイコン表示
    */
    function toggleActionButtons() {
        const anyChecked = Array.from(document.querySelectorAll('.row-check'))
                           .some(cb => cb.checked);
        document.getElementById('action-buttons').style.display = anyChecked ? 'block' : 'none';
    }

    /**
    * アイコン押下時のSubmit
    */
    function submitChecked(parm) {
         console.log(parm);
         var checkedIds = Array.prototype.filter.call(
           document.querySelectorAll('.row-check'),
           function(cb) { return cb.checked; }
         ).map(function(cb) { return cb.value; });
         console.log(checkedIds);

         if (checkedIds.length === 0) {
           alert("対象を選択してください");
           return;
         }

         var paramStr = "parm=" + parm + "&ids=" + checkedIds.join(",");
         console.log(paramStr);

         if (parm === "edit") {
             window.open("/todo_list?" + paramStr, "_blank", "width=600,height=400,resizable=yes");
         } else if (parm === "delete" || parm === "status") {
             var xhr = new XMLHttpRequest();
             xhr.open("GET", "/todo_list?" + paramStr, true);
             xhr.onreadystatechange = function() {
                 if (xhr.readyState === 4 && xhr.status === 200) {
                     if (parm === "delete") {
                         checked.forEach(function(cb) {
                         var row = cb.closest("tr");
                         if (row) row.remove();
                         });
                     } else if (parm === "status") {
                         checked.forEach(function(cb) {
                         var row = cb.closest("tr");
                         var toggleBtn = row?.querySelector("button[onclick^='toggleStatus']");
                         if (toggleBtn) {
                             toggleStatus(toggleBtn);
                           }
                        });
                     }
                     toggleActionButtons();
                 }
             };
             xhr.send();
         }
     }
    </script>
</body>
</html>