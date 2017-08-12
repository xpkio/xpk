package io.xpk.service;

import io.xpk.web.obj.Message;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SubscriptionService {

  private final Map<String, List<SseEmitter>> subscriptions;

  public SubscriptionService() {
    subscriptions = new ConcurrentHashMap<>();
  }

  public void addSubscription(String type, String typeId, SseEmitter subscriber) {
    String key = type + "/" + typeId;

    if (!subscriptions.containsKey(key)) {
      subscriptions.put(key, Collections.synchronizedList(new ArrayList<>()));
    }
    subscriber.onCompletion(removeFromSubscriptions(subscriber, key));
    subscriber.onTimeout(removeFromSubscriptions(subscriber, key));
    subscriptions.get(key).add(subscriber);
  }

  @NotNull
  private Runnable removeFromSubscriptions(SseEmitter subscriber, String key) {
    return () -> subscriptions.get(key).remove(subscriber);
  }

  public void publishSubscription(String type, String typeId, Message message) {
    for(SseEmitter subscriber : subscriptions.get(type + "/" + typeId)) {
      publishToSubscriber(message, subscriber);
    }
  }

  private void publishToSubscriber(Message message, SseEmitter subscriber) {
    try {
      subscriber.send(SseEmitter.event().data(message, MediaType.APPLICATION_JSON).reconnectTime(600000).id(String.valueOf(message.getTime())));
    } catch (IOException e) {
      subscriber.completeWithError(e); // Catch Broken Pipes and other cases where it doesn't get removed in time
    }
  }
}
