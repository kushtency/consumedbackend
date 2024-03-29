package com.nagarro.consumedbackend;

import com.nagarro.consumedbackend.controller.user.UserController;
import com.nagarro.consumedbackend.dto.backendRequest.Size;
import com.nagarro.consumedbackend.dto.backendResponse.PaginationResponse;
import com.nagarro.consumedbackend.model.User;
import com.nagarro.consumedbackend.service.backend.UserService;
import com.nagarro.consumedbackend.service.consumeAPI.FetchAndSaveDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ConsumedbackendApplicationTests {

	@Mock
	UserController userController;
	@MockBean
	FetchAndSaveDetailsService fetchAndSaveDetailsService;
	@MockBean
	UserService userService;

	@Test
	void contextLoads() {
		System.out.println("App Loaded");
	}

	@Test
	void postRequestCheck() {
		WebTestClient webTestClient = WebTestClient
				.bindToController(userController)
				.build();

		User user = new User(
				"Test",
				"IN",
				89,
				"male",
				"VERIFIED",
				"2023", LocalDateTime.now(), LocalDateTime.now());

		User user2 = new User(
				"Test2",
				"IN",
				89,
				"male",
				"VERIFIED",
				"2023", LocalDateTime.now(), LocalDateTime.now());

		Flux<User> fluxUser = Flux.just(user, user2);
		Size size = new Size();
		size.setSize(2);

		Mockito
				.when(fetchAndSaveDetailsService.fetchResult(2))
				.thenReturn(fluxUser);

		webTestClient
				.post()
				.uri("/users")
				.bodyValue(size)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
				.expectBodyList(User.class)
				.returnResult();
		;
	}

	@Test
	void getRequestCheck() {

		WebTestClient webTestClient = WebTestClient
				.bindToController(userController)
				.build();

		User user = new User(
				"Test",
				"IN",
				89,
				"male",
				"VERIFIED",
				"2023", LocalDateTime.now(), LocalDateTime.now());

		User user2 = new User(
				"Test2",
				"IN",
				89,
				"male",
				"VERIFIED",
				"2023", LocalDateTime.now(), LocalDateTime.now());
		PaginationResponse res = PaginationResponse.builder()
				.data(List.of(user, user2))
				.pageInfo(PaginationResponse.PageInfo.builder()
						.hasNextPage(true)
						.hasPreviousPage(true)
						.total(2).build())
				.build();

		Mockito
				.when(userService.getUsers("AGE", "ODD",2,0))
				.thenReturn(res);

		webTestClient
				.get()
				.uri(uri -> uri
						.path("/users")
						.queryParam("sortType", "AGE")
						.queryParam("sortOrder", "ODD")
						.queryParam("limit", 2)
						.queryParam("offset", 0)
						.build()
				).exchange()
				.expectStatus().isOk()
				.expectBody(PaginationResponse.class)
				.returnResult();
	}

}
