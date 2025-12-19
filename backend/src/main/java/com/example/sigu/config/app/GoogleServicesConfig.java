package com.example.sigu.config.app;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.tasks.TasksScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GoogleServicesConfig {
    @Value("${google.drive.oauth-credentials}")
    private Resource oauthCredentials;

    @Value("${google.tokens-dir}")
    private String tokensDir;

    // 1. Un solo Flow para TODO
    @Bean
    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() throws IOException, GeneralSecurityException {
        return new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                GoogleClientSecrets.load(GsonFactory.getDefaultInstance(),
                        new InputStreamReader(oauthCredentials.getInputStream())),
                // AQUÍ UNIMOS LOS SCOPES
                List.of(DriveScopes.DRIVE_FILE, TasksScopes.TASKS)
        )
                .setDataStoreFactory(new FileDataStoreFactory(new File(tokensDir)))
                .setAccessType("offline")
                .build();
    }


}
