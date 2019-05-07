var baseURL = 'http://127.0.0.1:8080/';

var $axios = axios.create({
    baseURL: baseURL,
    timeout: 1000,
    headers: {'X-Custom-Header': 'foobar'}
});