<?php
// Javascript hat keinen KeyCode für links / rechtsklick
// Darum convention: 
// Linksklick = 1
// Rechtsklick = 2

class Confirmation
{
    public $last_frame_renderd_ID;
}

$obj = new Confirmation;
$obj->last_frame_renderd_ID = $_GET["last_frame_renderd_ID"];


$myJSON = json_encode($obj);

$fp = fopen('renderedFrame.json', 'w');
fwrite($fp, $myJSON);
fclose($fp);
?>
