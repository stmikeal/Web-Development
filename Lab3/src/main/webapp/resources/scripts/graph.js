let colorStroke = "#ACBECE";
let colorFill = "#8C9EAE";
let colorGraphStroke = "rgba(0,0,0,0)";
let colorGraphFill = "rgba(115, 213, 131, 0.5)";
let colorGreenStroke = "rgba(115, 213, 131, 0.5)";
let colorGreenFill = "rgba(112,255,64, 0)";
let colorPointStroke = "#FFD900";
let colorPointFill = "#FFD900";
let colorGoalStroke = "rgba(115, 213, 131, 1)";
let colorGoalFill = "rgba(112,255,64, 1)";
let colorMissStroke = "#AC7140";
let colorMissFill = "#E57C25";

var elem = document.getElementById("graph");
var ctx = elem.getContext('2d');
var small = 5;
var big = 15;
let basicX = 0;
let basicY = 0;
var basicR = 1;
var pointsX = [];
var pointsY = [];
var pointsHit = [];

drawSample();
changeR(basicR);

function drawSample(x = basicX, y = basicY, r = basicR) {
    /*
        Draw background
    */
    ctx.fillStyle = "#222222";
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
    ctx.fillRect(elem.width / 2, elem.height / 2, elem.width / 6, -elem.height / 3); //Rectange drawing
    ctx.strokeRect(elem.width / 2, elem.height / 2, elem.width / 6, -elem.height / 3);

    ctx.beginPath(); // Triangle drawing
    ctx.moveTo(elem.width * 2 / 6, elem.height / 2);
    ctx.lineTo(elem.width / 2, elem.height / 6);
    ctx.lineTo(elem.width / 2, elem.height / 2);
    ctx.lineTo(elem.width * 2 / 6, elem.height / 2);
    ctx.fill();
    ctx.stroke();
    ctx.closePath();

    ctx.beginPath(); // Arc drawing
    ctx.lineWidth = 2;
    ctx.arc(elem.width / 2, elem.height / 2, elem.width / 3, 0, Math.PI / 2, false);
    ctx.stroke();
    ctx.lineWidth = 1;
    ctx.moveTo(elem.width / 2, elem.height / 2); // Canvas can't draw filled arc
    ctx.lineTo(elem.width * 5 / 6, elem.height / 2); // It's the reason why I draw another triangle
    ctx.lineTo(elem.width / 2, elem.height * 5 / 6);
    ctx.lineTo(elem.width / 2, elem.height / 2);
    ctx.fill();
    ctx.closePath();
    /*
        Option for graphic lines and notes
    */
    ctx.fillStyle = colorFill;
    ctx.strokeStyle = colorStroke;
    ctx.lineWidth = 1;
    ctx.setTransform(1, 0, 0, 1, 0.5, 0.5);
    ctx.font = "10pt serif";
    /*
        Lines and notes drawing
    */
    ctx.beginPath();
    ctx.moveTo(elem.width / 2, elem.height);
    ctx.lineTo(elem.width / 2, 0);
    ctx.lineTo(elem.width / 2 + small, big);
    ctx.moveTo(elem.width / 2, 0);
    ctx.lineTo(elem.width / 2 - small, big);
    ctx.moveTo(0, elem.height / 2);
    ctx.lineTo(elem.width, elem.height / 2);
    ctx.lineTo(elem.width - big, elem.height / 2 + small);
    ctx.moveTo(elem.width, elem.height / 2);
    ctx.lineTo(elem.width - big, elem.height / 2 - small);
    ctx.moveTo(elem.width / 2 - small, elem.height * 1 / 6);
    ctx.lineTo(elem.width / 2 + small, elem.height * 1 / 6);
    ctx.fillText("R", elem.width / 2 + 2 * small, elem.height * 1 / 6 + small);
    ctx.moveTo(elem.width / 2 - small, elem.height * 2 / 6);
    ctx.lineTo(elem.width / 2 + small, elem.height * 2 / 6);
    ctx.fillText("R/2", elem.width / 2 + 2 * small, elem.height * 2 / 6 + small);
    ctx.moveTo(elem.width / 2 - small, elem.height * 4 / 6);
    ctx.lineTo(elem.width / 2 + small, elem.height * 4 / 6);
    ctx.fillText("R/2", elem.width / 2 + 2 * small, elem.height * 4 / 6 + small);
    ctx.moveTo(elem.width / 2 - small, elem.height * 5 / 6);
    ctx.lineTo(elem.width / 2 + small, elem.height * 5 / 6);
    ctx.fillText("R", elem.width / 2 + 2 * small, elem.height * 5 / 6 + small);
    ctx.moveTo(elem.width * 1 / 6, elem.height / 2 + small);
    ctx.lineTo(elem.width * 1 / 6, elem.height / 2 - small);
    ctx.fillText("R", elem.width * 1 / 6 - small, elem.height / 2 - 2 * small);
    ctx.moveTo(elem.width * 2 / 6, elem.height / 2 + small);
    ctx.lineTo(elem.width * 2 / 6, elem.height / 2 - small);
    ctx.fillText("R/2", elem.width * 2 / 6 - small, elem.height / 2 - 2 * small);
    ctx.moveTo(elem.width * 4 / 6, elem.height / 2 + small);
    ctx.lineTo(elem.width * 4 / 6, elem.height / 2 - small);
    ctx.fillText("R/2", elem.width * 4 / 6 - small, elem.height / 2 - 2 * small);
    ctx.moveTo(elem.width * 5 / 6, elem.height / 2 + small);
    ctx.lineTo(elem.width * 5 / 6, elem.height / 2 - small);
    ctx.fillText("R", elem.width * 5 / 6 - small, elem.height / 2 - 2 * small);
    ctx.stroke();
    ctx.closePath();
    /*
        Option for green zone
    */
    ctx.fillStyle = colorGreenFill;
    ctx.strokeStyle = colorGreenStroke;
    /*
        Green zone drawing
    */
    ctx.fillRect(-10, -10, 20, 30);
    ctx.fillRect(elem.width / 2 - (5 / r) * elem.width / 3, elem.height / 2 + (3 / r) * elem.height / 3, (10 / r) * elem.width / 3, -(8 / r) * elem.height / 3);
    ctx.strokeRect(elem.width / 2 - (5 / r) * elem.width / 3, elem.height / 2 + (3 / r) * elem.height / 3, (10 / r) * elem.width / 3, -(8 / r) * elem.height / 3);
    /*
        Option for point
    */
    ctx.fillStyle = colorPointFill;
    ctx.strokeStyle = colorPointStroke;
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
    ctx.closePath();
    /*
        Old points draw
     */
    for (let i = 0; i < pointsX.length; i++) {
        let thisx = elem.width / 2 + (pointsX[i] / r) * elem.width / 3;
        let thisy = elem.height / 2 - (pointsY[i] / r) * elem.height / 3
        if (isHit(pointsX[i], pointsY[i])) {
            ctx.fillStyle = colorGoalFill;
            ctx.strokeStyle = colorGoalStroke;
        } else {
            ctx.fillStyle = colorMissFill;
            ctx.strokeStyle = colorMissStroke;
        }
        ctx.beginPath();
        ctx.arc(thisx, thisy, 4, 0, 2 * Math.PI, true);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    }
}

