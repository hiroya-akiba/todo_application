<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ログアウト</title>
  <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css" />
  <style>
    html, body {
      height: 100%;
      width: 100%;
    }
  </style>
</head>
<body>
    <div class="l-container">
        <div class="l-main">
            <div class="card">
            <%-- エラーメッセージ表示 --%>
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                String errorTitle = (String) request.getAttribute("errorTitle");
                if (errorMessage != null) {
            %>
                <h2 class="card-title"><%= errorTitle %></h2>
                <div class="card-content">
                    <p style="color:red;"><%= errorMessage %></p>
                </div>
                
            <%
                } else {
            %>
                <h2 class="card-title">ログアウトしました</h2>
            <%
                }
            %>
                <div class="card-content">
                    <p>もう一度ログインするには、以下のリンクをクリックしてください。</p>
                    <span><a href="<%=request.getContextPath()%>/login"> ログイン </a></span>
                    <span>/</span>
                    <span><a href="<%=request.getContextPath()%>/"> トップページへ </a></span>
                </div>
            </div>
        </div> <%-- l-mainのエンドタグ --%>
        <footer class="l-footer">
            &copy; 2025 hiroya akiba
        </footer>
    </div> <%-- l-containerのエンドタグ --%>
</body>
</html>