package com.adpro211.a8.tugaskelompok.chat.controller;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireLoggedIn;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.chat.models.ChatMessage;
import com.adpro211.a8.tugaskelompok.chat.models.ChatRoom;
import com.adpro211.a8.tugaskelompok.chat.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/chat")
public class ChatController {

    @Autowired
    AccountService accountService;

    @Autowired
    ChatService chatService;

    @Getter
    @AllArgsConstructor
    static class MsgListResponse {
        private List<ChatMessage> chatMessageList;
        private ChatMessage lastReadChatMessage;
    }

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<List<ChatRoom>> getChatRoomUser(@RequireLoggedIn Account account) {
        return ResponseEntity.ok(chatService.getAllUserChatRoom(account));
    }

    @GetMapping(produces = {"application/json"}, path = "/{id}")
    @ResponseBody
    public ResponseEntity<MsgListResponse> getChatRoom(@PathVariable long id, @RequireLoggedIn Account account, @RequestParam(required = false) Boolean visible) {
        ChatRoom room = chatService.getChatRoomById(id);
        if (room == null || !room.isParticipant(account)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (visible) {
            return ResponseEntity.ok(new MsgListResponse(chatService.getMessageInRoom(room, account), room.getLastSeenMessage()));
        } else {
            return ResponseEntity.ok(new MsgListResponse(room.getChatMessages(), room.getLastSeenMessage()));
        }
    }

    @PostMapping(produces = {"application/json"}, path = "/{id}")
    @ResponseBody
    public ResponseEntity<ChatMessage> postChatMsg(@PathVariable long id, @RequireLoggedIn Account account, @RequestBody String message) {
        ChatRoom room = chatService.getChatRoomById(id);
        if (room == null || !room.isParticipant(account)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(chatService.sendMessage(account, room, message));
    }

    @PostMapping(produces = {"application/json"}, path = "/createRoom/{uid}")
    @ResponseBody
    public ResponseEntity<ChatRoom> postChatRoom(@PathVariable int uid, @RequireLoggedIn Account account) {
        Account dest = accountService.getAccountById(uid);
        return ResponseEntity.ok(chatService.createChatRoom(account, dest));
    }
}
