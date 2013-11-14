<%@ include file="include.jsp"%>

<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
  <title>Trekk</title>
  <link rel="shortcut icon" type="image/x-icon" href="${resourceUrl}/img/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${resourceUrl}/css/bootstrap.min.css" />
</head>

<body ng-app="trekk">

<div class="container">
  <h1>Trekk</h1>
  <hr>
  <div ng-view></div>
</div>

<script type="text/ng-template" id="customerList.html">
  <h3>Customer List</h3>
  <hr>
  <button class="btn btn-primary" ng-click="create()">Create Customer</button><br><br>
  <table class="table table-striped table-bordered taple-condensed">
    <thead>
      <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Age</th>
        <th>Gender</th>
        <th>Options</th>
      </tr>
    </thead>
    <tr ng-repeat="c in customers">
      <td>{{ c.firstName }}</td>
      <td>{{ c.lastName }}</td>
      <td>{{ c.age }}</td>
      <td>{{ c.gender }}</td>
      <td>
        <button class="btn btn-default btn-xs" ng-click="edit(c.id)">Edit</button>
        <button class="btn btn-default btn-xs" ng-click="remove(c.id)">Delete</button>
        <button class="btn btn-default btn-xs" ng-click="navigation(c.id)">Navigation</button>
      </td>
    </tr>
  </table>
</script>

<script type="text/ng-template" id="customerDetail.html">
  <h3>{{ action }} Customer</h3>
  <hr>
  <div class="alert alert-danger" ng-show="isError">An error occured: {{ errorMsg }}</div>
  <form class="form-horizontal" role="form" ng-submit="save(customer)">
    <div class="form-group" ng-show="customer.id">
      <label class="col-sm-2 control-label">Customer Id</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" value="{{ customer.id }}" disabled>
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-2 control-label">First Name</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" ng-model="customer.firstName" autofocus>
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-2 control-label">Last Name</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" ng-model="customer.lastName">
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-2 control-label">Birthday</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" ng-model="customer.birthday">
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-2 control-label">Gender</label>
      <div class="col-sm-10">
        <select class="form-control" ng-model="customer.gender">
          <option value="w">Female</option>
          <option value="m">Male</option>
        </select>
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-2 control-label">Last Contact</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" ng-model="customer.lastContact">
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-2 control-label">Lifetime Value</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" ng-model="customer.lifetimeValue">
      </div>
    </div>

    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-primary">{{ action }} Customer</button>
        <button class="btn btn-default" ng-click="home()">Cancel</button>
      </div>
    </div>
  </form>
</script>

<script type="text/ng-template" id="navigationList.html">
  <h3>Navigation List</h3>
  <hr>
  <table class="table table-striped table-bordered taple-condensed">
    <thead>
      <tr>
        <th>Page</th>
        <th>Timestap</th>
      </tr>
    </thead>
    <tr ng-repeat="n in navigations">
      <td>{{ n.page }}</td>
      <td>{{ n.stamp }}</td>
    </tr>
  </table>
  <button class="btn btn-default" ng-click="home()">Back to Customers</button>
</script>

<c:choose>
<c:when test="${envbase eq 'prod'}">
<script src="${resourceUrl}/js/app.min.js" async></script>
</c:when>
<c:otherwise>

<script src="${resourceUrl}/js/angular.min.js"></script>
<script src="${resourceUrl}/js/angular-route.min.js"></script>
<script src="${resourceUrl}/js/angular-resource.min.js"></script>
<script src="${resourceUrl}/js/app.js"></script>

</c:otherwise>
</c:choose>

</body>
</html>
