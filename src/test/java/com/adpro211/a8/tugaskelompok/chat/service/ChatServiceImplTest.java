package com.adpro211.a8.tugaskelompok.chat.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.chat.models.ChatMessage;
import com.adpro211.a8.tugaskelompok.chat.models.ChatRoom;
import com.adpro211.a8.tugaskelompok.chat.repository.ChatMessageRepository;
import com.adpro211.a8.tugaskelompok.chat.repository.ChatRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @InjectMocks
    ChatServiceImpl chatService;

    @Mock
    ChatRoomRepository chatRoomRepository;

    @Mock
    ChatMessageRepository chatMessageRepository;

    Account accountA;
    Account accountB;
    Account accountC;

    @Mock
    ChatRoom defaultRoom;

    List<ChatRoom> chatRoomList = new ArrayList<>();
    List<ChatMessage> chatRoomMsg;

    @BeforeEach
    void setUp() {
        chatService = new ChatServiceImpl(chatMessageRepository, chatRoomRepository);

        accountA = new Seller();
        accountA.setId(1);
        accountA.setEmail("a@test.com");
        accountA.setName("Account A");

        accountB = new Seller();
        accountB.setId(2);
        accountB.setEmail("b@test.com");
        accountB.setName("Account B");

        accountC = new Seller();
        accountC.setId(3);
        accountC.setEmail("c@test.com");
        accountC.setName("Account C");


        defaultRoom = new ChatRoom();
        defaultRoom.setParticipantA(accountA);
        defaultRoom.setParticipantB(accountB);
        defaultRoom.setId(1);

        chatRoomList.add(defaultRoom);
        chatRoomMsg = new ArrayList<>();
    }

    @Test
    void createChatRoomNotExist() {
        when(chatRoomRepository.getChatRoomByParticipant(any(), any())).thenReturn(null);
        when(chatRoomRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ChatRoom room = chatService.createChatRoom(accountA, accountB);

        assertEquals(room.getParticipantA(), accountA);
        assertEquals(room.getParticipantB(), accountB);
        assertNull(room.getLastSeenMessage());
        assertNull(room.getLastSentMessage());

        verify(chatRoomRepository, times(1)).save(any());

    }

    @Test
    void createChatRoomAlreadyExist() {
        when(chatRoomRepository.getChatRoomByParticipant(any(), any())).thenReturn(defaultRoom);

        ChatRoom room = chatService.createChatRoom(accountA, accountB);

        assertEquals(room, defaultRoom);

        verify(chatRoomRepository, times(0)).save(any());

    }

    @Test
    void sendMessageSuccess() {

        when(chatMessageRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ChatMessage prevMsg = defaultRoom.getLastSeenMessage();
        ChatMessage message = chatService.sendMessage(accountA, defaultRoom, "Test");

        assertEquals(message.getMessage(), "Test");
        assertEquals(message.getRoom(), defaultRoom);
        assertEquals(message.getSender(), accountA);
        assertThat(message.getTimeStamp()).isBefore(Instant.now()).isAfter(Instant.now().minus(1, ChronoUnit.MINUTES));

        assertEquals(defaultRoom.getLastSentMessage(), message);
        assertEquals(defaultRoom.getLastSeenMessage(), prevMsg);

        verify(chatRoomRepository, times(1)).save(defaultRoom);
        verify(chatMessageRepository, times(1)).save(any());

    }

    @Test
    void sendMessageNotAMember() {

        ChatMessage prevMsg = defaultRoom.getLastSeenMessage();
        ChatMessage lastSentMsg = defaultRoom.getLastSentMessage();
        ChatMessage message = chatService.sendMessage(accountC, defaultRoom, "Test");

        assertNull(message);

        assertEquals(defaultRoom.getLastSentMessage(), lastSentMsg);
        assertEquals(defaultRoom.getLastSeenMessage(), prevMsg);

        verify(chatRoomRepository, times(0)).save(defaultRoom);
        verify(chatMessageRepository, times(0)).save(any());

    }

    @Test
    void getMessageInRoomEmpty() {
        chatRoomMsg.clear();
        defaultRoom.setChatMessages(chatRoomMsg);

        assertEquals(chatService.getMessageInRoom(defaultRoom, accountA).size(), 0);
    }

    @Test
    void getMessageInRoomLastMessageSentByUs() {
        chatRoomMsg.clear();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(accountA);
        chatMessage.setId(1);
        chatMessage.setMessage("Test");
        chatMessage.setRoom(defaultRoom);
        chatMessage.setTimeStamp(Timestamp.from(Instant.now()));
        chatRoomMsg.add(chatMessage);
        defaultRoom.setLastSentMessage(chatMessage);
        defaultRoom.setChatMessages(chatRoomMsg);

        assertNull(defaultRoom.getLastSeenMessage());
        chatService.getMessageInRoom(defaultRoom, accountA);
        assertNull(defaultRoom.getLastSeenMessage());
    }

    @Test
    void getMessageInRoomLastMessageNotSentByUs() {
        chatRoomMsg.clear();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(accountB);
        chatMessage.setMessage("Test");
        chatMessage.setRoom(defaultRoom);
        chatMessage.setTimeStamp(Timestamp.from(Instant.now()));
        chatRoomMsg.add(chatMessage);
        defaultRoom.setLastSentMessage(chatMessage);
        defaultRoom.setChatMessages(chatRoomMsg);

        assertNull(defaultRoom.getLastSeenMessage());
        chatService.getMessageInRoom(defaultRoom, accountA);
        assertEquals(defaultRoom.getLastSeenMessage(), chatMessage);
    }

    @Test
    void getChatRoomById() {
        when(chatRoomRepository.getChatRoomById(anyLong())).thenReturn(defaultRoom);

        assertEquals(chatService.getChatRoomById(1), defaultRoom);
        verify(chatRoomRepository, atLeastOnce()).getChatRoomById(anyLong());
    }

    @Test
    void getAllUserChatRoom() {
        when(chatRoomRepository.findChatRoomsByParticipant(accountA)).thenReturn(chatRoomList);

        assertArrayEquals(chatService.getAllUserChatRoom(accountA).toArray(), chatRoomList.toArray());
        verify(chatRoomRepository, atLeastOnce()).findChatRoomsByParticipant(accountA);
    }

    @Test
    void testIsParticipant() {
        assertTrue(defaultRoom.isParticipant(accountA));
        assertTrue(defaultRoom.isParticipant(accountB));
        assertFalse(defaultRoom.isParticipant(accountC));
        assertEquals(defaultRoom.getId(), 1);
    }
}