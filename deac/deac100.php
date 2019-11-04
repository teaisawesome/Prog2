<?php
session_start();
if(!(isset($_SESSION["hackerin"]) && $_SESSION["hackerin"] == true))
{
header("location: index.php");
exit;
}
$hackername = $_SESSION["hackername"];
$hackertime = $_SESSION["hackertime"];
$time = $_GET["time"];
if(!empty($time))
{
$hackertime = $time;
$hackerfile = "/tmp/".$_SESSION["hackername"];
file_put_contents($hackerfile, $time);
}
?>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<style>
p {
margin-top: 0px;
text-align: center;
font-size: 55px;
font-variant: small-caps;
}
img {
display: block;
margin-left: auto;
margin-right: auto;
}
</style>
</head>
<body>
<p id="ido">Streameljünk 100 óra DEAC játékot!</p>
<img src = "http://deac.hu/upload/5b376e152b0b5.png" width="300">
<p id="hanyan" class="smallcap">Hány DEAC-Hackers streamel?</p>
<div align="center">
Hajrá, <?php echo $hackername; ?>!
</div>
<div align="center">
<button onclick="refresh100()">FRISSÍT</button>
<button onclick="stop100()">LEÁLLÍT</button>
</div>
<div align="center">
<?php
$hackers = array(
"MatyiBatfai"=>" ",
"NorbertBatfai"=>" ",
"BotondLovasz"=>" ",
"LajosNagy"=>" ",
"RobiDekany"=>" "
);
foreach($hackers as $h => &$p)
{
    $hackerfile = "/tmp/".$h;
    $time = 0;
    if(file_exists($hackerfile))
    $time = file_get_contents($hackerfile);
    $p = $time;
}

arsort($hackers);

foreach($hackers as $h => $p)
{
    $hours = (int)($p / (1000*60*60));
    $minutes = (int)(($p - $hours*1000*60*60) / (1000*60));
    $seconds = (int)(($p - $hours*1000*60*60 - $minutes*1000*60) / 1000);
    echo "Hacker: " . $h . ", idő: " . $hours . " óra " . $minutes . " perc " . $seconds . " mp";
    echo "<br>";
}
?>
</div>
<script>
var limit = 1000*60*60*100;
var limit = 1000*60*60*100;
var refreshlimit = 1000*60*5;
var nofhackers = 1;
var time = <?php echo $hackertime; ?>;
var localtime = 0;
setInterval(function() {
time = time + nofhackers*1000;
localtime = localtime + 1000;
var hours = Math.floor(time / (1000*60*60));
var minutes = Math.floor((time - hours*1000*60*60) / (1000*60));
var seconds = Math.floor((time - hours*1000*60*60 - minutes*1000*60) / 1000);
document.getElementById("ido").innerHTML = hours + " óra "
+ minutes + " perc " + seconds + " mp ";
if(nofhackers==1)
document.getElementById("hanyan").innerHTML = nofhackers + " hacker streamel";
else
document.getElementById("hanyan").innerHTML = nofhackers + " hacker streamel párhuzamosan";
if (time >= limit) {
document.getElementById("ido").innerHTML = "A 100 óra stream teljesítve!";
}
if(localtime >= refreshlimit){
localtime = 0;
refresh100();
}
}, 1000);
function stop100() {
window.location.href = "stop.php?time=" + time;
}
function refresh100() {
window.location.href = "100.php?time=" + time;
}
</script>
</body>
</html>