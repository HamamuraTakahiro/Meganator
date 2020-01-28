<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="WEB-INF/css/reset.css">
    <link rel="stylesheet" href="WEB-INF/css/style.css">
    <link href="https://fonts.googleapis.com/earlyaccess/nikukyu.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
    <div class="mainBackGround">
        <div class="mainInner">
            <div class="mainContainer">
                <div class="mainContainer__title">
                    <!-- <h1>おしえて！ひ・まじ〜ん！</h1> -->
                    <span class="char1">お</span>
                    <span class="char2">し</span>
                    <span class="char3">え</span>
                    <span class="char4">て</span>
                    <span class="char5">？</span>
                    <span class="char6">ひ</span>
                    <span class="char7">・</span>
                    <span class="char8">ま</span>
                    <span class="char9">じ</span>
                    <span class="char10">〜</span>
                    <span class="char11">ん</span>
                    <span class="char12">！</span>
                </div>
                <div class="mainContainer__contents">
                    <div class="mainContainer__contents__discription">
                        <img src="image/majin.png" alt="魔人" class="imageMajin">
                        <p class="mainContainer__contents__discription--text">我が名は「ひ・まじ〜ん」。<br>
                            私が、主人の暇つぶしを探して見せよう！<br>
                            下の「スタートボタン」を押すがよい！
                        </p>
                        <form action="servlet/home" method="get">
                        <input type="button" value="スタート">
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</body>
</html>