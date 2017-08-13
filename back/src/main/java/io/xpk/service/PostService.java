package io.xpk.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xpk.domain.obj.Post;
import io.xpk.domain.obj.Spot;
import io.xpk.domain.obj.XpkUser;
import io.xpk.domain.repo.PostRepository;
import io.xpk.domain.repo.SpotRepository;
import io.xpk.domain.repo.XpkUserRepository;
import io.xpk.web.obj.NewPost;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class PostService extends TextWebSocketHandler {

  private final Map<Long, List<WebSocketSession>> sessionsPerSpotId;
  private final ObjectMapper objectMapper;
  private final PostRepository postRepository;
  private final SpotRepository spotRepository;
  private final XpkUserRepository xpkUserRepository;

  public PostService(ObjectMapper objectMapper, PostRepository postRepository, SpotRepository spotRepository, XpkUserRepository xpkUserRepository) {
    this.objectMapper = objectMapper;
    this.postRepository = postRepository;
    this.spotRepository = spotRepository;
    this.xpkUserRepository = xpkUserRepository;
    sessionsPerSpotId = new HashMap<>();
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) {
      NewPost newPost = fromMessage(message);
      Post post = postRepository.save(newPostToPost(newPost));

      newPost.getSpots()
          .stream()
          .map(sessionsPerSpotId::get)
          .flatMap(List::stream)
          .forEach((webSocketSession -> sendMessage(post, webSocketSession)));
  }

  private NewPost fromMessage(TextMessage message) {
    log.debug("Received message: " + message.getPayload());
    try {
      return objectMapper.readValue(message.getPayload(), NewPost.class);
    } catch (IOException e) {
      log.warn("Caught IOException while receiving text message!", e);
      throw new UncheckedIOException(e);
    }
  }

  private void sendMessage(Post post, WebSocketSession webSocketSession) {
    try {
      webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(post)));
    } catch (IOException e) {
      log.warn("Caught IOException while sending text message!", e);
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    Long spotId = getSpotId(session);
    sessionsPerSpotId.putIfAbsent(spotId, new CopyOnWriteArrayList<>());
    sessionsPerSpotId.get(spotId).add(session);
    log.debug("Connection opened!");
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.debug("Connection Closed: " + status.toString());
    sessionsPerSpotId.get(getSpotId(session)).remove(session);
  }

  private Long getSpotId(WebSocketSession session) {
    return Long.valueOf(session.getUri().getPath().substring(6));
  }

  private Post newPostToPost(NewPost newPost) {
    Post post = new Post();
    post.setBody(newPost.getBody());
    post.setAuthor(getXpkUserFromAuthorId(newPost));
    post.setCreateDate(OffsetDateTime.now());
    post.setSpots(getSpotsFromIds(newPost.getSpots()));
    return post;
  }

  @NotNull
  private XpkUser getXpkUserFromAuthorId(NewPost newPost) {
    return xpkUserRepository.findOne(newPost.getAuthorId());
  }

  private Set<Spot> getSpotsFromIds(Set<Long> spots) {
    return spotRepository.findByIdIn(spots);
  }
}
