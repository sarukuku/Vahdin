GoogleMap = function (elementId, apiKey) {
    
    var apiLoaded = false,
        callQueue = [ ],
        
        // returns a function that, when called, will make sure the Google
        // Maps API is loaded before calling the given function f
        apiCall = function (f) {
        return function () {
            var that = this,
                args = arguments;
            if (!apiLoaded) {
                callQueue.push(function () {
                    f.apply(that, args);
                });
            } else {
                f.apply(that, args);
            }
        };
    };
    
    // dynamically loads the Google Maps API with the given API key
    var loadAPI = function (apiKey) {
        var script = document.createElement('script');
        script.type = 'text/javascript';
        
        var callbackName = 'google_maps_callback_'+(new Date()).valueOf();
        window[callbackName] = function () {
            apiLoaded = true;
            while (callQueue.length > 0) {
                callQueue.shift()();
            }
            delete window[callbackName];
        };
        
        script.src = 'https://maps.googleapis.com/maps/api/js?sensor=false&key='+apiKey+'&callback='+callbackName;
        document.body.appendChild(script);
    };
    
    var dom = document.getElementById(elementId);
    var map;
    var markers = { };
    loadAPI(apiKey);
    apiCall(function () {
        map = new google.maps.Map(dom, {
            'center': new google.maps.LatLng(-34.397, 150.644),
            'zoom': 8,
            'mapTypeId': google.maps.MapTypeId.ROADMAP
        });
        google.maps.event.addListener(map, 'click', function (event) {
            GoogleMap.click(event.latLng.lat(), event.latLng.lng());
        });
    })();
    
    /** Adds a marker to the specified coordinates. */
    this.addMarker = apiCall(function (lat, lng, id) {
        markers[id] = new google.maps.Marker({
            'position': new google.maps.LatLng(lat, lng),
            'map': map,
            'icon': 'VAADIN/themes/vahdintheme/img/google-maps-icon-red.png'
        });
    });
    
    /** Removes the specified marker. */
    this.removeMarker = apiCall(function (id) {
        markers[id].setMap(null);
        delete markers[id];
    });
    
    /** Centers the map at the specified coordinates. */
    this.center = apiCall(function (lat, lng) {
        map.panTo(new google.maps.LatLng(lat, lng));
    });
};
