package io.lamden.api;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MasterNode {

    private URI uri;
    private int retriesUntilUnhealthy;
    private List<LocalDateTime> unhealthyReports = new ArrayList<>();
    private int healthCheckTimeSpanSeconds;
    private boolean healthy;

    public MasterNode(URI uri, int retriesUntilUnhealthy, int healthCheckTimeSpanSeconds) {
        this.uri = uri;
        this.retriesUntilUnhealthy = retriesUntilUnhealthy;
        this.healthCheckTimeSpanSeconds = healthCheckTimeSpanSeconds;
        this.healthy = true;
    }

    public URI getUri() {
        return uri;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void reportUnhealthy() {

        unhealthyReports = unhealthyReports.stream()
                .filter(u -> u.isAfter(LocalDateTime.now()
                        .minus(healthCheckTimeSpanSeconds, ChronoUnit.SECONDS)))
                .collect(Collectors.toList());

        unhealthyReports.add(LocalDateTime.now());

        if (unhealthyReports.size() > retriesUntilUnhealthy){
            this.healthy = false;
            log.warn("Masternode with URL " + this.uri.toString() + " was marked as unhealthy. No more requests will be sent to this masternode.");
        }
    }
}
