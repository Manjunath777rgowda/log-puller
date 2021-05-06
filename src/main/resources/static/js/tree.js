var example = {
    "logId": "2",
    "serviceName": null,
    "statusCode": 500,
    "nodes": {
        "logId": null,
        "serviceName": "CLIENT",
        "request": null,
        "response": null,
        "grayLogBeanList": null,
        "children": [{
            "logId": "2",
            "serviceName": "NL-API-GATEWAY",
            "request": null,
            "response": null,
            "statusCode": 200,
            "grayLogBeanList": [{
                "server": "manjunathr",
                "requesterId": "",
                "loggerLevel": "INFO",
                "ip": "ip6-localhost",
                "requestMethod": "POST",
                "className": "com.middleware.nuvelink.apigateway.config.InboundOutboundLoggingFilter",
                "simpleClassName": "InboundOutboundLoggingFilter",
                "source": "localhost",
                "message": "Inbound Request : {\"endpoint\":\"http://localhost:8081/api/v1/catalogs/ftp/retrieve\",\"requestMethod\":\"POST\",\"ip\":\"ip6-localhost\",\"userId\":\"d82a25ad-0927-4b81-891c-62f6ebf1392c\",\"tenant\":\"admin\",\"username\":\"admin\"}",
                "userId": "d82a25ad-0927-4b81-891c-62f6ebf1392c",
                "endpoint": "http://localhost:8081/api/v1/catalogs/ftp/retrieve",
                "environment": "local",
                "server_fqdn": "manjunathr",
                "full_message": "Inbound Request : {\"endpoint\":\"http://localhost:8081/api/v1/catalogs/ftp/retrieve\",\"requestMethod\":\"POST\",\"ip\":\"ip6-localhost\",\"userId\":\"d82a25ad-0927-4b81-891c-62f6ebf1392c\",\"tenant\":\"admin\",\"username\":\"admin\"}",
                "service": "NL-API-GATEWAY",
                "logId": "2",
                "facility": "logstash-gelf",
                "inbound_json": "{\"endpoint\":\"http://localhost:8081/api/v1/catalogs/ftp/retrieve\",\"requestMethod\":\"POST\",\"ip\":\"ip6-localhost\",\"userId\":\"d82a25ad-0927-4b81-891c-62f6ebf1392c\",\"tenant\":\"admin\",\"username\":\"admin\"}",
                "tenant": "admin",
                "timestamp": "2021-05-03T12:50:13.395Z",
                "username": "admin",
                "stackTrace": ""
            }],
            "children": [{
                "logId": "3",
                "serviceName": "NL-CATALOG",
                "request": null,
                "response": null,
                "statusCode": 500,
                "grayLogBeanList": [
                    {}
                ],
                "children": [{
                    "logId": "4",
                    "serviceName": "NL-APPLICATION-1",
                    "request": null,
                    "response": null,
                    "grayLogBeanList": [
                        {}
                    ],
                    "children": null,
                    "default": false
                }, {
                    "logId": "5",
                    "serviceName": "NL-APPLICATION-2",
                    "request": null,
                    "response": null,
                    "grayLogBeanList": [
                        {}
                    ],
                    "children": null,
                    "default": false
                }, {
                    "logId": "6",
                    "serviceName": "NL-APPLICATION-3",
                    "request": null,
                    "response": null,
                    "grayLogBeanList": [
                        {}
                    ],
                    "children": null,
                    "default": false
                }],
                "default": false
            }],
            "default": true
        }],
        "default": false
    }
};

// var treeData = "";

var defaultNode;

if (treeData == null)
    var treeData = example;



console.log("response : ", treeData);

// ************** Generate the tree diagram	 *****************
var margin = {
        top: 20,
        right: 120,
        bottom: 20,
        left: 120
    },
    width = 960 - margin.right - margin.left,
    height = 500 - margin.top - margin.bottom;

var i = 0,
    duration = 750,
    root;

var tree = d3.layout.tree().size([height, width]);

var diagonal = d3.svg.diagonal().projection(function(d) {
    return [d.y, d.x];
});

var svg = d3.select("div.node-tree").append("svg").attr("width", width + margin.right + margin.left).attr("height", height + margin.top + margin.bottom).append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

root = treeData.nodes;
root.x0 = height / 2;
root.y0 = 0;

update(root);

d3.select(self.frameElement).style("height", "500px");

