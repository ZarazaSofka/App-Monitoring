package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/logs")
public class LogsController {

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportLogs() {
        try {
            byte[] logsCsv = getLogsFromGraylog();

            if (logsCsv == null || logsCsv.length == 0) {
                log.warn("No logs found to export. Returning an empty CSV file.");
                logsCsv = new byte[0];
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logs.csv");
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.set(HttpHeaders.CONTENT_ENCODING, "UTF-8");

            log.info("Logs exported successfully.");
            return new ResponseEntity<>(logsCsv, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while exporting logs: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while exporting logs".getBytes(StandardCharsets.UTF_8));
        }
    }


    private byte[] getLogsFromGraylog() {
        String graylogApiUrl = "http://graylog:9000/api/search/universal/absolute/export";

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime thirtyMinutesAgo = now.minusMinutes(30);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String fromTime = thirtyMinutesAgo.format(formatter);
        String toTime = now.format(formatter);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(graylogApiUrl)
                .queryParam("query", "*")
                .queryParam("from", fromTime)
                .queryParam("to", toTime)
                .queryParam("limit", 100)
                .queryParam("fields", "timestamp,message");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setHost(InetSocketAddress.createUnresolved("spring-app", 8080));
        headers.setAccept(Collections.singletonList(MediaType.parseMediaType("text/csv")));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    uriBuilder.toUriString(), HttpMethod.GET, entity, byte[].class);
            log.info(Arrays.toString(Objects.requireNonNull(response.getBody())));
            log.info(response.getHeaders().getContentType() + " " + response.getStatusCode());
            log.info("Fetched logs from " + fromTime + " to " + toTime + " from Graylog successfully.");
            return response.getBody();
        } catch (Exception e) {
            log.error("Error occurred while fetching logs from Graylog: {}", e.getMessage(), e);
            return null;
        }
    }
}
