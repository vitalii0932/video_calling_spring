package com.example.video_call;

import com.example.video_call.user.User;
import com.example.video_call.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VideoCallApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoCallApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (
			UserService userService
	) {
		return args -> {
			userService.register(User.builder()
							.userName("Vetal")
							.email("vitalikhall@gmail.com")
							.password("supersecret!")
					.build());

			userService.register(User.builder()
					.userName("John Doe")
					.email("john_doe@gmail.com")
					.password("supersecret!")
					.build());

			userService.register(User.builder()
					.userName("Alina")
					.email("alina@gmail.com")
					.password("supersecret!")
					.build());
		};
	}

}
