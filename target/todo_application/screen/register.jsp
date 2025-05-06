<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="jp.kouto.fuyuki.akiba.todo_application.dto.UsersDto" %>
<%@ page import="java.util.Calendar" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ユーザー登録</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
</head>
<body class="login-layout">
    <!-- 左側のイラスト部分 -->
    <div class="login-illustration">
        <div class="login-illustration-content">
            <div class="login-illustration-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm7 14h-5v5h-4v-5h-5v-4h5v-5h4v5h5v4z"/>
                </svg>
            </div>
            <h2>新しいアカウントを作成</h2>
            <p>ToDoリストアプリケーションで、あなたのタスクを効率的に管理しましょう。</p>
        </div>
    </div>

    <!-- 右側の登録フォーム部分 -->
    <div class="login-container">
        <div class="login-form-wrapper">
            <div class="login-header">
                <h1>ユーザー登録</h1>
                <p>アカウント情報を入力して登録してください</p>
            </div>

            <%
                Boolean completeFlg = (Boolean) request.getAttribute("completeFlg");
                if (completeFlg==null || Boolean.FALSE.equals(completeFlg)) {
            %>

            <!-- ユーザー登録フォーム -->
            <form action="<%= request.getContextPath()%>/login?parm=temporary" method="POST" class="form form-register">
                <label for="username">ユーザー名:</label>
                <input type="text" id="username" name="username" value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>" required>

                <label for="email">メールアドレス:</label>
                <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required>

                <label for="password">パスワード:</label>
                <input type="password" id="password" name="password" required>

                <label for="confirmPassword">パスワード確認:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                
                <fieldset>
                  <label class="kiyaku-label">
                    <input type="checkbox" id="kiyaku" name="kiyaku" required>
                    <span>
                    <a href="/kiyaku.jsp" target="_blank" rel="noopener">個人情報の取り扱いについて</a>に同意します。
                    </span>
                  </label>
                </fieldset>
                <br/>
                <button type="submit" class="btn">同意してメールアドレスを送信</button>
            </form>

            <!-- エラーメッセージ表示 -->
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="message error mt-2">
                    <p><%= request.getAttribute("errorMessage") %></p>
                </div>
            <% } %>

            <div class="login-footer">
                <p>すでにアカウントをお持ちの方は</p>
                <a href="<%= request.getContextPath()%>/login" class="btn btn-secondary mt-1">ログインページへ</a>
            </div>

            <%
                } else {
                    System.out.println(request.getAttribute("userId").toString());
                    Long userId = Long.parseLong(request.getAttribute("userId").toString());
                    String username = (String) request.getAttribute("username");
            %>
                <div class="card">
                    <div class="card-content">
                        <div class="message success">
                            <p>登録完了しました。<br/>
                                <%=username %>さんのIDは<%=userId %>になります。<br/>
                                下記ページよりログインをお願いします。
                            </p>
                        </div>
                        <div class="text-center mt-3">
                            <form action="<%= request.getContextPath()%>/login" method="POST">
                                <input type="hidden" id="userId" name="userId" value="<%= userId %>">
                                <input type="hidden" id="isGrant" name="isGrant" value="true">
                                <button type="submit" class="btn">ログインページへ</button>
                            </form>
                        </div>
                    </div>
                </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>