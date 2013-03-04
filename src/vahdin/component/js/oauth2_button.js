jso_configure({
    'google': {
        'client_id': '412257911068.apps.googleusercontent.com',
        'redirect_uri': location.protocol + '//' + location.host + location.pathname,
        'authorization': 'https://accounts.google.com/o/oauth2/auth'
    },
    'facebook': {
    	'client_id': '135567833284360',
    	'redirect_uri': 'http://vahdin.jelastic.servint.net',
        'authorization': 'https://www.facebook.com/dialog/oauth'
    }
});

OAuth2Button = function (elementId, provider) {
    var dom = document.getElementById(elementId);
    dom.addEventListener('click', function () {
        var authWin = window.open("", "", "menubar=no,location=no,status=no,width=640,height=480");
        
        var poll = function () {
            if (authWin.closed) return;
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
        
        switch (provider) {
        case 'google':
            var url = 'https://www.googleapis.com/oauth2/v1/userinfo',
                scopes = [ 'https://www.googleapis.com/auth/userinfo.profile' ],
                id_field_name = 'id';
            break;
        case 'facebook':
        	var url = 'https://graph.facebook.com/1586747406?fields=id', // Joonas' user id
            	scopes = [ '' ],
            	id_field_name = 'id';
            break;
        default:
            throw new Error("Unknown OAuth provider.");
        }
        $.oajax({
            'url': url,
            'jso_provider': provider,
            'jso_allowia': true,
            'jso_scopes': scopes,
            'dataType': 'json',
            'success': function (data) {
                OAuth2Button['authenticate_' + provider](data[id_field_name]);
            },
            'error': function () { }
        });
    });
};