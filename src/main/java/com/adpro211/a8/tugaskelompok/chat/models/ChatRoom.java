package com.adpro211.a8.tugaskelompok.chat.models;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.product.model.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Account participantA;

    @ManyToOne
    private Account participantB;

    @OneToOne
    private ChatMessage lastSeenMessage;

    @OneToOne
    private ChatMessage lastSentMessage;

    @OneToMany(mappedBy = "room", cascade=CascadeType.REMOVE)
    @JsonIgnore
    private List<ChatMessage> chatMessages;

    public boolean isParticipant(Account account) {
        return account == participantA || account == participantB;
    }

}
