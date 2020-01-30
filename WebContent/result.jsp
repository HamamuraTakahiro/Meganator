<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Result" %>
<%
	//スコープからリストを取得
	ArrayList<Result> resultList = (ArrayList<Result>) session.getAttribute(arg0);
%>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
	<script src="js/sample.js" type="text/javascript"></script>
	<link rel="stylesheet" href="WEB-INF/css/result.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/result.css">

</head>
<body>
	<div class="container">

		<!-- cardに値を格納してListのサイズ分だけ作成(引数名は仮で入れてます) -->

		<% for(int i = 0; i < resultList.size(); i++) { %>


			<!-- section(カード内の要素)タグの設定-->

				<div class="card">

			<!-- カードの上部に来る画像の設定 -->
				<div class="position_Box">
					<div class="center_img">
						<a href= <% resultList.get(i).getlinkUrl(); %>>
							<img src= <% resultList.get(i).getimagePath %>>
						</a>
					</div><!-- /.center_img -->
				</div><!-- /#position_Box -->

			<!-- カードのテキストの設定 -->
				<div class="card-content">
					<h1 class="card-title"><% resultList.get(i).getText(); %></h1>
				</div>

        </div>

<% } %>
</body>
</html>