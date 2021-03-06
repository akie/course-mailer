package com.odde.mailsender.service;

import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Value("${smtp.host}")
    private String mailHost;
    @Value("${smtp.port}")
    private Integer smtpPort;
    @Value("${smtp.ssl.port}")
    private String sslSmtpPort;
    @Value("${smtp.ssl.enable}")
    private Boolean sslEnable;
    private static final String CHARSET = "ISO-2022-JP";
    private static final String SENDER_NAME = "gadget.mailsender";
    private static final String PASSWORD = "mailsender";

    @Override
    public void send(MailInfo mailInfo) throws Exception {
        SimpleEmail simpleEmail = new SimpleEmail();

        simpleEmail.setHostName(mailHost);
        if (sslEnable) {
            simpleEmail.setSslSmtpPort(sslSmtpPort);
            simpleEmail.setSSL(true);
        } else {
            simpleEmail.setSmtpPort(smtpPort);
        }

        simpleEmail.setCharset(CHARSET);
        simpleEmail.setAuthentication(SENDER_NAME, PASSWORD);

        simpleEmail.setFrom(mailInfo.getFrom());
        simpleEmail.setSubject(mailInfo.getSubject());
        simpleEmail.setMsg(mailInfo.getBody());
        simpleEmail.addTo(mailInfo.getTo());
        simpleEmail.send();
    }

    public void sendMultiple(List<MailInfo> mailInfoList) throws Exception {
        for (MailInfo mailInfo : mailInfoList) {
            send(mailInfo);
        }
    }

}
