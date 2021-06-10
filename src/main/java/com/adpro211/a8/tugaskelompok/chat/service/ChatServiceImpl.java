package com.adpro211.a8.tugaskelompok.chat.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.chat.models.ChatMessage;
import com.adpro211.a8.tugaskelompok.chat.models.ChatRoom;
import com.adpro211.a8.tugaskelompok.chat.repository.ChatMessageRepository;
import com.adpro211.a8.tugaskelompok.chat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    ChatMessageRepository chatMessageRepository;
    ChatRoomRepository chatRoomRepository;

    public ChatServiceImpl(@Autowired ChatMessageRepository chatMessageRepository, @Autowired ChatRoomRepository chatRoomRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public ChatRoom createChatRoom(Account participantA, Account participantB) {
        ChatRoom room = chatRoomRepository.getChatRoomByParticipant(participantA, participantB);
        if ( room != null) return room;
        room = new ChatRoom();
        room.setParticipantA(participantA);
        room.setParticipantB(participantB);
        return chatRoomRepository.save(room);
    }

    @Override
    public ChatMessage sendMessage(Account sender, ChatRoom room, String message) {
        if (!room.isParticipant(sender)) return null;
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoom(room);
        chatMessage.setSender(sender);
        chatMessage.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
        chatMessage.setMessage(message);
        chatMessageRepository.save(chatMessage);
        room.setLastSentMessage(chatMessage);
        chatRoomRepository.save(room);
        return chatMessage;
    }

    @Override
    public List<ChatMessage> getMessageInRoom(ChatRoom room, Account currentAcc) {
        List<ChatMessage> chatMessages = room.getChatMessages();
        if (chatMessages.size() > 0) {
            ChatMessage lastMsg = room.getLastSentMessage();
            if (lastMsg.getSender() != currentAcc) {
                room.setLastSeenMessage(lastMsg);
                chatRoomRepository.save(room);
            }
        }
        return chatMessages;
    }

    @Override
    public ChatRoom getChatRoomById(long id) {
        return chatRoomRepository.getChatRoomById(id);
    }

    @Override
    public List<ChatRoom> getAllUserChatRoom(Account account) {
        return chatRoomRepository.findChatRoomsByParticipant(account);
    }
}
