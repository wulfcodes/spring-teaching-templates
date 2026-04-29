package io.wulfcodes.ai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record AuthorStats(
    @JsonProperty("name") String name, 
    @JsonProperty("genre") String genre, 
    @JsonProperty("booksPublished") int booksPublished, 
    @JsonProperty("famousWorks") List<String> famousWorks) {
}
