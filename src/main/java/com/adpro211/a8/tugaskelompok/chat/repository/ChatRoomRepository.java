package com.adpro211.a8.tugaskelompok.chat.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.chat.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("from ChatRoom where (participantA = ?1 and participantB = ?2) or (participantA = ?2 and participantB = ?1)")
    ChatRoom getChatRoomByParticipant(Account participantA, Account participantB);

    @Query("from ChatRoom where participantA = ?1 or participantB = ?1")
    List<ChatRoom> findChatRoomsByParticipant(Account participant);

    ChatRoom getChatRoomById(long id);
}
