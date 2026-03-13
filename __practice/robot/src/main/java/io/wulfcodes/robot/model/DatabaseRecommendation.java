package io.wulfcodes.robot.model;

import java.util.List;

public record DatabaseRecommendation(
    String databaseName,
    String databaseType, // e.g., Relational, Document, Key-Value, Search
    String primaryReason,
    List<String> pros,
    List<String> cons
) {
}