package io.xpk.service;

import io.xpk.web.obj.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionService {

  private final Map<String, List<SseEmitter>> subscriptions;

  public SubscriptionService() {
    subscriptions = new HashMap<>();
  }

  public void addSubscription(String type, String typeId, SseEmitter subscriber) {
    String key = type + "/" + typeId;

    if (!subscriptions.containsKey(key)) {
      subscriptions.put(key, new ArrayList<>());
    }
    subscriptions.get(key).add(subscriber);
    subscriber.onCompletion(() -> subscriptions.get(key).remove(subscriber));
  }

  public void publishSubscription(String type, String typeId, Message message) {
    for(SseEmitter subscriber : subscriptions.get(type + "/" + typeId)) {
      publishToSubscriber(message, subscriber);
    }
  }

  private void publishToSubscriber(Message message, SseEmitter subscriber) {
    try {
      subscriber.send(SseEmitter.event().data(message));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
