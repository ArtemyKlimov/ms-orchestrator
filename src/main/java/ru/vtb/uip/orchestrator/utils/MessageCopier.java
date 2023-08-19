package ru.vtb.uip.orchestrator.utils;

import org.springframework.jms.support.converter.MessageType;

import javax.jms.*;
import java.util.Enumeration;

public class MessageCopier {

    public static Message copyMessage(Message original, Session session) throws JMSException {
        Message copy = createNewMessage(original, session);

        // Copy relevant properties from original to copy
        Enumeration<String> propertyNames = original.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = propertyNames.nextElement();
            Object propertyValue = original.getObjectProperty(propertyName);
            copy.setObjectProperty(propertyName, propertyValue);
        }

        // Copy content from the original message to the copy
        if (original instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) original;
            String text = textMessage.getText();
            ((TextMessage) copy).setText(text);
            textMessage.setJMSType(MessageType.TEXT.toString());
        } else if (original instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) original;
            long length = bytesMessage.getBodyLength();
            byte[] bytes = new byte[(int) length];
            bytesMessage.setJMSType(MessageType.BYTES.toString());
            bytesMessage.readBytes(bytes);
            ((BytesMessage) copy).writeBytes(bytes);
        }
        return copy;
    }

    private static Message createNewMessage(Message original, Session session) throws JMSException {
        if (original instanceof TextMessage) {
            return session.createTextMessage();
        } else if (original instanceof BytesMessage) {
            return session.createBytesMessage();
        }
        return session.createMessage();
    }
}