function isHit(x, y) {
    return y - 2 * x <= basicR && y >= 0 && x <= 0 ||
        x * x + y * y <= basicR * basicR && x >= 0 && y <= 0 ||
        x >= 0 && y >= 0 && y <= basicR && 2 * x < basicR;
}

function changeR(r) {
    basicR = r;
    drawSample();
}

function movePoint(x, y) {
    basicX = x;
    basicY = y;
    drawSample();
}

function clearTable() {
    pointsX = [];
    pointsY = [];
    pointsHit = [];
    drawSample();
}

function loadPointsFromTable() {
    let table = document.querySelector("#historyTable");
    if (!table) {
        console.log("WARNING[table value undefined]: " + table);
        return;
    }
    let $table_rows = table.tBodies[0].rows;
    if ($table_rows[0].cells[0].innerText === '') return;
    for (let i = 0; i < $table_rows.length; ++i) {
        pointsX.push(parseFloat($table_rows[i].cells[0].innerText.replace(",", ".")));
        pointsY.push(parseFloat($table_rows[i].cells[1].innerText.replace(",", ".")));
        pointsHit.push($table_rows[i].cells[5].innerText.trim());
    }
    drawSample();
}

function addMouseEvent() {
    let canvas = document.querySelector("#graph");

    canvas.addEventListener('mousedown', function (e) {
        let rect = document.querySelector("#graph").getBoundingClientRect();
        let r = PF("rMenu").value;
        if (!r) {
            r = 1;
        }
        if (event.clientX - rect.x < rect.width && event.clientY - rect.y < rect.height && r) {
            let valueX = ((event.clientX - rect.x - rect.width / 2) * r * 3) / (rect.width);
            let valueY = -((event.clientY - rect.y - rect.height / 2) * r * 3) / (rect.height);
            if (valueX >= -5 && valueX <= 5 && valueY >= -3 && valueY <= 5) {
                pointsX.push(valueX);
                pointsY.push(valueY);
                drawSample();
                let oldX = document.getElementById("j_idt16:xInput_input").value;
                let oldY = document.getElementById("j_idt16:yInput").value;
                document.getElementById("j_idt16:xInput_input").value = String(valueX).substr(0, 8);
                document.getElementById("j_idt16:yInput").value = String(valueY).substr(0, 8);
                document.getElementById("j_idt16:checkButton").click();
                document.getElementById("j_idt16:xInput_input").setAttribute("value", "2");
                document.getElementById("j_idt16:yInput").value = "2";
            }
        }
    }, false)
}

loadPointsFromTable();
addMouseEvent();