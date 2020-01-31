<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Result" %>
<%@ page import="config.Constant_text" %>
<%
	//スコープからリストを取得
	ArrayList<Result> resultList = (ArrayList<Result>)session.getAttribute(Constant_text.ADVICES);
%>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<link rel="stylesheet" href="WEB-INF/css/result.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/result.css">

</head>
<body>

	<div class="header">
		<img class="headerImg"  alt="" src="${pageContext.request.contextPath}/image/logo.png">
	</div>

	<div class="container">

		<!-- cardに値を格納してListのサイズ分だけ作成(引数名は仮で入れてます) -->
	<%if(resultList != null){ %>
		<% for(int i = 0; i < resultList.size(); i++) { %>

			<!-- section(カード内の要素)タグの設定-->
			<section class="card">

			<!-- カードの上部に来る画像の設定 -->
			<a href= https://www.google.com/?hl=ja>
				<img src=${pageContext.request.contextPath}/image/<% resultList.get(i).getImageName(); %>>
			</a>

			<!-- カードのテキストの設定 -->
			<div class="card-content">
				<h1 class="card-title"><% resultList.get(i).getText(); %></h1>
			</div>

		</section>

		<% } %>
	<%}else{ %>
	<!-- Attributeの取得に失敗したとき -->
		null
	<%} %>

	</div>

	<!-- 各種リンクの作成 -->
	<div class="Link">
		<div class="linkImg">
			<a href="">
				<img alt="" src=${pageContext.request.contextPath}/image/return.png>
			</a>
			<a href="https://www.google.com/?hl=ja"><img alt="" src=${pageContext.request.contextPath}/image/end.png></a>
		</div>
	</div>

</body>
</html>