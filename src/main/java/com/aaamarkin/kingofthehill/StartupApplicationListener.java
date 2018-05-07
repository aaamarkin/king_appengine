package com.aaamarkin.kingofthehill;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {

            Resource resource = resourceLoader.getResource("classpath:firebase.json");
            InputStream serviceAccount = resource.getInputStream();

//            FileInputStream serviceAccount = new FileInputStream("firebase.json");

//            FileInputStream serviceAccount = new ClassPathResource("countries.xml").getFile();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://https://kingofthehill-202721.firebaseio.com/")
                    .build();
//
            FirebaseApp.initializeApp(options);

        } catch (Exception e) {

        }
    }
}
