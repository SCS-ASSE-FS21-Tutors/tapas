var stompClient = null;

function setConnected(connected) {
    document.getElementById('connected').innerText = "Connected";
}

function connect() {
    var socket = new SockJS('/taskListWs');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/taskList', function(messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showMessageOutput(messageOutput) {

    tasksData = messageOutput.map(function (task){
            return [task.taskId,task.taskName,task.taskType,task.inputData,task.outputData,task.taskStatus];
    })

    let datatable = $('#myTable').DataTable();
    datatable.clear();
    datatable.rows.add(tasksData);
    datatable.draw();


}

connect()

