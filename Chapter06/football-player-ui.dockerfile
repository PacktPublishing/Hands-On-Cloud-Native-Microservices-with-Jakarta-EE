FROM docker.io/httpd

ADD football-player-ui/dist/football-player-ui/* /usr/local/apache2/htdocs/
