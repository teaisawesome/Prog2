
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


input[type=text], select {
  width: 100%;
  display: inline-block;
  border: 1px solid black;
  font-size: 16px;
  border-radius: 5px;
      padding: 8px 16px;
}
input[type=password], select {
  width: 100%;
  display: inline-block;
  border: 1px solid black;
  font-size: 16px;
  border-radius: 5px;
      padding: 8px 16px;

}

input[type=submit], select {
  background-color: black;
  color: white;
  border-radius: 5px;
  font-size: 16px;
      padding: 8px 16px;

}

input[type=submit]:hover {
  background-color: white;
  color: black;
}

</style>



</head>
<body>

<p id="ido">Streameljünk 100 óra DEAC játékot!</p>
<img  src = "http://deac.hu/upload/5b376e152b0b5.png"  width="300">
<p id="hanyan">Hány DEAC-Hackers streamel?</p>


<form action="deac100.php" method="POST">
    <label for="hackername">DEAC-Hacker:</label>
    <input type="text" name="hackername" placeholder="Hacker neved...">
    <label for="hackercode">Hacker kód:</label>
        <input type="password" name="hackercode" placeholder="Mailben kapott kódod...">
    <label for="hackerchannel">Twitch csatorna:</label>
    <input type="text" name="hackerchannel" placeholder="https://www.twitch.tv/nbatfai">
    <br>
    <input type="submit" value="ÉLŐ ADÁS"> Kérj hacker kódot: Dr. Bátfai Norbert, <a href="mailto:batfai.norbert@inf.unideb.hu">batfai.norbert@inf.unideb.hu</a>
  </form>

</body>
</html>