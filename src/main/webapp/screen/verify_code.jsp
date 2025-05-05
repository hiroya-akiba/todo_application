<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ja">
<head>
	<meta charset="UTF-8">
    <title>メールアドレスへ認証コードを送信しました。</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css" />
</head>
<body>
    <h2>メールを確認して届いた認証コード6桁をを入力してください</h2>

    <%-- エラーメッセージ表示 --%>
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>
    

    <form action="<%= request.getContextPath() %>/login?parm=register" method="POST" class="form" style="width: 300px">
        認証コード（数字6文字）：<br>
        <input type="text" name="authCode" maxlength="6" pattern="[0-9]{6}" required> <br><br>
        <input type="hidden" id="receipt_code" name="receipt_code" value="<%= request.getAttribute("receipt_code") %>">
        <button type="submit">認証する</button>
    </form>
</body>
</html>