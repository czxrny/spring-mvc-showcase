let webSocket;
let webSocketTimer;

const wsUri = getRootUri() + "/SpringMvcShowcase/webSocketJSON";

var dataPointsParam = [];

for(let i = 0; i < 50; i++){
    dataPointsParam[i] = { label: i, y: 0 };
}

function getRootUri(){
    const wsUriHostPort = (location.protocol === "http:" ? "ws://" : "wss://");
    return wsUriHostPort + (document.location.hostname === "" ? "localhost" : document.location.hostname) + ":" + (document.location.port === "" ? "8443" : document.location.port);
}

function drawChart(dataPointsParam){
    var chart = new CanvasJS.Chart("chartContainer", {
        title: {
            text: "CanvasJS Chart"
        },
        axisY: {
            title: "Y Axis Description",
            maximum: 100
        },
        data: [
            {
                type: "column",
                dataPoints: dataPointsParam
            }
        ]
    });
    chart.render();
}

function init(){
    drawChart(dataPointsParam);
    initWebSocket();
}

function initWebSocket(){
    webSocket = new WebSocket(wsUri);
    webSocket.onopen = (evt) => {
        onOpen(evt);
    };
    webSocket.onmessage = (evt) => {
        onMessage(evt);
    };
    webSocket.onerror = (evt) => {
        onError(evt);
    };
    webSocket.onclose = (evt) => {
        onClose(evt);
    };
}

function onOpen(evt){
    writeToScreen("CONNECTED");
    // Start sending messages every second only after connection is open
    webSocketTimer = setInterval(() => doSend(), 1000);
}

function onMessage(evt){
    writeToScreen("RECEIVED: " + evt.data);
    var dataArrayJSON = JSON.parse(evt.data);
    for(let i = 0; i < dataArrayJSON.length; i++){
        dataPointsParam[i] = { label: i, y: dataArrayJSON[i] };
    }
    drawChart(dataPointsParam);
}

function onError(evt){
    writeToScreen("<span style='color: red;'>ERROR</span> " + evt.data);
}

function onClose(evt){
    writeToScreen("DISCONNECTED");
    // Stop the timer because connection is closed
    if(webSocketTimer){
        clearInterval(webSocketTimer);
        webSocketTimer = null;
    }
}

function doSend(message){
    if (!webSocket || webSocket.readyState !== WebSocket.OPEN) {
        writeToScreen("WebSocket is not open.");
        return;
    }
    writeToScreen("SENT: " + (message ? message : "x"));
    if(message) webSocket.send(message);
    else webSocket.send("x");
}

function writeToScreen(message){
    const messageField = document.getElementById("messageStatusID");
    if(messageField) {
        messageField.value = message;
    }
}

window.addEventListener("load", init, false);
