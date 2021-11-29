package br.com.alura.livraria.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

@Service
@ActiveProfiles("prod")
public class EnviadorEmail implements EnviadorDeEmail{

    @Autowired
    private JavaMailSender mailSender;

    @Async
    @Override
    public void enviarEmail(String destinatario, String assunto, String mensagem) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(destinatario);
        simpleMailMessage.setSubject(assunto);
        simpleMailMessage.setText(mensagem);

        mailSender.send(simpleMailMessage);
    }
}
