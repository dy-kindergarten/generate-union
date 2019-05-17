function getWebRoot() {
    var appName = "";
    var host = window.location.host;
    var schema = document.location.protocol;
    var webRoot =  schema + "//" + (appName == "" ? (host + "/") : (host + "/" + appName + "/"));
    return webRoot;
}

function getUrl(url) {
    return getWebRoot() + "api/" + url;
}