function makeFbLogin() {
    var un = document.getElementById("email").value;
    var pw = document.getElementById("pwd").value;
    var url = "http://localhost:5484/microserviceone-1.0-SNAPSHOT/fb/" + un + "/" + pw;
    /*
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", url, false);
        //xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send();
        var response = JSON.parse(xhttp.responseText);
    */


    //alert(response)

    $.ajax({
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
    var url = "http://localhost:5484/microserviceone-1.0-SNAPSHOT/fb";
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: url,
        type: 'POST',
        data: JSON.stringify({"userId": id, "userName": name, "email": email, "password": pwd, "screenName": scrName}),
        success: function success(data) {
            console.log(data.toString())
            sessionStorage.setItem("userId",id);
            window.location.href = "login.html"
        },
        error: function errorTest(data) {
            console.log("herere" + data.toString())
        }
    })

}

function insertItem(){
    var id = sessionStorage.getItem("userId");
    var item = document.getElementById("item").value;
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: "http://localhost:5000/item",
        type: 'POST',
        data: JSON.stringify({"userId": id, "item": item}),
        success: function success(data) {
            fetchAllItems(id)
        }

    })
}

function fetchAllItems(userId) {
    $.ajax({
        url: "http://localhost:5000/item/"+id,
        type: 'GET',
        success: function success(data) {
            $("#myarea").empty()
            $("#myarea").html(data)
        }
    });

}

function loadFooter(){
    $.ajax({
        url: "http://localhost:8086/",
        type: 'GET',
        success: function success(data) {
            console.log(data)
            $("#footer").html("<footer>"+JSON.stringify(data)+"</footer>")
        }
    })
}
