document.addEventListener("DOMContentLoaded", function () {
    let x = document.querySelector("#demo");
    let y = document.querySelector("#rangeShow");
    let getPos = document.querySelector("#geoLocate");
    //let range = document.querySelector("#range");
    //let stuffUrl = "http://18.216.115.205:8080/WebApplication15/webresources/some"
    let getStuff = document.querySelector("#getStuff");
    let submitData = document.querySelector("#submit");
    let data = document.querySelector("#data");
    let longitude = document.querySelector("#longitude");
    let latitude = document.querySelector("#latitude");
    let key = document.querySelector("#key");
    let range = document.querySelector("#range");
    let lat;
    let long;
    let printButton = document.querySelector("#printButton");
    getPos.addEventListener("click", function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.watchPosition(showPosition);
        } else {
            x.innerHTML = "Geolocation is not supported by this browser.";
        }
    });
    /*
    range.addEventListener("click", function getRange() {
        if (range.value != 0) {

            y.innerHTML = `Your range is ${range.value}`;
        } else {
            y.innerHTML = "Set your range.";
        }
    });
    */
    function showPosition(position) {
        lat = position.coords.latitude;
        long = position.coords.longitude;

        x.innerHTML = "Latitude: " + position.coords.latitude +
            "<br>Longitude: " + position.coords.longitude;
        var latlon = position.coords.latitude + "," + position.coords.longitude;

        var map = "https://maps.googleapis.com/maps/api/staticmap?center="
            + latlon + "&markers=color:red%7Clabel:C%7C" + latlon + "&zoom=18&size=400x300&key=AIzaSyA2AzcUJLiiu879rNxangBccd4YeH8FBBQ";
        //let map = "https://maps.googleapis.com/maps/api/staticmap?center="
        //+latlon+"&zoom=14&size=400x300&key=AIzaSyBu-916DdpKAjTmJNIgngS6HL_kDIKU0aU";
        console.log(position.coords.longitude + ", " + position.coords.latitude);
        document.querySelector("#mapholder").innerHTML = `<img src="${map}">`
    }
    let dataSubmit = document.querySelector("#dataSubmit");
    dataSubmit.addEventListener("click", function getLocationv2() {
        if (navigator.geolocation) {
            navigator.geolocation.watchPosition(showPositionv2);
        } else {
            x.innerHTML = "Geolocation is not supported by this browser.";
        }
    })
    function showPositionv2(position) {
        console.log("moi");
        console.log(long + ", " + lat);
    }
    submitData.addEventListener("click", function leaveData(position) {
        let somedata = data.value + ";" + long + ";" + lat + ";" + key.value + ";" + range.value;
        console.log(somedata);
        fetch("add", {
            method: 'POST',
            headers: {
                'Accept': 'text/plain',
                'Content-Type': 'text/plain'
            },
            body: somedata,
        })
            .catch(error => console.error('Error: ' + error))
            .then(response => console.log('Success:', response));
    })
    printButton.addEventListener("click", function printData() {
        /*
        let somedata = data.value + ";" + long + ";" + lat + ";" + key.value + ";" + range.value;
        console.log(somedata);
        fetch("add", {
            method: 'POST',
            headers: {
                'Accept': 'text/plain',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: somedata,
        })
            .catch(error => console.error('Error: ' + error))
            .then(response => console.log('Success:', response));
            */
        let somedata = data.value + ";" + long + ";" + lat + ";" + key.value + ";" + range.value;

        var details = {
            'somedata': somedata
        };

        var formBody = [];
        for (var property in details) {
            var encodedKey = encodeURIComponent(property);
            var encodedValue = encodeURIComponent(details[property]);
            formBody.push(encodedKey + "=" + encodedValue);
        }
        formBody = formBody.join("&");

        fetch('add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: formBody
        })
    })

    /*
    getStuff.addEventListener("click", function () {
        fetch(stuffUrl)
            .then(
                function (response) {
                    if (response.status !== 200) {
                        console.warn('Looks like there was a problem. Status Code: ' +
                            response.status);
                        return;
                    }
                    response.number.then(function (data) {
                        console.log(data);
                    });
                });
    });
    */
});

