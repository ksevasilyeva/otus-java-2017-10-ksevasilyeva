var statisticsSocket = function() {
    var ws;

    return {
        init: function() {
                ws = new WebSocket("ws://localhost:8093/ws-stat");

                ws.onopen = function (event) {
                    statisticsSocket.getStatistics();
                    refreshIntervalId = setInterval(statisticsSocket.getStatistics, 2000);
                }

                ws.onmessage = function (event) {
                    var statistics = JSON.parse(event.data);
                    document.getElementById("cacheSize").innerHTML = statistics.size;
                    document.getElementById("cacheHit").innerHTML = statistics.hit;
                    document.getElementById("cacheMiss").innerHTML = statistics.miss;
                }

                ws.onclose = function (event) {
                    ws.close();
                }
        },

        getStatistics: function() {
            ws.send("");
        }
    };
}();