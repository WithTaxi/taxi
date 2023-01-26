package com.withtaxi.taxi.service;


import com.withtaxi.taxi.model.User;
import com.withtaxi.taxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public static String ePw = null;

    private MimeMessage createMessage(String to) throws Exception {
        ePw = createKey();
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + ePw);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to); // 보내는 대상
        message.setSubject("WithTaxi 대학교 인증 메일입니다");

        String msgg = "";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 WithTaxi 입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("kkm23125291@gmail.com","WithTaxi")); //보내는 사람

        return message;
    }

    /***
     * 인증번호 구현 createkey()
     * @return
     */
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        return ePw;
    }

    @Override
    public int issuedTemporaryPassword(String userId) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            return 0;
        }

        try {
            String temporaryPassword = sendSimpleMessage(user.getEmail());

            String encPassword = passwordEncoder.encode(temporaryPassword);
            user.setPassword(encPassword);

            userRepository.save(user);

            return 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
