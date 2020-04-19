<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>Canvas</title>
    <jsp:include page='header.jsp'/>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
</head>
<body>
    <h1> Histogram of timeSeries : ${serie.title}</h1>
    <div style="width: 75%">
        <input class="btn btn-lg btn-primary btn-block" type="button" value="Go back" onClick="document.location='/getSeries/${serie.id}';" />
        <canvas id ="myChart"></canvas>
    </div>
    <script>
        var ctx = document.getElementById('myChart').getContext('2d');
        var chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels : ${labels},
                datasets:[{
                fill: false,
                label:"${serie.description}",
                borderColor: 'rgb(75,192,192)',
                data: ${datas},
                }]
            },
            options: {}
        });
    </script>
</body>
</html>