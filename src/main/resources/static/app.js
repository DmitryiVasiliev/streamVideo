var stompClient = null;


function startStream() {
    var socket = new SockJS('/websocket');
    var img = document.querySelector('img');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        sendRequest();
        stompClient.subscribe('/topic/streamClient', function (response) {
            img.src = 'data:image/png;base64,' + response.body;
            sendRequest();
        });
    });
}

function sendRequest() {
    stompClient.send("/app/request", {}, JSON.stringify(''));
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#start").click(function () {
        startStream();
    });
});