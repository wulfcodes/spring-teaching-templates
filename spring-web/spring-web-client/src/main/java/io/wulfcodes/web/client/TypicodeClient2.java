package io.wulfcodes.web.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import io.wulfcodes.web.model.Post;

@Service
public class TypicodeClient2 {

    @Autowired
    private RestClient restClient;

    public Post getPostById(Integer id) {
        return restClient.get()
                         .uri("/posts/{id}", id)
                         .retrieve()
                         .body(Post.class);
    }

    public List<Post> getPostsByUserId(Integer userId) {
        return restClient.get()
                         .uri(uriBuilder -> uriBuilder
                             .path("/posts")
                             .queryParam("userId", userId)
                             .build())
                         .retrieve()
                         .body(new ParameterizedTypeReference<List<Post>>() {
                         });
    }

    public Post createPost(Post newPost) {
        return restClient.post()
                         .uri("/posts")
                         .contentType(MediaType.APPLICATION_JSON)
                         .header(HttpHeaders.AUTHORIZATION, "Basic user:password")
                         .body(newPost)
                         .retrieve()
                         .body(Post.class);
    }

    public void updatePost(Integer id, Post updatedPost) {
        restClient.put()
                  .uri("/posts/{id}", id)
                  .body(updatedPost)
                  .retrieve()
                  .toBodilessEntity();
    }

    public void deletePost(Integer id) {
        restClient.delete()
                  .uri("/posts/{id}", id)
                  .retrieve()
                  .toBodilessEntity();
    }

}