function update(source) { // Compute the new tree layout.
    var nodes = tree.nodes(root).reverse(),
        links = tree.links(nodes);

    // Normalize for fixed-depth.
    nodes.forEach(function(d) {
        d.y = d.depth * 180;
    });

    // Update the nodes…
    var node = svg.selectAll("g.node").data(nodes, function(d) {
        return d.id || (d.id = ++i);
    });

    // Enter any new nodes at the parent's previous position.
    var nodeEnter = node.enter().append("g").attr("class", "node").attr("transform", function(d) {
        return "translate(" + source.y0 + "," + source.x0 + ")";
    }).on("click", click);

    nodeEnter.append("circle").attr("r", 1e-6).style("fill", function(d) {

        if (d.default) {
            defaultNode = d;
        }
        return d.default ? "yellow" : (d._children ? "lightsteelblue" : "#fff");
    });

    nodeEnter.append("text").attr("x", function(d) {
        return d.children || d._children ? -13 : 13;
    }).attr("dy", ".35em").attr("text-anchor", function(d) {
        return d.children || d._children ? "end" : "start";
    }).text(function(d) {
        return d.serviceName;
    }).style("fill-opacity", 1e-6);

    // Transition nodes to their new position.
    var nodeUpdate = node.transition().duration(duration).attr("transform", function(d) {
        return "translate(" + d.y + "," + d.x + ")";
    });

    nodeUpdate.select("circle").attr("r", 10).style("fill", function(d) {
        return d.default ? "yellow" : (d._children ? "lightsteelblue" : "#fff");
    });

    nodeUpdate.select("text").style("fill-opacity", 1);

    // Transition exiting nodes to the parent's new position.
    var nodeExit = node.exit().transition().duration(duration).attr("transform", function(d) {
        return "translate(" + source.y + "," + source.x + ")";
    }).remove();

    nodeExit.select("circle").attr("r", 1e-6);

    nodeExit.select("text").style("fill-opacity", 1e-6);

    // Update the links…
    var link = svg.selectAll("path.link").data(links, function(d) {
        return d.target.id;
    });

    // Enter any new links at the parent's previous position.
    link.enter().insert("path", "g").attr("class", "link").attr("d", function(d) {
        var o = {
            x: source.x0,
            y: source.y0
        };
        return diagonal({ source: o, target: o });
    });

    // Transition links to their new position.
    link.transition().duration(duration).attr("d", diagonal);

    // Transition exiting nodes to the parent's new position.
    link.exit().transition().duration(duration).attr("d", function(d) {
        var o = {
            x: source.x,
            y: source.y
        };
        return diagonal({ source: o, target: o });
    }).remove();

    // Stash the old positions for transition.
    nodes.forEach(function(d) {
        d.x0 = d.x;
        d.y0 = d.y;
    });
}

// Toggle children on click.
function click(d) {

    if (d.children) {
        d._children = d.children;
        d.children = null;
    } else {
        d.children = d._children;
        d._children = null;
    }
    update(d);

    displayData(d);
}

function activateSummary() {
    var x = document.getElementById("summary");
    x.classList.remove("de-activate")
    x.classList.add("activate");
    var y = document.getElementById("logs");
    y.classList.add("de-activate");
    y.classList.remove("activate");

    var i = document.getElementById("service-detail-body-summary");
    i.style.display = "block";
    var j = document.getElementById("service-detail-body-logs");
    j.style.display = "none";
}

function activatelogs() {
    var x = document.getElementById("logs");
    x.classList.remove("de-activate")
    x.classList.add("activate");
    var y = document.getElementById("summary");
    y.classList.add("de-activate");
    y.classList.remove("activate")

    var i = document.getElementById("service-detail-body-summary");
    i.style.display = "none";
    var j = document.getElementById("service-detail-body-logs");
    j.style.display = "block";
}

window.onload = function() {

    var element = document.getElementById("service-name-data");
    element.innerHTML = defaultNode.serviceName ? defaultNode.serviceName : "";

    var element = document.getElementById("log-id-data");
    element.innerHTML = defaultNode.logId ? defaultNode.logId : "";

    var element = document.getElementById("request-body-data");
    element.innerHTML = defaultNode.request ? defaultNode.request : "";

    var element = document.getElementById("response-data");
    element.innerHTML = defaultNode.response ? defaultNode.response : "";

    var element = document.getElementById("status-data");
    element.innerHTML = defaultNode.stautsData ? defaultNode.stautsData : "";

    var element = document.getElementById("status-code-data");
    element.innerHTML = defaultNode.statusCode ? defaultNode.statusCode : "";


    if (defaultNode.statusCode) {
        element.innerHTML = defaultNode.statusCode;

        if (defaultNode.statusCode != 200) {
            element.style.backgroundColor = "#f3b9b9";
        }
    }

    var element = document.getElementById("requester-id-data");
    element.innerHTML = defaultNode.requesterId ? defaultNode.requesterId : "";

    var element = document.getElementById("environment-data");
    element.innerHTML = defaultNode.environment ? defaultNode.environment : "";

    var element = document.getElementById("user-name-data");
    element.innerHTML = defaultNode.username ? defaultNode.username : "";

    var element = document.getElementById("user-id-data");
    element.innerHTML = defaultNode.userId ? defaultNode.userId : "";

    var element = document.getElementById("tenant-data");
    element.innerHTML = defaultNode.tenant ? defaultNode.userId : "";

    var element = document.getElementById("url-data");
    element.innerHTML = defaultNode.endpoint ? defaultNode.endpoint : "";

    createTable(defaultNode);

}

