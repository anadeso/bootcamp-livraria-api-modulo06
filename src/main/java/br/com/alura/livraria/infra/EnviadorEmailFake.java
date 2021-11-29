package br.com.alura.livraria.infra;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

@Service
@ActiveProfiles({"default", "test"})
public class EnviadorEmailFake implements EnviadorDeEmail {

    @Async
    public void enviarEmail(String destinatario, String assunto, String mensagem) {
        System.out.println("Enviando E-mail");

        System.out.println("Destinatario: " +destinatario);
        System.out.println("Assunto: " +assunto);
        System.out.println("Mensagem: " +mensagem);
    }
}
