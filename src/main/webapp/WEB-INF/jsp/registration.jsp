<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Create an account</title>
  </head>
  <body>
    <div class="container">
        <form method="POST" action="/registration" th:object="${loginWrapper}">
            <h2>Create your account</h2>
            <div>
                 <input type="text" th:field=*{login} placeholder="Login" autofocus="true"></input>
            </div>
            <div>
                 <input type="password" th:field=*{password} placeholder="Password"></input>
            </div>
            <div class="form-example">
           	    <input type="submit" value="Sign Up">
            </div>
        </form>
    </div>
  </body>
</html>