package com.adpro211.a8.tugaskelompok.chat.controller;
 
import com.adpro211.a8.tugaskelompok.chat.models.ChatMessage;
import com.adpro211.a8.tugaskelompok.chat.models.GetMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
@RestController
public class ChatController {
 
    private static final List<ChatMessage> messageStore = new ArrayList<>();
 
    @PostMapping("/sendMessage")
    public ResponseEntity<List<ChatMessage>> saveMessage(@RequestBody ChatMessage message) {
        log.debug(message);
        message.setId(messageStore.size() + 1);
        messageStore.add(message);
        return ResponseEntity.ok(messageStore);
    }
 
    @GetMapping("/getMessages")
    public ResponseEntity<List<ChatMessage>> getMessage(GetMessage input) throws InterruptedException {
        if (lastStoredMessage().isPresent() && lastStoredMessage().get().getId() > input.getId()) {
            List<ChatMessage> output = new ArrayList<>();
            for (int index = input.getId(); index < messageStore.size(); index++) {
                output.add(messageStore.get(index));
            }
            return ResponseEntity.ok(output);
        }
 
        return keepPolling(input);
    }
 
    private ResponseEntity<List<ChatMessage>> keepPolling(GetMessage input) throws InterruptedException {
        Thread.sleep(5000);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/getMessages?id=" + input.getId() + "&to=" + input.getTo()));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }
 
    private Optional<ChatMessage> lastStoredMessage() {
        return messageStore.isEmpty() ? Optional.empty() : Optional.of(messageStore.get(messageStore.size()-1));
    }
}