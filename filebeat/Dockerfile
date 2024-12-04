FROM elastic/filebeat:7.17.24
USER root
COPY filebeat.yml /usr/share/filebeat/filebeat.yml
RUN chmod go-w /usr/share/filebeat/filebeat.yml
RUN filebeat modules enable postgresql