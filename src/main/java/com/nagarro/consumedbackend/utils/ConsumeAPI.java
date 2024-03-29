package com.nagarro.consumedbackend.utils;

import com.nagarro.consumedbackend.Constants.RequestURL;
import com.nagarro.consumedbackend.async.FetchDetailsParallelExecutor;
import com.nagarro.consumedbackend.model.User;
import com.nagarro.consumedbackend.dto.consumeApiResponse.UserResponse;
import com.nagarro.consumedbackend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ConsumeAPI {
    private final WebClient fetchUser;
    private final FetchDetailsParallelExecutor fetchDetailsExecutor;
    private final UserRepository userRepository;
    private final ExecutorService executorService;

    @Autowired
    public ConsumeAPI(@Qualifier("fetchUser") WebClient fetchUser,
                      FetchDetailsParallelExecutor fetchDetailsExecutor,
                      UserRepository userRepository,
                      ExecutorService executorService){
        this.fetchUser = fetchUser;
        this.fetchDetailsExecutor = fetchDetailsExecutor;
        this.userRepository = userRepository;
        this.executorService = executorService;
    }
    public void testBean() throws ExecutionException, InterruptedException {
        User user = new User();
        AtomicBoolean isGenderVerified = new AtomicBoolean(false);
        AtomicBoolean isNatVerified = new AtomicBoolean(false);

        fetchUser
                .get()
                .uri(RequestURL.FETCH_USER_NAME)
                .retrieve()
                .toEntity(UserResponse.class)
                .subscribe(
                        (result) -> {
                            UserResponse userResponse = result.getBody();
                            assert userResponse != null;
                            String username = userResponse.getResults().get(0).getName().getFirst();
                            String lastName = userResponse.getResults().get(0).getName().getLast();
                            String nationality = userResponse.getResults().get(0).getNat();
                            String gender = userResponse.getResults().get(0).getGender();
                            var fetchNat = fetchDetailsExecutor.fetchNationality(username).thenApplyAsync((natRes) -> {
                                Objects.requireNonNull(natRes.getBody()).getCountry().forEach(
                                        (country) -> {
                                            if(country.getCountryId().equalsIgnoreCase(nationality)){
                                                user.setNationality(nationality);
                                                isNatVerified.set(true);
                                            }
                                        }
                                );
                                return null;
                            }, executorService);
                            var fetchGen = fetchDetailsExecutor.fetchGender(username).thenApplyAsync((genRes) -> {
                                String resGender = Objects.requireNonNull(genRes.getBody()).getGender();
                                if(resGender.equalsIgnoreCase(gender)){
                                    user.setGender(gender);
                                    isGenderVerified.set(true);
                                }
                                return null;
                            }, executorService);
                            fetchNat.thenCombine(fetchGen, (nat,gen) -> {
                                if(isGenderVerified.get() && isNatVerified.get()){
                                    user.setVerificationStatus("VERIFIED");
                                }else{
                                    user.setVerificationStatus("TO_BE_VERIFIED");
                                }
                                user.setName(username.concat(" ").concat(lastName));
                                user.setDOB(userResponse.getResults().get(0).getDob().getDate());
                                user.setAge(userResponse.getResults().get(0).getDob().getAge());

                                userRepository.save(user);
                                return null;
                            });
                        },
                        System.out::println,
                        () -> System.out.println("fetched successfully")
                );
    }
}
