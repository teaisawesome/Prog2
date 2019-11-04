<?php 

    $hackers = array(
    "MatyiBatfai"=>"",
    "NorbertBatfai"=>"",
    "DekanyRobert"=>"",
    "LovaszBotond"=>"",
    "ValakiMali"=>"",
    );

    $a = 0;
    
    foreach($hackers as $h => &$p)
    {
        $p = 5000000 + $a;

        $a += 200000;
    }

    arsort($hackers);

    foreach($hackers as $h => $p)
    {
        $time = 0;
        $time = $p;
        $hours = (int)($time / (1000*60*60));
        $minutes = (int)(($time - $hours*1000*60*60) / (1000*60));
        $seconds = (int)(($time - $hours*1000*60*60 - $minutes*1000*60) / 1000);
        echo "Hacker: " . $h . ", idő: " . $hours . " óra " . $minutes . " perc " . $seconds . " mp";
        echo "<br>";
    }
?>