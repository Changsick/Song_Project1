package com.song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
public class SongProject2Application {

	public static void main(String[] args) {
		SpringApplication.run(SongProject2Application.class, args);
	}

}
