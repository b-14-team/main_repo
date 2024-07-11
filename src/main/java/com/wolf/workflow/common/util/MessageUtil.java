package com.wolf.workflow.common.util;

import java.util.Locale;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    private static MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    private final static Locale LOCALE = Locale.getDefault();

    public static String getMessage(String messageCd) {
        return messageSource.getMessage(messageCd, null, LOCALE);
    }

    public static String getMessage(String messageCd, Objects[] objects) {
        return messageSource.getMessage(messageCd, objects, LOCALE);
    }

}
