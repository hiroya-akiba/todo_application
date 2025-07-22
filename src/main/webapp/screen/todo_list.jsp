<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.stream.Collectors" %>
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
                    <%
                        List<TodoListDto> todoList = (List<TodoListDto>) request.getAttribute("todoList");
                    %>
                        <form action="<%= request.getContextPath()%>/todo_list?parm=new" method="POST" class="form">
                            <input type="hidden" id="userId" name="userId" value="<%= user.getId() %>">
                            <button type="submit" class="btn">新規タスク追加</button>
                        </form>
                        
                        <div id="action-buttons" style="text-align: left; margin-bottom: 8px;">
                            <!-- 編集 -->
                            <button title="編集" class="action-icon" onclick="submitChecked('edit')" >
                                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                                    <path d="M12.146.854a.5.5 0 0 1 .708 0l2.292 2.292a.5.5 0 0 1 0 .708l-10 10A.5.5 0 0 1 4.5 14H2a1 1 0 0 1-1-1v-2.5a.5.5 0 0 1 .146-.354l10-10zM11.207 2L13 3.793 14.793 2 13 0.207 11.207 2zM1.5 13.5V12h1.5l9-9L10.5 4.5l-9 9H1.5z"/>
                                </svg>
                            </button>
                            
                            <!-- 削除 -->
                            <button title="削除" class="action-icon delete-icon" onclick="submitChecked('delete')">
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
                            
                            <!-- ステータス一括更新 -->
                            <%
                                String idsArgs = todoList.stream()
                                    .map(s -> "'" + s.getId() + "'")
                                    .collect(Collectors.joining(", "));
                            %>
                            <button title="ステータス更新" class="action-icon"  onclick="submitChecked('status', <%=idsArgs%>)">
                                <img src="<%= request.getContextPath()%>/svg/update.svg" width="14px" height="14px">
                            </button>
                        </div>
                    </table>
                    <%
                        if (todoList != null && !todoList.isEmpty()) {
                    %>
                    <table id="todo-table" border="1">
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="check-all" onclick="toggleAllByState()"/></th>
                                <%--<th>ID <button type="button" onclick="sortTableBy(1)" class="btn">⇅</button></th>--%>
                                <th>内容<button type="button" onclick="sortTableBy(1)" class="btn">⇅</button></th>
                                <th>期限日<button type="button" onclick="sortTableBy(2)" class="btn">⇅</button></th>
                                <th>状態<button type="button" onclick="sortTableBy(3)" class="btn">⇅</button></th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            for (TodoListDto todo : todoList) {
                        %>
                        <tr id="row-<%= todo.getId() %>">
                          <td><input type="checkbox" class="row-check" value="<%= todo.getId() %>"/></td>
                        
                          <td id="content-<%= todo.getId() %>"><%= todo.getContent() %></td>
                          <td id="dueDate-<%= todo.getId() %>"><%= todo.getDueDate() %></td>
                        
                          <td class="todo-status">
                            <span class="todo-status-text" id="status-<%= todo.getId() %>"><%= todo.getStatus() %></span>
                            <button class="todo-status-btn"
                                    onclick="submitChecked('status', '<%= todo.getId() %>')"
                                    style="background: none; border: none; padding: 0;">
                              <img src="<%= request.getContextPath()%>/svg/update.svg" width="14px" height="14px">
                            </button>
                          </td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
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
    const sortState = {};

    function sortTableBy(colIndex) {
      const tbody = document.querySelector("#todo-table tbody");
      const rows = Array.from(tbody.querySelectorAll("tr"));
      sortState[colIndex] = !sortState[colIndex];
      const isAsc = sortState[colIndex];

      rows.sort((a, b) => {
        let valA = a.cells[colIndex].innerText.trim();
        let valB = b.cells[colIndex].innerText.trim();

        const isDateA = /^\d{4}-\d{2}-\d{2}$/.test(valA);
        const isDateB = /^\d{4}-\d{2}-\d{2}$/.test(valB);

        // ✅ 日付比較
        if (isDateA && isDateB) {
          const dateA = new Date(valA);
          const dateB = new Date(valB);
          return isAsc ? dateA - dateB : dateB - dateA;
        }

        // ✅ 片方だけnull/空欄ならそちらを後ろに回す
        if (!valA) return isAsc ? 1 : -1;
        if (!valB) return isAsc ? -1 : 1;

        // ✅ 数値比較
        const numA = parseInt(valA, 10);
        const numB = parseInt(valB, 10);
        if (!isNaN(numA) && !isNaN(numB)) {
          return isAsc ? numA - numB : numB - numA;
        }

        // ✅ 文字列比較
        return isAsc
          ? valA.localeCompare(valB, "ja")
          : valB.localeCompare(valA, "ja");
      });

      tbody.innerHTML = "";
      rows.forEach(row => tbody.appendChild(row));
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
    * ・編集ボタン: 編集ページへ遷移してチェック付けたタスクを表示
    * ・削除ボタン：削除して表からレコード消す
    * ・ステータス確認ボタン：現在のタスクの状態に更新する
    */
    function submitChecked(parm, ...updateTaskIds) {
         console.log(parm);
         var paramStr = "";
         if(updateTaskIds.length === 0){
             var checkboxes = document.querySelectorAll('.row-check');
             var checked = Array.prototype.filter.call(checkboxes, function(cb) {
               return cb.checked;
             });
             console.log(checked);
             var checkedIds = checked.map(function(cb) { return cb.value; });
             if (checkedIds.length === 0) {
               alert("対象を選択してください");
               return;
             }
             if (parm === "delete") {
                 if(!confirm('本当に削除しますか？')) return;
             }
             paramStr = "parm=" + parm + "&ids=" + checkedIds.join(",");
         } else {
             paramStr = "parm=" + parm + "&ids=" + updateTaskIds.join(",");
         }
         console.log(paramStr);

         // 編集画面遷移
         if (parm === "edit") {
           window.open("/todo_application/todo_list?" + paramStr, "_blank", "width=600,height=400,resizable=yes");

         // 削除 or ステータス更新
         } else if (parm === "delete" || parm === "status") {
           const xhr = new XMLHttpRequest();
           xhr.open("GET", "/todo_application/todo_list?" + paramStr, true);

           xhr.onreadystatechange = function () {
             if (xhr.readyState === 4 && xhr.status === 200) {
               if (parm === "delete") {
                   const idsToDelete = updateTaskIds.length === 0 ? checkedIds : updateTaskIds;
                   idsToDelete.forEach(function (id) {
                     const row = document.getElementById("row-" + id);
                     console.log("target row:", row);
                     if (row) row.remove();
                 });
               } else if (parm === "status") {
                 // ステータス更新：レスポンスを受け取ってDOM反映
                 try {
                   const response = JSON.parse(xhr.responseText);
                   
                   if (response.status === 200 && response.result === "OK") {
                     const items = response.data.items;
                     
                     items.forEach(function (task) {
                       const id = task.id;
                       const contentTd = document.getElementById("content-" + id);
                       const dueDateTd = document.getElementById("dueDate-" + id);
                       const statusSpan = document.getElementById("status-" + id);
                       if (contentTd) contentTd.innerText = task.content;
                       if (dueDateTd) dueDateTd.innerText = task.dueDate;
                       if (statusSpan) statusSpan.innerText = task.status;
                     });
                   } else {
                     console.warn("APIレスポンスが失敗:", response);
                   }
                 } catch (e) {
                   console.error("JSONパース失敗:", e);
                   console.debug("生レスポンス:", xhr.responseText);
                 }
               }
             }
           };

           xhr.send();
         }
     }
    </script>
</body>
</html>