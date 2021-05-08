var example = {
    "logId": "ff2b4d49-d6cc-464c-8a52-a016ed5ef023",
    "serviceName": "NL-API-GATEWAY",
    "nodes": {
        "logId": null,
        "serviceName": "CLIENT",
        "request": null,
        "response": null,
        "statusCode": null,
        "statusResponse": null,
        "endpoint": null,
        "grayLogBeanList": null,
        "children": [{
            "logId": "ff2b4d49-d6cc-464c-8a52-a016ed5ef023",
            "serviceName": "NL-API-GATEWAY",
            "request": null,
            "response": null,
            "statusCode": null,
            "statusResponse": null,
            "endpoint": null,
            "grayLogBeanList": [{
                "server": "manjunathr",
                "requesterId": "",
                "loggerLevel": "INFO",
                "ip": "ip6-localhost",
                "requestMethod": "GET",
                "className": "com.middleware.nuvelink.apigateway.config.InboundOutboundLoggingFilter",
                "simpleClassName": "InboundOutboundLoggingFilter",
                "source": "localhost",
                "message": "Inbound Request : {\"endpoint\":\"http://localhost:8081/api/v1/admin/users?pageNumber=1&pageSize=1&sortBy=username&sortType=ASCENDING\",\"requestMethod\":\"GET\",\"ip\":\"ip6-localhost\",\"userId\":\"a79fe6a6-ff3c-4192-890a-5022de787bde\",\"tenant\":\"mycompany1\",\"use",
                "userId": "a79fe6a6-ff3c-4192-890a-5022de787bde",
                "endpoint": "http://localhost:8081/api/v1/admin/users?pageNumber=1&pageSize=1&sortBy=username&sortType=ASCENDING",
                "request": null,
                "response": null,
                "responseCode": null,
                "environment": "local",
                "server_fqdn": "manjunathr",
                "full_message": "Inbound Request : {\"endpoint\":\"http://localhost:8081/api/v1/admin/users?pageNumber=1&pageSize=1&sortBy=username&sortType=ASCENDING\",\"requestMethod\":\"GET\",\"ip\":\"ip6-localhost\",\"userId\":\"a79fe6a6-ff3c-4192-890a-5022de787bde\",\"tenant\":\"mycompany1\",\"username\":\"mycompany1_admin\"}",
                "service": "NL-API-GATEWAY",
                "logId": "ff2b4d49-d6cc-464c-8a52-a016ed5ef023",
                "facility": "logstash-gelf",
                "inbound_json": "{\"endpoint\":\"http://localhost:8081/api/v1/admin/users?pageNumber=1&pageSize=1&sortBy=username&sortType=ASCENDING\",\"requestMethod\":\"GET\",\"ip\":\"ip6-localhost\",\"userId\":\"a79fe6a6-ff3c-4192-890a-5022de787bde\",\"tenant\":\"mycompany1\",\"username\":\"mycompany1_admin\"}",
                "tenant": "mycompany1",
                "timestamp": "2021-05-03T12:30:17.593Z",
                "username": "mycompany1_admin",
                "stackTrace": ""
            }],
            "children": [{
                "logId": "b855dd43-b9f3-42d7-9fb3-c05811583a17",
                "serviceName": "NL-ADMIN-API",
                "request": null,
                "response": null,
                "statusCode": null,
                "statusResponse": null,
                "endpoint": null,
                "grayLogBeanList": [{
                    "server": "manjunathr",
                    "requesterId": "ff2b4d49-d6cc-464c-8a52-a016ed5ef023",
                    "loggerLevel": "INFO",
                    "ip": "",
                    "requestMethod": "",
                    "className": "com.middleware.nuvelink.commons.config.webconfig.RequestInterceptor",
                    "simpleClassName": "RequestInterceptor",
                    "source": "localhost",
                    "message": "Principal : Principal(userName=mycompany1_admin, userId=a79fe6a6-ff3c-4192-890a-5022de787bde, email=mycompany1_admin@gmail.com, tenantName=mycompany1, accessToken=Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkTjBDdnRrUTR1NGt2WTFZbXFDMWV",
                    "userId": "",
                    "endpoint": "",
                    "request": null,
                    "response": null,
                    "responseCode": null,
                    "environment": "local",
                    "server_fqdn": "manjunathr",
                    "full_message": "Principal : Principal(userName=mycompany1_admin, userId=a79fe6a6-ff3c-4192-890a-5022de787bde, email=mycompany1_admin@gmail.com, tenantName=mycompany1, accessToken=Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkTjBDdnRrUTR1NGt2WTFZbXFDMWVFUV9PcXRobFp3UV9fUVNOQVpSSEw0In0.eyJleHAiOjE2MjAwNDg0MDgsImlhdCI6MTYyMDA0NDgwOCwianRpIjoiNmE5ZDE2ZjktNDgwOC00YjMxLTk1MDMtYmQxZDNjMGRhMmIyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL051dmVwcm8iLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImFjY291bnQiXSwic3ViIjoiYTc5ZmU2YTYtZmYzYy00MTkyLTg5MGEtNTAyMmRlNzg3YmRlIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwic2Vzc2lvbl9zdGF0ZSI6ImNiOWYxYTIxLTAyZjMtNDFjNy1hODEzLTEwMmQ4NzQyMTJiNiIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiY29tcGFueV9vd25lciIsImdyb3VwX2FkbWluIiwib2ZmbGluZV9hY2Nlc3MiLCJjb21wYW55X2FkbWluIiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtdXNlcnMiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgZ3JvdXAgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6Im15Y29tcGFueTEgYWRtaW4iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJteWNvbXBhbnkxX2FkbWluIiwiZ2l2ZW5fbmFtZSI6Im15Y29tcGFueTEiLCJsb2NhbGUiOiJlbiIsImZhbWlseV9uYW1lIjoiYWRtaW4iLCJlbWFpbCI6Im15Y29tcGFueTFfYWRtaW5AZ21haWwuY29tIiwiZ3JvdXAiOlsiL215Y29tcGFueTEvYWRtaW5zL2NvbXBhbnlfb3duZXIiLCIvbXljb21wYW55MSJdfQ.Qh1QvweRZY1IJEx-OALvaK7XN3eElcRdttQNNG5VYyYVHZrXMGhl283hreX1Zs8w7Zrq0FZQaSrHOnfTuY2EXJP34y-_caQ9m0Xoc1AYwMCiNVsOCIJnAaA1Tw0q2EdkDDXoJTmwk213Y3i2lV9LlpBr8q-9codxRUJnzG1hmdGOztO4JUOcWdtvnnSJoKFWgislZeICxRn6cGJ8n1R1Ee02d7odeaod8V-rM9Q9cd4jj1HaZSv8YsM4EGlpLytoI0ZULJ5acoNicO1k3a74nhcMePkeYYpj9yrcZFJEJNz_NiUQVM-98_5PVT1UivZn8X-rKvM08uQ_PCuocl2uvQ, isAdmin=false, locale=en)",
                    "service": "NL-ADMIN-API",
                    "logId": "b855dd43-b9f3-42d7-9fb3-c05811583a17",
                    "facility": "logstash-gelf",
                    "inbound_json": "",
                    "tenant": "",
                    "timestamp": "2021-05-03T12:30:17.630Z",
                    "username": "",
                    "stackTrace": ""
                }, {
                    "server": "manjunathr",
                    "requesterId": "ff2b4d49-d6cc-464c-8a52-a016ed5ef023",
                    "loggerLevel": "TRACE",
                    "ip": "",
                    "requestMethod": "",
                    "className": "org.hibernate.type.descriptor.sql.BasicBinder",
                    "simpleClassName": "BasicBinder",
                    "source": "localhost",
                    "message": "binding parameter [1] as [VARCHAR] - [mycompany1]",
                    "userId": "",
                    "endpoint": "",
                    "request": null,
                    "response": null,
                    "responseCode": null,
                    "environment": "local",
                    "server_fqdn": "manjunathr",
                    "full_message": "binding parameter [1] as [VARCHAR] - [mycompany1]",
                    "service": "NL-ADMIN-API",
                    "logId": "b855dd43-b9f3-42d7-9fb3-c05811583a17",
                    "facility": "logstash-gelf",
                    "inbound_json": "",
                    "tenant": "",
                    "timestamp": "2021-05-03T12:30:17.643Z",
                    "username": "",
                    "stackTrace": ""
                }, {
                    "server": "manjunathr",
                    "requesterId": "ff2b4d49-d6cc-464c-8a52-a016ed5ef023",
                    "loggerLevel": "DEBUG",
                    "ip": "",
                    "requestMethod": "",
                    "className": "org.hibernate.engine.jdbc.spi.SqlStatementLogger",
                    "simpleClassName": "SqlStatementLogger",
                    "source": "localhost",
                    "message": "select tenantenti0_.tenant_id as tenant_i1_2_, tenantenti0_.created_at as created_2_2_, tenantenti0_.created_by as created_3_2_, tenantenti0_.modified_at as modified4_2_, tenantenti0_.modified_by as modified5_2_, tenantenti0_.deleted_on as deleted_6",
                    "userId": "",
                    "endpoint": "",
                    "request": null,
                    "response": null,
                    "responseCode": null,
                    "environment": "local",
                    "server_fqdn": "manjunathr",
                    "full_message": "select tenantenti0_.tenant_id as tenant_i1_2_, tenantenti0_.created_at as created_2_2_, tenantenti0_.created_by as created_3_2_, tenantenti0_.modified_at as modified4_2_, tenantenti0_.modified_by as modified5_2_, tenantenti0_.deleted_on as deleted_6_2_, tenantenti0_.description as descript7_2_, tenantenti0_.display_name as display_8_2_, tenantenti0_.locale as locale9_2_, tenantenti0_.owner_id as owner_i10_2_, tenantenti0_.tenant_name as tenant_11_2_, tenantenti0_.tenant_status as tenant_12_2_, tenantenti0_.user_limit as user_li13_2_ from tenant_entity tenantenti0_ where tenantenti0_.tenant_name=?",
                    "service": "NL-ADMIN-API",
                    "logId": "b855dd43-b9f3-42d7-9fb3-c05811583a17",
                    "facility": "logstash-gelf",
                    "inbound_json": "",
                    "tenant": "",
                    "timestamp": "2021-05-03T12:30:17.639Z",
                    "username": "",
                    "stackTrace": ""
                }],
                "children": null,
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
    element.innerHTML = defaultNode.requestBody ? defaultNode.requestBody : "";

    var element = document.getElementById("request-params-data");
    element.innerHTML = defaultNode.requestParams ? defaultNode.requestParams : "";

    var element = document.getElementById("response-data");
    element.innerHTML = defaultNode.responseBody ? defaultNode.responseBody : "";

    var element = document.getElementById("status-data");
    element.innerHTML = defaultNode.responseStatus ? defaultNode.responseStatus : "";

    var element = document.getElementById("status-code-data");
    element.innerHTML = defaultNode.responseCode ? defaultNode.responseCode : "";


    if (defaultNode.responseCode) {
        element.innerHTML = defaultNode.responseCode;

        if (defaultNode.responseCode != 200) {
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
    element.innerHTML = defaultNode.tenant ? defaultNode.tenant : "";

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
    element.innerHTML = data.requestBody ? data.requestBody : "";

    var element = document.getElementById("request-params-data");
    element.innerHTML = data.requestParams ? data.requestParams : "";

    var element = document.getElementById("response-data");
    element.innerHTML = data.responseBody ? data.responseBody : "";

    var element = document.getElementById("status-data");
    element.innerHTML = data.responseStatus ? defaultNode.responseStatus : "";

    var element = document.getElementById("status-code-data");
    if (data.responseCode) {
        element.innerHTML = data.responseCode;

        if (data.responseCode != 200) {
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
    element.innerHTML = data.tenant ? data.tenant : "";

    var element = document.getElementById("url-data");
    element.innerHTML = data.endpoint ? data.endpoint : "";

    createTable(data);
}

function resetData() {

    var element = document.getElementById("service-name-data");
    element.innerHTML = "";

    var element = document.getElementById("log-id-data");
    element.innerHTML = "";

    var element = document.getElementById("request-body-data");
    element.innerHTML = "";

    var element = document.getElementById("request-params-data");
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
    var div = document.getElementById("service-detail-body-logs-table");
    div.innerHTML = "";

    var table = document.getElementById("service-detail-body-logs-table");


    if (data.grayLogBeanList) {
        var rowNumber = 0;
        var colNumber = 0;

        var row = table.insertRow(rowNumber);

        var cell = row.insertCell(colNumber);
        cell.innerHTML = "<b>Timestamp</b>";
        cell.classList.add("timestamp");

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Service</b>";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Log Id";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Requester Id";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Server";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Logger Level";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Ip";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Request Method";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Class Name";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Simple Class Name";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Source";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Message";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Full Message";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Tenant";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>User Id";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>User Name";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Endpoint";

        var cell = row.insertCell(++colNumber);
        cell.innerHTML = "<b>Response";

        data.grayLogBeanList.forEach(function(object, index) {

            colNumber = 0;

            var bean = object;

            var row = table.insertRow(index + 1);

            if (bean.loggerLevel == "ERROR") {
                row.style.backgroundColor = "#f3b9b9";
            }

            var cell = row.insertCell(colNumber);
            cell.innerHTML = bean.timestamp;


            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.service;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.logId;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.requesterId;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.server;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.loggerLevel;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.ip;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.requestMethod;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.className;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.simpleClassName;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.source;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.message;
            cell.style.overflow = "scroll";
            cell.style.maxWidth = "500px";

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.full_message;
            cell.style.overflow = "scroll";
            cell.style.maxWidth = "500px";

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.tenant;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.userId;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.username;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.endpoint;

            var cell = row.insertCell(++colNumber);
            cell.innerHTML = bean.responseBody;
        });

    } else {
        div.style.border = "none";
        div.style.fontSize = "80px";
        div.style.textAlign = "center";
        div.innerHTML = "Oops!!.. No Logs Found!";
    }
}