package com.mediscreen.notesconf;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NotesAPI {

    private static final Logger log = LoggerFactory.getLogger(NotesAPI.class);

    public static void main(String[] args) {
        SpringApplication.run(NotesAPI.class, args);
    }
}