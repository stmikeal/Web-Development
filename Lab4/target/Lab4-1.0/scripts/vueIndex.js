let table = new Vue({
    el: '#historyTable',
    data: {
        message: "Hello",
        points: [
            {
                x: "Тут пока пусто:(",
                y: "",
                r: "",
                time: "",
                script: "",
                hit: ""
            }
        ]
    }
});



let xInput = new Vue({
    el: '#xTextInput',
    data: {
        text: "",
        min: "-3",
        max: "5"
    },
    watch: {
        text: onlyDigits
    }
});

let yInput = new Vue({
    el: '#yTextInput',
    data: {
        text: "",
        min: "-5",
        max: "3"
    },
    watch: {
        text: onlyDigits
    }
});

let rInput = new Vue({
    el: '#rTextInput',
    data: {
        text: "",
        min: "-3",
        max: "5"
    },
    watch: {
        text: onlyDigits
    }
});

let buttons = new Vue({
    el: '#controlFormButtons',
    methods: {
        addPoint: addPoint,
        clearPoint: clearPoint
    }
});

let logout = new Vue({
    el: '#backButton',
    methods: {
        addPoint: goBack
    }
});

let name = new Vue({
    el: '#nameField',
    data: {
        username: localStorage.getItem(423)
    }
});

let graph = new Vue({
    el: "#graph",
    methods: {
        onClick: onGraphClick
    }
});




console.log("started");
function addPoint(){
    console.log("send");
    $.ajax({
        type: "POST",
        url: "point",
        data: {
            "command":"point",
            "x":xInput.text,
            "y":yInput.text,
            "r":rInput.text,
            "token":localStorage.getItem(422),
            "username":localStorage.getItem(423)
        },
        success: onAnswerPoint,
        dataType: "json"
    });
}

function getPoint(){
    console.log("send");
    $.ajax({
        type: "POST",
        url: "point",
        data: {
            "command":"get",
            "x":xInput.text,
            "y":yInput.text,
            "r":rInput.text,
            "token":localStorage.getItem(422),
            "username":localStorage.getItem(423)
        },
        success: onAnswerPoint,
        dataType: "json"
    });
}

getPoint();

function clearPoint(){
    console.log("send");
    $.ajax({
        type: "POST",
        url: "clear",
        data: {
            "command":"clear",
            "token":localStorage.getItem(422),
            "username":localStorage.getItem(423)
        },
        success: onAnswerClear,
        dataType: "json"
    });
}

if(!localStorage.getItem(423)&&!localStorage.getItem(422)){
    document.location.href = "greeting.html";
}

function goBack(){
    $.ajax({
        type: "POST",
        url: "point",
        data: {
            "command":"point",
            "x":xInput.text,
            "y":yInput.text,
            "r":rInput.text,
            "token":localStorage.getItem(422),
            "username":localStorage.getItem(423)
        },
        success: onAnswerBack,
        dataType: "json"
    });
}

function onAnswerBack(res){
    let JSON_string = JSON.stringify(res);
    let data = JSON.parse(JSON_string);
    data.correct = data.hasOwnProperty('correct') ? data.correct : "0";
    if (data.correct.length > 0) {
        localStorage.removeItem(422);
        localStorage.removeItem(423);
        document.location.href = "greeting.html";
    }
}

function onAnswerPoint(res){
    let JSON_string = JSON.stringify(res);
    let data = JSON.parse(JSON_string);
    let nullField = 0;
    data.list = data.hasOwnProperty('list') ? data.list : [];
    let newPoints = [];
    data.list.forEach(function(item, index, array){
        item.x = item.hasOwnProperty('x') ? item.x : nullField;
        item.y = item.hasOwnProperty('y') ? item.y : nullField;
        item.r = item.hasOwnProperty('r') ? item.r : nullField;
        item.time = item.hasOwnProperty('time') ? item.time : nullField;
        item.script = item.hasOwnProperty('script') ? item.script : nullField;
        item.hit = item.hasOwnProperty('hit') ? item.hit : nullField;
        console.log(JSON_string);
        newPoints.push({
            x: item.x,
            y: item.y,
            r: item.r,
            time: item.time,
            script: item.script,
            hit: item.hit
        });
    })

    table.points = newPoints;
    drawSample();
}

function onAnswerClear(res){
    let JSON_string = JSON.stringify(res);
    let data = JSON.parse(JSON_string);
    data.correct = data.hasOwnProperty('correct') ? data.correct : "";
    if (data.correct.length > 0) {
        table.points = [];
    }
    drawSample();
}




