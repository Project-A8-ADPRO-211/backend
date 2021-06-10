package com.adpro211.a8.tugaskelompok.chat.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.chat.models.ChatMessage;
import com.adpro211.a8.tugaskelompok.chat.models.ChatRoom;

import java.util.List;

public interface ChatService {
    ChatMessage sendMessage(Account sender, ChatRoom room, String message);
    List<ChatRoom> getAllUserChatRoom(Account account);
    List<ChatMessage> getMessageInRoom(ChatRoom room, Account currentAcc);
    ChatRoom getChatRoomById(long id);
    ChatRoom createChatRoom(Account participantA, Account participantB);
}
