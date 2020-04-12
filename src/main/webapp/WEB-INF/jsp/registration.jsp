<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Create an account</title>
  </head>
  <body>
  <style><%@include file="/WEB-INF/jsp/resources/css/bootstrap.min.css"%></style>
  <style><%@include file="/WEB-INF/jsp/resources/css/common.css"%></style>
    <div class="container">
        <form method="POST" action="/registration" class="form-signin">
            <h2 class="form-heading">Create your account</h2>
            <div class="form-group">
                 <input type="text" name="login" class="form-control" placeholder="Login" autofocus="true"></input>
            </div>
            <div class="form-group">
                 <input type="password" name="password" class="form-control" placeholder="Password"></input>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="/WEB-INF/jsp/resources//js/bootstrap.min.js"></script>
  </body>
</html>