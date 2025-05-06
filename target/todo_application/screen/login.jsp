<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ログイン</title>
  <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css" />

</head>
<body class="login-layout">
    <!-- 左側のイラスト部分 -->
    <div class="login-illustration">
        <div class="login-illustration-content">
            <div class="login-illustration-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M12 2c5.514 0 10 4.486 10 10s-4.486 10-10 10-10-4.486-10-10 4.486-10 10-10zm0 18c4.411 0 8-3.589 8-8s-3.589-8-8-8-8 3.589-8 8 3.589 8 8 8zm-3.5-6.5l2.5-4.5 2.5 4.5h-5z" />
                </svg>
            </div>
            <h2>ToDo Task Manager へようこそ</h2>
            <p>あなたのタスク管理を簡単に。ログインして、今日のやることを整理しましょう。</p>
        </div>
    </div>

    <!-- 右側のログインフォーム部分 -->
    <div class="login-container">
        <div class="login-form-wrapper">
            <div class="login-header">
                <h1>ログイン</h1>
                <p>アカウント情報を入力してログインしてください</p>
            </div>

            <% Boolean isGrant = (Boolean)session.getAttribute("isGrant"); %>
            <% if (Boolean.FALSE.equals(isGrant)) { %>
                <div class="message error mt-2">
                    <p>ログイン情報が間違っています</p>
                </div>
            <% } %>
            
            <form action="<%= request.getContextPath()%>/login?parm=cert" method="POST" class="form">
                <label for="userId">ユーザーID</label>
                <input type="number" id="userId" name="userId" class="no-spin" required>

                <label for="password">パスワード</label>
                <input type="password" id="password" name="password" required>

                <button type="submit" class="btn">ログイン</button>
            </form>

            <div class="login-footer">
                <p>アカウントをお持ちでない方は</p>
                <a href="<%= request.getContextPath() %>/login?parm=new" class="btn btn-secondary mt-1">新規登録</a>
            </div>
        </div>
    <footer class="l-footer">
            &copy; 2025 hiroya akiba
    </footer>
    </div>
</body>
</html>