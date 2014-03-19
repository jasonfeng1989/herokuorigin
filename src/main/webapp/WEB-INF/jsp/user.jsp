<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta charset="utf-8">
    <title>Spring MVC and Hibernate Template</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

    <!--
      IMPORTANT:
      This is Heroku specific styling. Remove to customize.
    -->
    <link href="http://heroku.github.com/template-app-bootstrap/heroku.css" rel="stylesheet">
    <!-- /// -->

</head>

<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a href="/" class="brand">Spring MVC and Hibernate Template</a>
            <a href="/" class="brand" id="heroku">by <strong>heroku</strong></a>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="span8 offset2">
            <div class="page-header">
                <h1>Contacts</h1>
                <h1>${user.firstName}</h1>
            </div>

            <c:if  test="${!empty usersList}">
                <h3>User</h3>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>userId</th>                     
                        <th>FirstName</th>  
                        <th>LastName</th>  
                        <th>Email</th>
                        <th>&nbsp;</th>
                        
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${usersList}" var="user">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.FirstName}</td>
                            <td>${user.LastName}</td>
							<td>${user.Email}</td>
                       
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
