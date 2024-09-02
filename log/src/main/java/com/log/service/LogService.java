package com.log.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogService {
    private final String logFilePath = "application.log";

    public ResponseEntity<String> getLogs() {
        try {
            log.info("Reading logs from file: {}", logFilePath);
            return ResponseEntity.ok(Files.readString(Paths.get(logFilePath)));
        } catch (IOException e) {
            log.error("Unable to read logs from file: {}", logFilePath, e);
            return ResponseEntity.status(500).body("Unable to read logs.");
        }
    }
}