function onlyDigits(min, max) {
    drawSample();
    let separator = ","
    let replaced = new RegExp('[^\\d\\'+separator+'\\-]', "g");
    let regex = new RegExp('\\'+separator, "g");
    this.text = this.text.replace(replaced, "");

    let minValue = parseFloat(this.min);
    let maxValue = parseFloat(this.max);
    let val = this.text.replace(new RegExp(separator, "g"), ".");
    if (minValue <= maxValue) {
        if (this.text[0] === "-") {
            if (this.text.length > 8) {
                this.text = this.text.substr(0, 8);
            }
        } else {
            if (this.text.length > 7) {
                this.text = this.text.substr(0, 7);
            }
        }

        if (this.text[0] === separator) {
            this.text = "0" + this.text;
        }

        if (minValue < 0 && maxValue < 0) {
            if (this.text[0] !== "-")
                this.text = "-" + this.text[0];
        } else if (minValue >= 0 && maxValue >= 0) {
            if (this.text[0] === "-")
                this.text = this.text.substr(0, 0);
        }

        if (val < minValue || val > maxValue) {
            this.text = this.text.substr(0, 0);
        }
        if (this.text.match(regex))
            if (this.text.match(regex).length > 1) {
                this.text = this.text.substr(0, 0);
            }

        if (this.text.match(/-/g))
            if (this.text.match(/-/g).length > 1) {
                this.text = this.text.substr(0, 0);
            }
    }
}





function onGraphClick(event) {
    console.log("CLICK!");
    let rect = document.getElementById("graph").getBoundingClientRect();
    let r = false;
    r = parseFloat(rInput.text);
    let width = 300;
    let height = 300;
    let x = (rect.width-width)/2;
    let y = (rect.height-height)/2+rect.top;
    console.log(r);
    console.log(event.clientX, rect.left, rect.width, event.clientY, rect.top, rect.height);
    if (event.clientX - x < width && event.clientY - y < height && r) {
        let valueX = ((event.clientX - x - width / 2) * r * 3) / (width);
        let valueY = -((event.clientY - y - height / 2) * r * 3) / (height);
        if (valueX >= -3 && valueX <= 5 && valueY>= -5 && valueY <= 3){
        $.ajax({
            type: "POST",
            url: "point",
            data: {
                "command": "point",
                "x": valueX,
                "y": valueY,
                "r": r,
                "token": localStorage.getItem(422),
                "username": localStorage.getItem(423)
            },
            success: onAnswerPoint,
            dataType: "json"
        });}
        else {
            alert("Вы тыкнули вне зоны действия:(");
        }
    }
}

let colorStroke = "#424874";
let colorFill = "#424874";
let colorGraphStroke = "rgba(0,0,0,0)";
let colorGraphFill = "rgba(115, 213, 131, 0.5)";
let colorGreenStroke = "rgba(115, 213, 131, 0.5)";
let colorGreenFill = "rgba(112,255,64, 1)";
let colorPointStroke = "#AC7140";
let colorPointFill = "#E57C25";

var elem = document.getElementById("canvas");
var ctx = elem.getContext('2d');
var small = 5;
var big = 15;
let basicX = 0;
let basicY = 0;
let basicR = 5;

