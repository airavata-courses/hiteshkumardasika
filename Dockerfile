FROM httpd:2.4
COPY ./*.html /usr/local/apache2/htdocs/
COPY ./*js /usr/local/apache2/htdocs/

