package com.example.sigu.service.implementation.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.drive.Drive;
import com.google.api.services.tasks.Tasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleServiceProvider {


    private final GoogleAuthorizationCodeFlow flow;
    private final String USER_ID = "user";

    public Drive getDriveService() throws IOException {
        Credential credential = flow.loadCredential(USER_ID);
        if (credential == null) {
            throw new RuntimeException("No autorizado. Ve a /api/oauth/login");
        }
        return new Drive.Builder(flow.getTransport(), flow.getJsonFactory(), credential)
                .setApplicationName("SIGU")
                .build();
    }

    public Tasks getTasksService() throws IOException {
        Credential credential = flow.loadCredential(USER_ID);
        if (credential == null) {
            throw new RuntimeException("No autorizado. Ve a /api/oauth/login");
        }
        return new Tasks.Builder(flow.getTransport(), flow.getJsonFactory(), credential)
                .setApplicationName("SIGU")
                .build();
    }
}
