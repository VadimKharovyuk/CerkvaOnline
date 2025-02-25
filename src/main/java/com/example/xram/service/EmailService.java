package com.example.xram.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendSubscriptionConfirmation(String to) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("vadimkh17@gmail.com");
        helper.setTo(to);
        helper.setSubject("Подтверждение подписки");

        String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                   <style>
                       body {
                           background-color: #111827;
                           margin: 0;
                           padding: 0;
                           font-family: 'Arial', sans-serif;
                           color: #e5e7eb;
                       }
                       .container {
                           max-width: 600px;
                           margin: 0 auto;
                           padding: 40px 20px;
                       }
                       .header {
                           background-color: #1f2937;
                           border: 2px solid #fbbf24;
                           color: #fbbf24;
                           padding: 30px;
                           text-align: center;
                           border-radius: 15px;
                           margin-bottom: 30px;
                           box-shadow: 0 4px 6px rgba(251, 191, 36, 0.1);
                       }
                       .header h1 {
                           margin: 0;
                           font-size: 28px;
                           text-transform: uppercase;
                           letter-spacing: 1px;
                       }
                       .content {
                           background-color: #1f2937;
                           padding: 30px;
                           line-height: 1.8;
                           border-radius: 15px;
                           border: 1px solid #374151;
                       }
                       .content p {
                           margin-bottom: 20px;
                           font-size: 16px;
                       }
                       .features {
                           background-color: #111827;
                           border-radius: 10px;
                           padding: 20px;
                           margin: 20px 0;
                       }
                       .feature-item {
                           display: flex;
                           align-items: center;
                           margin-bottom: 15px;
                           padding: 15px;
                           background-color: #1f2937;
                           border-radius: 8px;
                           border: 1px solid #fbbf24;
                       }
                       .feature-icon {
                           width: 40px;
                           height: 40px;
                           background-color: #fbbf24;
                           border-radius: 50%;
                           display: flex;
                           align-items: center;
                           justify-content: center;
                           margin-right: 15px;
                           font-size: 20px;
                           color: #1f2937;
                       }
                       .feature-text {
                           color: #e5e7eb;
                           font-size: 16px;
                       }
                       .button {
                           display: inline-block;
                           background-color: #fbbf24;
                           color: #1f2937;
                           padding: 15px 30px;
                           text-decoration: none;
                           border-radius: 8px;
                           font-weight: bold;
                           text-transform: uppercase;
                           letter-spacing: 1px;
                           margin-top: 30px;
                           transition: all 0.3s ease;
                       }
                       .button:hover {
                           background-color: #f59e0b;
                           transform: translateY(-2px);
                       }
                       .footer {
                           text-align: center;
                           margin-top: 40px;
                           padding-top: 20px;
                           border-top: 1px solid #374151;
                           color: #9ca3af;
                       }
                   </style>
                </head>
                <body>
                   <div class="container">
                       <div class="header">
                           <h1>🙏 Добро пожаловать в нашу духовную семью! 🙏</h1>
                       </div>
                       <div class="content">
                           <p>Дорогой брат/сестра во Христе!</p>
                           <p>Мы искренне рады приветствовать Вас в нашем духовном сообществе. Благодарим за подписку на наши новости и обновления.</p>
                          \s
                           <div class="features">
                               <div class="feature-item">
                                   <div class="feature-icon">📅</div>
                                   <div class="feature-text">Расписание служб и церковных мероприятий</div>
                               </div>
                               <div class="feature-item">
                                   <div class="feature-icon">✝️</div>
                                   <div class="feature-text">Важные православные праздники и их значение</div>
                               </div>
                               <div class="feature-item">
                                   <div class="feature-icon">📖</div>
                                   <div class="feature-text">Духовные материалы и проповеди</div>
                               </div>
                               <div class="feature-item">
                                   <div class="feature-icon">🕊️</div>
                                   <div class="feature-text">Особые молитвенные прошения и благословения</div>
                               </div>
                           </div>
                
                           <p>Теперь вы всегда будете в курсе важных событий нашей церковной жизни и сможете принимать активное участие в жизни общины.</p>
                          \s
                           <a href="http://your-site.com" class="button">Посетить наш сайт</a>
                       </div>
                      \s
                       <div class="footer">
                           <p>С молитвой и благословением,</p>
                           <p>Храм Онлайн</p>
                       </div>
                   </div>
                </body>
                </html>
            """;

        helper.setText(htmlContent, true); // true указывает, что это HTML

        mailSender.send(mimeMessage);
    }
}