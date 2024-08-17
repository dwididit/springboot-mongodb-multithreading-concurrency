package dev.dwidi.springbootmongodbmultithreadingconcurrency.notification;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.exceptions.EmailSendFailedException;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * The EmailServiceImpl class is an implementation of the EmailService interface.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final Resend resend;
    private final UserRepository userRepository;
    private final MustacheFactory mustacheFactory;


    @Autowired
    public EmailServiceImpl(@Value("${resend.api.key}") String apiKey, Resend resend, UserRepository userRepository, MustacheFactory mustacheFactory) {
        this.resend = resend;
        this.userRepository = userRepository;
        this.mustacheFactory = mustacheFactory;
    }

    @Override
    public CompletableFuture<Void> sendEmail(String to, String from, String subject, String body) {
        return CompletableFuture.runAsync(() -> {
            logger.info("Sending email on thread: {}", Thread.currentThread().getName());

            // Fetch user's name from the database
            String username = userRepository.findUsernameByEmail(to);

            // Prepare model data for Mustache template
            ContentDTO contentDTO = new ContentDTO(username, to, "123-456-7890");

            // Create a map from the ContentDTO for Mustache
            Map<String, Object> model = new HashMap<>();
            model.put("username", contentDTO.getUsername());
            model.put("email", contentDTO.getEmail());
            model.put("phoneNumber", contentDTO.getPhoneNumber());

            // Render the template using MustacheFactory
            String htmlContent = null;
            try {
                Mustache mustache = mustacheFactory.compile("templates/welcome-email.mustache");
                StringWriter writer = new StringWriter();
                mustache.execute(writer, model).flush();
                htmlContent = writer.toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed to render email template", e);
            }

            // Create email options using Resend
            CreateEmailOptions createEmailOptions = CreateEmailOptions.builder()
                    .from("Didit From DWIDI.DEV <" + from + ">")
                    .to(to)
                    .subject(subject)
                    .html(htmlContent)
                    .build();

            try {
                CreateEmailResponse response = resend.emails().send(createEmailOptions);
                logger.info("Email successfully sent to: {} with ID: {}", to, response.getId());
            } catch (ResendException e) {
                throw new EmailSendFailedException("Failed to send email", e);
            }
        });
    }
}
