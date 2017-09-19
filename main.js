function makeFbLogin() {
    var un = document.getElementById("email").value;
    var pw = document.getElementById("pwd").value;
    sessionStorage.setItem("userId", un);
    var url = "http://localhost:8084/apigateway-1/fb/" + un + "/" + pw;
    /*
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", url, false);
        //xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send();
        var response = JSON.parse(xhttp.responseText);
    */


    //alert(response)

    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: url,
        type: 'GET',
        success: function success(data) {
            console.log(data)
            window.location.href = "todoList.html"
        }
    });

}

function registerUser() {
    var id = document.getElementById("id").value;
    var name = document.getElementById("name").value;
    var scrName = document.getElementById("screenName").value;
    var email = document.getElementById("email").value;
    var pwd = document.getElementById("pwd").value;
    var url = "http://127.0.0.1:8084/apigateway-1/fb";
    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: url,
        crossDomain: true,
        type: 'POST',
        data: JSON.stringify({"userId": id, "userName": name, "email": email, "password": pwd, "screenName": scrName}),
        success: function success(data) {
            console.log(data.toString())
            sessionStorage.setItem("userId", id);
            window.location.href = "login.html"
        },
        error: function errorTest(data) {
            console.log("herere" + data.toSt+ring())
        }
    })

}

function insertItem() {
    var id = sessionStorage.getItem("userId");
    var item = document.getElementById("item").value;
    $.ajax({
        headers: {
            'Content-Type': 'text/plain'
        },
        url: "http://127.0.0.1:8084/apigateway-1/fb/item",
        type: 'POST',
        data: JSON.stringify({"userId": id, "item": item}),
        success: function success(data) {
            console.log("Here the data is" + data)
            fetchAllItems(id)
        }

    })
}

function fetchAllItems(userId) {
    $.ajax({
        url: "http://127.0.0.1:8084/apigateway-1/fb/item/" + userId,
        type: 'GET',
        success: function success(data) {
            listItems = JSON.parse(data)
            console.log(data)
            console.log(listItems)
            $("#myarea").html(data)
        }
    });

}

function loadFooter() {
    $.ajax({
        url: "http://127.0.0.1:8086/",
        type: 'GET',
        success: function success(data) {
            console.log(data)
            $("#footer").html("<footer>" + JSON.stringify(data) + "</footer>")
        }
    });
    fetchAllItems(sessionStorage.getItem("userId"));
}

function deleteItem() {
    var itemId = document.getElementById("itemId").value;
    $.ajax({
        url: "http://127.0.0.1:8084/apigateway-1/fb/item/" + itemId,
        type: 'DELETE',
        success: function success(data) {
            fetchAllItems(sessionStorage.getItem("userId"))
        }
    });


}
