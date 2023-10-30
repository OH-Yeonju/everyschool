package com.everyschool.chatservice.domain.chatroom.repository;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.everyschool.chatservice.domain.MongoSeq;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChatRoomQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ChatRoomQueryRepository chatRoomQueryRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;

    @DisplayName("로그인 한 유저가 속한 채팅방 목록 가져오기")
    @Test
    void findChatRooms() {
        //given
        ChatRoom savedChatRoom1 = chatRoomRepository.save(ChatRoom.builder().build());
        ChatRoomUser user1 = createChatRoomUser(savedChatRoom1, 1L, "1학년 2반 임우택(부)", 2);
        ChatRoomUser opponent1 = createChatRoomUser(savedChatRoom1, 2L, "선생님", 2);
        Chat user1SentMessage = createSentMessage(savedChatRoom1, user1, opponent1, "user1 sent message");
        Chat opponent1SentMessage = createSentMessage(savedChatRoom1, opponent1, user1, "opponent1 sent message");

        ChatRoom savedChatRoom2 = chatRoomRepository.save(ChatRoom.builder().build());
        ChatRoomUser user2 = createChatRoomUser(savedChatRoom2, 1L, "1학년 2반 신성주(부)", 2);
        ChatRoomUser opponent2 = createChatRoomUser(savedChatRoom2, 3L, "선생님", 2);
        Chat opponent2SentMessage = createSentMessage(savedChatRoom2, opponent2, user2, "opponent2 sent message");
        Chat user2SentMessage = createSentMessage(savedChatRoom2, user2, opponent2, "user2 sent message");
        //when
        List<ChatRoomListResponse> chatRooms = chatRoomQueryRepository.findChatRooms(user1.getUserId());
        for (ChatRoomListResponse chatRoom : chatRooms) {
            System.out.println("채팅방 id : " + chatRoom.getRoomId());
            System.out.println("채팅방 제목 : " + chatRoom.getRoomTitle());
            System.out.println("채팅방 마지막메세지 : " + chatRoom.getLastMessage());
            System.out.println();
        }
        //then
        assertThat(chatRooms.size()).isEqualTo(2);
        assertThat(chatRooms.get(0).getRoomTitle()).isEqualTo("1학년 2반 신성주(부)");
        assertThat(chatRooms.get(0).getLastMessage()).isEqualTo("user2 sent message");
        assertThat(chatRooms.get(1).getRoomTitle()).isEqualTo("1학년 2반 임우택(부)");
        assertThat(chatRooms.get(1).getLastMessage()).isEqualTo("opponent1 sent message");


    }

    private Chat createSentMessage(ChatRoom savedChatRoom, ChatRoomUser sender, ChatRoomUser receiver, String message) {
        sender.updateUpdateChat(message);
        receiver.updateUpdateChat(message);
        return chatRepository.save(Chat.builder()
                .id(MongoSeq.getSeq())
                .userId(sender.getUserId())
                .content(message)
                .isBad(false)
                .chatRoomId(savedChatRoom.getId())
                .build());
    }

    private ChatRoomUser createChatRoomUser(ChatRoom savedChatRoom1, long userId, String chatRoomTitle, int unreadCount) {
        return chatRoomUserRepository.save(ChatRoomUser.builder()
                .chatRoomTitle(chatRoomTitle)
                .socketTopic("CHATROOM_TOPIC")
                .userId(userId)
                .isAlarm(true)
                .unreadCount(unreadCount)
                .chatRoom(savedChatRoom1)
                .build());
    }

}