<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        echo "<h2>TCP/IP Connection</h2>\n";

            /* Get the port for the WWW service. */
            $service_port = 4040;

            /* Get the IP address for the target host. */
            $address = gethostbyname('foodinventory.halt.cz');
            //$address = gethostbyname('localhost');

            /* Create a TCP/IP socket. */
            $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
            if ($socket === false) {
                echo "socket_create() failed: reason: " . socket_strerror(socket_last_error()) . "\n";
            } else {
                echo "OK.\n";
            }

            echo "Attempting to connect to '$address' on port '$service_port'...";
            $result = socket_connect($socket, $address, $service_port);
            if ($result === false) {
                echo "socket_connect() failed.\nReason: ($result) " . socket_strerror(socket_last_error($socket)) . "\n";
            } else {
                echo "OK.\n";
            }

            $in = "{\"header\" : \"LoginRequest\", \"content\": {\"username\" : \"tester_from_hell@seznam.cz\"}}";
            $out = '';

            echo "Sending HTTP HEAD request...";
            
            $len = strlen($in);
            
            $buffer = pack('N', $len);
            
            var_dump($buffer);
            
            socket_write($socket, $buffer, strlen($buffer));
            
            socket_write($socket, $in, strlen($in));
            echo "OK.\n";

            echo "Reading response:\n\n";
            
            $reslen = unpack("N", socket_read($socket, 4))[1];
            var_dump($reslen);
            $res = json_decode(socket_read($socket, $reslen));
            var_dump($res);
            

            echo "Closing socket...";
            socket_close($socket);
            echo "OK.\n\n";
        ?>
    </body>
</html>
