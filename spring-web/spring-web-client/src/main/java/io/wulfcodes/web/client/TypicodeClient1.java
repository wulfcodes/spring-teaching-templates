package io.wulfcodes.web.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import io.wulfcodes.web.model.Post;

@Service
public class TypicodeClient1 {

    @Autowired
    private RestTemplate restTemplate;

    public Post getPostById(Integer id) {
        return restTemplate.getForObject("/posts/{id}", Post.class, id);
    }

    public Post getPostByIdRaw(Integer id) {
        ResponseEntity<Post> responseEntity = restTemplate.exchange("/posts/{id}", HttpMethod.GET, null, Post.class, id);
        return responseEntity.getBody();
    }

    public List<Post> getPostsByUserId(Integer userId) {
        String url = UriComponentsBuilder.fromUriString("/posts")
                                         .queryParam("userId", userId)
                                         .build()
                                         .toUriString();
        return (List<Post>) restTemplate.getForObject(url, List.class);
    }

    public Post createPost(Post post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "Basic user:password");

        HttpEntity<Post> requestEntity = new HttpEntity<>(post, headers);
        return restTemplate.postForObject("/posts", requestEntity, Post.class);
    }

    public void updatePost(Integer id, Post post) {
        restTemplate.put("/posts/{id}", post, id);
    }

    public void updatePostRaw(Integer id, Post post) {
        HttpEntity<Post> requestEntity = new HttpEntity<>(post);

        ResponseEntity<Post> responseEntity = restTemplate.exchange(
            "/posts/{id}",
            HttpMethod.PUT,
            requestEntity,
            Post.class,
            id
        );

        System.out.println(responseEntity.getStatusCode());
    }

    public void deletePost(Integer id) {
        restTemplate.delete("/posts/{id}", id);
    }

}
