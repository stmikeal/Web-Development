let username = new Vue({
    el: '#usernameInput',
    data: {
        text: "",
        mainClass: 'pointInput',
        errorClass: 'divInputError',
        hasError: false
    },
    classObject: {
        'background-color: black': true
    }
});

let password = new Vue({
    el: '#passwordInput',
    data: {
        text: "",
        mainClass: 'pointInput',
        errorClass: 'divInputError',
        hasError: false
    },
    classObject: {
        divInputError: true
    }
});

let buttons = new Vue({
    el: '#loginFormButtons',
    methods: {
        register: register,
        login: login
    }
});









function register() {
    $.ajax({
        type: "POST",
        url: "register",
        data: {
            "command":"register",
            "username":username.text,
            "password":password.text
        },
        success: onAnswer,
        dataType: "json"
    });
}

function login() {
    $.ajax({
        type: "POST",
        url: "login",
        data: {
            "command":"login",
            "username":username.text,
            "password":password.text
        },
        success: onAnswer,
        dataType: "json"
    });
}

function onAnswer(res) {
    let JSON_string = JSON.stringify(res);
    console.log(JSON_string);
    let data = JSON.parse(JSON_string);
    data.token = data.hasOwnProperty('token') ? data.token : "";
    data.username = data.hasOwnProperty('username') ? data.username : "";
    data.command = data.hasOwnProperty('command') ? data.command : "";
    console.log(data.token);
    if (data.token.length > 0) {
        localStorage.setItem(422, data.token);
        localStorage.setItem(423, data.username);
        console.log("HI");
        document.location.href = "index.html";
    } else {
        setRedFields(data.command);
    }
}

function setRedFields(command) {
    username.hasError = true;
    password.hasError = true;
}

function setBlackFields() {

}

function onStart() {
    console.log("started");
    $.ajax({
        type: "POST",
        url: "time",
        success: onAnswerTime,
        dataType: "json"
    });
}

function onAnswerTime(res){
    console.log("persistence is started")
}

onStart();
