<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css" />
  <link rel="stylesheet" href="<%= request.getContextPath()%>/css/toppage.css" />
  <style>
    html, body {
      height: 100%;
      width: 100%;
    }
  </style>
</head>
<body>
    <div class="hero-section">
        <h1 class="hero-title"> ToDo Task Manager</h1>
        <p class="hero-subtitle"> あなたの毎日を、もっとスマートに。</p>
        <span>
        	<a href="<%=request.getContextPath()%>/login"> ログイン </a>/ <a href="<%= request.getContextPath() %>/login?parm=new"> 会員登録 </a>
        </span>
        <br />
        <br />
        <footer>
            &copy; 2025 hiroya akiba
        </footer>
    </div>
</body>
</html>