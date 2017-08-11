package io.xpk.web.endpoint;

import io.xpk.service.SubscriptionService;
import io.xpk.web.obj.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  public SubscriptionController(SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
  }

  @GetMapping("/{type}/{typeId}")
  public SseEmitter subscribe(@PathVariable String type, @PathVariable String typeId) {
    SseEmitter subscriber = new SseEmitter();
    subscriptionService.addSubscription(type, typeId, subscriber);
    return subscriber;
  }

  @PostMapping("/{type}/{typeId}")
  public void publish(@PathVariable String type, @PathVariable String typeId, @RequestBody Message message) {
    subscriptionService.publishSubscription(type, typeId, message);
  }
}