drawSample();

    function drawSample() {
        /*
            Draw background
        */
        ctx.fillStyle = $('body').css('backgroundColor');
        ctx.fillRect(0, 0, elem.width, elem.height);
        /*
            Options for graphic-zone
            I used stroke style to smooth lines
        */
        ctx.lineWidth = 1;
        ctx.strokeStyle = "hsl(0,0%,100%)";
        ctx.fillStyle = colorGraphFill;
        ctx.strokeStyle = colorGraphStroke;
        /*
            Drawing graphic-zone
            Actually, I should make a function
            for each drawings but I don't care
        */
        ctx.fillRect(elem.width/2, elem.height/2, elem.width/3, -elem.height/3); //Rectange drawing
        ctx.strokeRect(elem.width/2, elem.height/2, elem.width*5/6, elem.height*5/6);

        ctx.beginPath(); // Triangle drawing
        ctx.moveTo(elem.width/2, elem.height/6);
        ctx.lineTo(elem.width*2/6, elem.height/2);
        ctx.lineTo(elem.width/2, elem.height/2);
        ctx.lineTo(elem.width/2, elem.height/6);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath(); // Arc drawind
        ctx.lineWidth = 2;
        ctx.arc(elem.width/2, elem.height/2, elem.width/6, 0, Math.PI/2, false);
        ctx.stroke();
        ctx.lineWidth = 1;
        ctx.moveTo(elem.width/2, elem.height/2); // Canvas can't draw filled arc
        ctx.lineTo(elem.width*4/6, elem.height/2); // It's the reason why I draw another triangle
        ctx.lineTo(elem.width/2, elem.height*4/6);
        ctx.lineTo(elem.width/2, elem.height/2);
        ctx.fill();
        ctx.closePath();
        /*
            Option for graphic lines and notes
        */
        ctx.fillStyle = colorFill;
        ctx.strokeStyle = colorStroke;
        ctx.lineWidth = 1;
        ctx.setTransform(1,0,0,1,0.5,0.5);
        ctx.font = "10pt serif";
        /*
            Lines and notes drawing
        */
        ctx.beginPath();
        ctx.moveTo(elem.width/2, elem.height);
        ctx.lineTo(elem.width/2, 0);
        ctx.lineTo(elem.width/2 + small, big);
        ctx.moveTo(elem.width/2, 0);
        ctx.lineTo(elem.width/2 - small, big);
        ctx.moveTo(0, elem.height/2);
        ctx.lineTo(elem.width, elem.height/2);
        ctx.lineTo(elem.width - big, elem.height/2 + small);
        ctx.moveTo(elem.width, elem.height/2);
        ctx.lineTo(elem.width - big, elem.height/2 - small);
        ctx.moveTo(elem.width/2 - small, elem.height*1/6);
        ctx.lineTo(elem.width/2 + small, elem.height*1/6);
        ctx.fillText("R", elem.width/2 + 2*small, elem.height*1/6 + small);
        ctx.moveTo(elem.width/2 - small, elem.height*2/6);
        ctx.lineTo(elem.width/2 + small, elem.height*2/6);
        ctx.fillText("R/2", elem.width/2 + 2*small, elem.height*2/6 + small);
        ctx.moveTo(elem.width/2 - small, elem.height*4/6);
        ctx.lineTo(elem.width/2 + small, elem.height*4/6);
        ctx.fillText("R/2", elem.width/2 + 2*small, elem.height*4/6 + small);
        ctx.moveTo(elem.width/2 - small, elem.height*5/6);
        ctx.lineTo(elem.width/2 + small, elem.height*5/6);
        ctx.fillText("R", elem.width/2 + 2*small, elem.height*5/6 + small);
        ctx.moveTo(elem.width*1/6, elem.height/2 + small);
        ctx.lineTo(elem.width*1/6, elem.height/2 - small);
        ctx.fillText("R", elem.width*1/6 - small, elem.height/2 - 2*small);
        ctx.moveTo(elem.width*2/6, elem.height/2 + small);
        ctx.lineTo(elem.width*2/6, elem.height/2 - small);
        ctx.fillText("R/2", elem.width*2/6 - small, elem.height/2 - 2*small);
        ctx.moveTo(elem.width*4/6, elem.height/2 + small);
        ctx.lineTo(elem.width*4/6, elem.height/2 - small);
        ctx.fillText("R/2", elem.width*4/6 - small, elem.height/2 - 2*small);
        ctx.moveTo(elem.width*5/6, elem.height/2 + small);
        ctx.lineTo(elem.width*5/6, elem.height/2 - small);
        ctx.fillText("R", elem.width*5/6 - small, elem.height/2 - 2*small);
        ctx.stroke();
        ctx.closePath();
        /*
            Option for green zone
        *//*
    ctx.fillStyle = colorGreenFill;
    ctx.strokeStyle = colorGreenStroke;*/
        /*
            Green zone drawing
        *//*
    ctx.fillRect(-10,-10,20,30);
    ctx.fillRect(elem.width/2 - (3/r)*elem.width/3, elem.height/2 + (3/r)*elem.height/3, (8/r)*elem.width/3, -(8/r)*elem.height/3);
    ctx.strokeRect(elem.width/2 - (3/r)*elem.width/3, elem.height/2 + (3/r)*elem.height/3, (8/r)*elem.width/3, -(8/r)*elem.height/3);
    /*
        Option for point
    */
        table.points.forEach(function (point) {
            let x = point.x;
            let y = point.y;
            let r = rInput.text;
            if (point.hit==="true") {
                ctx.fillStyle = colorGreenFill;
                ctx.strokeStyle = colorGreenStroke;
            } else {
                ctx.fillStyle = colorPointFill;
                ctx.strokeStyle = colorPointStroke;
            }

            ctx.lineWidth = 1;
            var valx = elem.width / 2 + (x / r) * elem.width / 3;
            var valy = elem.height / 2 - (y / r) * elem.height / 3
            /*
                Point drawing
            */
            ctx.beginPath();
            ctx.arc(valx, valy, 4, 0, 2 * Math.PI, true);
            ctx.fill();
            ctx.stroke();
            ctx.closePath()
        });
    }