function displayData(data) {

    resetData();

    var element = document.getElementById("service-name-data");
    element.innerHTML = data.serviceName ? data.serviceName : "";

    var element = document.getElementById("log-id-data");
    element.innerHTML = data.logId ? data.logId : "";

    var element = document.getElementById("request-body-data");
    element.innerHTML = data.request ? data.request : "";

    var element = document.getElementById("response-data");
    element.innerHTML = data.response ? data.response : "";

    var element = document.getElementById("status-data");
    element.innerHTML = data.stautsData ? data.stautsData : "";

    var element = document.getElementById("status-code-data");

    if (data.statusCode) {
        element.innerHTML = data.statusCode;

        if (data.statusCode != 200) {
            element.style.backgroundColor = "#f3b9b9";
        }
    }

    var element = document.getElementById("requester-id-data");
    element.innerHTML = data.requesterId ? data.requesterId : "";

    var element = document.getElementById("environment-data");
    element.innerHTML = data.environment ? data.environment : "";

    var element = document.getElementById("user-name-data");
    element.innerHTML = data.username ? data.username : "";

    var element = document.getElementById("user-id-data");
    element.innerHTML = data.userId ? data.userId : "";

    var element = document.getElementById("tenant-data");
    element.innerHTML = data.tenant ? data.userId : "";

    var element = document.getElementById("url-data");
    element.innerHTML = data.endpoint ? data.endpoint : "";
}

function resetData() {

    var element = document.getElementById("service-name-data");
    element.innerHTML = "";

    var element = document.getElementById("log-id-data");
    element.innerHTML = "";

    var element = document.getElementById("request-body-data");
    element.innerHTML = "";

    var element = document.getElementById("response-data");
    element.innerHTML = "";

    var element = document.getElementById("status-code-data");
    element.innerHTML = "";
    element.style.backgroundColor = "rgb(197, 235, 173)";

    var element = document.getElementById("status-data");
    element.innerHTML = "";

    var element = document.getElementById("requester-id-data");
    element.innerHTML = "";

    var element = document.getElementById("environment-data");
    element.innerHTML = "";

    var element = document.getElementById("user-name-data");
    element.innerHTML = "";

    var element = document.getElementById("user-id-data");
    element.innerHTML = "";

    var element = document.getElementById("tenant-data");
    element.innerHTML = "";

    var element = document.getElementById("url-data");
    element.innerHTML = "";
}

function createTable(data) {
    var table = document.getElementById("service-detail-body-logs-table");

    if (data) {
        var rowNumber = 0;
        var colNumber = 0;

        var row = table.insertRow(rowNumber);
        var cell = row.insertCell(colNumber);
        cell.innerHTML = "Timestamp";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "service";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Log Id";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Requester Id";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Server";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Logger Level";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Ip";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Request Method";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Class Name";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Simple Class Name";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Source";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Message";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Full Message";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Tenant";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "User Id";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "User Name";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Endpoint";

        var cell = row.insertCell(colNumber + 1);
        cell.innerHTML = "Response";

        for (var j = 1; j <= data.grayLogBeanList.length; j++) {

            var bean = data.grayLogBeanList[j - 1];

            var colNumber = 0;

            debugger
            var row = table.insertRow(j);
            var cell = row.insertCell(colNumber);
            cell.innerHTML = bean.timestamp;

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "service";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Log Id";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Requester Id";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Server";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Logger Level";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Ip";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Request Method";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Class Name";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Simple Class Name";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Source";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Message";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Full Message";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Tenant";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "User Id";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "User Name";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Endpoint";

            var cell = row.insertCell(colNumber + 1);
            cell.innerHTML = "Response";
        }

    }
}