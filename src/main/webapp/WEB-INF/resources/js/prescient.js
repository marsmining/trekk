//
// prescient.js - first pass at gesture recognition
//

/* global pa, $, Rx */

var Prescient = {};

// print out the queue from our "google analytics style" script tag
//
(function() {
    "use strict";

    pa.q.forEach(function(x) {
        console.log("q: " + Array.prototype.slice.call(x, 0).join(","));
    });
}());

// recognition component - depends on "Prescient" global
//
(function(p) {
    "use strict";

    function mkScrollDelta($el) {
        var lastPosY = 0;
        return function() {
            var currentPosY = $el.scrollTop(),
                delta = lastPosY - currentPosY;
            lastPosY = currentPosY;
            return delta;
        };
    }

    // this function is probably used for it's side-effects.
    // might return a handle to what was initialized, but the
    // general idea is this fn would attach 1 or more event
    // handlers, at which point the app is just reactive
    function init(config) {
        console.log("init", config);

        // imagine implementation of scroll-down, then up
        //
        // so if we could imagine scroll-down message fires, but
        // the message has a timeout, and then we want to have a
        // non-deterministic choice where we wait to receive both
        // scroll-down and up messages at same time, we don't
        // attempt to read a scroll-down unless we have a scroll-up

        // this could be done with callbacks (cps), promises, or
        // reactive streams, or even with a channels style (csp)
        // let's try this fancy new reactive lib

        var sampleTime = 500,
            deltaMin = 200,
            scrollDelta = Rx.Observable.fromEvent(window, "scroll").
                sample(sampleTime).
                map(mkScrollDelta($(window))).
                filter(function(n) {
                    return Math.abs(n) > deltaMin;
                });

        scrollDelta.subscribe(function (n) {
            var dir = n > 0 ? "up" : "down";
            console.log("user scrolled: " + dir);
        }, function (e) {
            console.log("err: ", e);
        });

    }

    // export any fns or state
    p.Recognition = { init : init };

}(Prescient));

// start things up when jQuery says the page is loaded
//
$(function() {
    "use strict";
    Prescient.Recognition.init({
        miniCampaigns : [{
            name : "Close Intent - Newsletter Action"
        }, {
            name : "Scroll Down then Up - Register Action",
            sequence : [{ type: "scroll-down" }, { type : "scroll-up" } ],
            action : { type : "alert", message : "You scrolled up then down" }
        }]
    });
});