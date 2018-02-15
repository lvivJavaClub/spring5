//Establish the WebSocket connection and set up event handlers
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/userList");
webSocket.onmessage = function (msg) { updateUserList(msg); };


//Update the list of connected users
function updateUserList(msg) {
    var data = JSON.parse(msg.data);
    insert("userlist", "<li>" + data.name + "</li>");
}

//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}
