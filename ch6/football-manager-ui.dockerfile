FROM docker.io/httpd

ADD football-manager-ui/dist/football-manager-ui/* /usr/local/apache2/htdocs/
