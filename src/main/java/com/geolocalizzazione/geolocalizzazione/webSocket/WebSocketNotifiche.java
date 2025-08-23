package com.geolocalizzazione.geolocalizzazione.webSocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;



@Log4j2
@Component
public class WebSocketNotifiche extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        broadcast("🔵 Nuovo utente connesso: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        broadcast("🔴 Utente disconnesso: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        broadcast("💬 [" + session.getId() + "]: " + message.getPayload());
    }

    public void broadcast(Object payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);

            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    log.info("Inviato messaggio al client con id {}",s);
                    s.sendMessage(new TextMessage(json));
                }
            }
        } catch (Exception e) {
            log.error("Errore durante la comunicazione con i client");
            e.printStackTrace();
        }
    }
}
