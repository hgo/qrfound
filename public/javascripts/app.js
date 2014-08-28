require.config({
    baseUrl: '/public/javascripts',
    paths: {
        // the left side is the module ID,
        // the right side is the path to
        // the jQuery file, relative to baseUrl.
        // Also, the path should NOT include
        // the '.js' file extension. This example
        // is using jQuery 1.9.0 located at
        // js/lib/jquery-1.9.0.js, relative to
        // the HTML page.
        "jquery": 'jquery',
        'bootstrap': '//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min',
        "global": "global",
        "formhelpers-phone": 'bootstrap-formhelpers-phone'
    },
    shim: {
        "bootstrap": {
            deps: ["jquery"]
        },
        "global": {
        	deps: ["bootstrap"]
        },
        "formhelpers-phone":{
        	deps: ["jquery"]
        }
    }
});