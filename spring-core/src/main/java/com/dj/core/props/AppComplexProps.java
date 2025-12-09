package com.dj.core.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "app.complex")
public class AppComplexProps {

    private Optional<String> additionalInfo;
    private List<String> externalServers;
    private List<String> internalServers;
    private Map<String, Integer> stats;
    private Duration timeout;
    private DataSize fileSize;
    private URL domain;

    public List<String> getExternalServers() {
        return externalServers;
    }

    public void setExternalServers(List<String> externalServers) {
        this.externalServers = externalServers;
    }

    public List<String> getInternalServers() {
        return internalServers;
    }

    public void setInternalServers(List<String> internalServers) {
        this.internalServers = internalServers;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public DataSize getFileSize() {
        return fileSize;
    }

    public void setFileSize(DataSize fileSize) {
        this.fileSize = fileSize;
    }

    public Optional<String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Optional<String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public URL getDomain() {
        return domain;
    }

    public void setDomain(URL domain) {
        this.domain = domain;
    }
}
