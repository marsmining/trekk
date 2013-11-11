<%@ include file="include.jsp"%>

<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
  <title>Trekk</title>
  <link rel="shortcut icon" type="image/x-icon" href="${resourceUrl}/img/favicon.ico" />

<c:choose>
<c:when test="${envbase eq 'prod'}">
  <link rel="stylesheet" type="text/css" href="${resourceUrl}/css/main.min.css" />
</c:when>
<c:otherwise>
  <link rel="stylesheet" type="text/css" href="${resourceUrl}/css/bootstrap.min.css" />
</c:otherwise>
</c:choose>

</head>

<body>

<div class="container">
  <h1>Trekk</h1>
</div>

<c:choose>
<c:when test="${envbase eq 'prod'}">
<script src="${resourceUrl}/js/main.min.js" async></script>
</c:when>
<c:otherwise>

<script src="${resourceUrl}/js/angular.min.js"></script>

</c:otherwise>
</c:choose>

</body>
</html>
