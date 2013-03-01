jso_configure({
    'google': {
        'client_id': '412257911068.apps.googleusercontent.com',
        'redirect_uri': location.protocol + '//' + location.host + location.pathname,
        'authorization': 'https://accounts.google.com/o/oauth2/auth'
    }
});

OAuth2Button = function (elementId, provider) {
    var dom = document.getElementById(elementId);
    dom.addEventListener('click', function () {
        var authWin = window.open();
        
        var poll = function () {
            try {
                if (authWin.location.host === location.host) {
                    jso_checkfortoken(provider, authWin.location.href);
                    authWin.close();
                } else {
                    setTimeout(poll, 100);
                }
            } catch (e) {
                setTimeout(poll, 100);
            }
        };
        
        jso_registerRedirectHandler(function (url) {
            authWin.location.href = url;
            poll();
        });
        
        jso_wipe();
        $.oajax({
            'url': 'https://www.googleapis.com/oauth2/v1/userinfo',
            'jso_provider': 'google',
            'jso_allowia': true,
            'jso_scopes': [ 'https://www.googleapis.com/auth/userinfo.profile' ],
            'dataType': 'json',
            'success': function (data) {
                OAuth2Button.authenticate(data.id);
            },
            'error': function () {
                console.log('OAjax error.'); // TODO: error handling
            }
        });
    });
};