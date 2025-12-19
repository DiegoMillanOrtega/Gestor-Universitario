package com.example.sigu.config.app;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GoogleDriveConfig {

    @Value("${google.drive.oauth-credentials}")
    private Resource oauthCredentials;

    @Value("${google.drive.tokens-dir}")
    private String tokensDir;

    @Bean
    public Drive googleDrive() throws IOException, GeneralSecurityException {

        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(),
                new InputStreamReader(oauthCredentials.getInputStream())
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport,
                GsonFactory.getDefaultInstance(),
                clientSecrets,
                List.of(DriveScopes.DRIVE_FILE)
        )
                .setDataStoreFactory(new FileDataStoreFactory(new File(tokensDir)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(8080)
                .setCallbackPath("/oauth2/callback").build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver)
                .authorize("user");

        return new Drive.Builder(
                httpTransport,
                GsonFactory.getDefaultInstance(),
                credential
        )
                .setApplicationName("SIGU-Drive-Manager")
                .build();
    }
}
