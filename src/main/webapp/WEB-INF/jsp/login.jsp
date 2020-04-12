<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Login</title>
  </head>
	<body>
	<style><%@include file="/WEB-INF/jsp/resources/css/bootstrap.min.css"%></style>
      <style><%@include file="/WEB-INF/jsp/resources/css/common.css"%></style>
		 <div class="container">

		<form action="/login" method="post" class="form-signin">
		<h2 class="form-heading">Log in</h2>
			<div class="form-group">
				<label for="name">Enter your login: </label>
				<input type="text" class="form-control" name="login" id="name" required>
			</div>
			<div class="form-group">
				<label for="email">Enter your password: </label>
				<input type="password" class="form-control" name="password" id="password">
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
                        <h4 class="text-center"><a href="/registrationPage">Create an account</a></h4>
		</form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="/WEB-INF/jsp/resources//js/bootstrap.min.js"></script>
	</body>

</html>