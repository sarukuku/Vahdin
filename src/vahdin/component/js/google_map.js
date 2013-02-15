(function () {
    
    var window = this;
    
    var loadAPI = function (apiKey, callback) {
        var script = document.createElement('script');
        script.type = 'text/javascript';
        
        var callbackName = 'google_maps_callback_'+(new Date()).valueOf();
        window[callbackName] = function () {
            callback();
            delete window[callbackName];
        };
        
        script.src = 'https://maps.googleapis.com/maps/api/js?sensor=false&key='+apiKey+'&callback='+callbackName;
        document.body.appendChild(script);
    };
    
    window.GoogleMap = function (apiKey) {
        
        var that = this;
        
        var dom = Array.prototype.pop.apply(document.getElementsByName('google_map'));
        
        loadAPI(apiKey, function () {
            var map = new google.maps.Map(dom, {
                'center': new google.maps.LatLng(-34.397, 150.644),
                'zoom': 8,
                'mapTypeId': google.maps.MapTypeId.ROADMAP
            });
        });
    };
})();