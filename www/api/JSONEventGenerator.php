<?php
// Javascript hat keinen KeyCode für links / rechtsklick
// Darum convention: 
// Linksklick = 1
// Rechtsklick = 2

class Event
{
    public $keycode;
    public $x;
    public $y;
}

$event = new event;
$event->keycode = $_GET["keycode"];
$event->x = $_GET["x"];
$event->y = $_GET["y"];

$myJSON = json_encode($event);

$fp = fopen('issueEvent.json', 'w');
fwrite($fp, $myJSON);
fclose($fp);
?>
