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

/**
 * 打开时间选择框
 * @param type
 * @param idx
 */
function openChooser(type, idx) {
    let chooserNode = type + "-chooser";
    currentIdx = idx;
    $("#" + chooserNode).modal("show");
}