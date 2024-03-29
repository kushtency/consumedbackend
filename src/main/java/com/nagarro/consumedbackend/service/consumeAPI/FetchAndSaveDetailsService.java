package com.nagarro.consumedbackend.service.consumeAPI;

import com.nagarro.consumedbackend.async.FetchDetailsParallelExecutor;
import com.nagarro.consumedbackend.dto.consumeApiResponse.UserResponse;
import com.nagarro.consumedbackend.model.User;
import com.nagarro.consumedbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class FetchAndSaveDetailsService {
    private final FetchUserNameService fetchUserNameService;
    private final FetchDetailsParallelExecutor fetchDetailsExecutor;
    private final UserRepository userRepository;
    private final ExecutorService executorService;

    @Autowired
    public FetchAndSaveDetailsService(
            FetchUserNameService fetchUserNameService,
            FetchDetailsParallelExecutor fetchDetailsExecutor,
            UserRepository userRepository,
            ExecutorService executorService
    ) {
        this.fetchUserNameService = fetchUserNameService;
        this.fetchDetailsExecutor = fetchDetailsExecutor;
        this.userRepository = userRepository;
        this.executorService = executorService;
    }

    public Flux<User> fetchResult(int results) {
        return fetchUserNameService.fetch(String.valueOf(results))
                .flatMapMany((userResponse) -> Flux.fromIterable(userResponse.getResults()))
                .flatMap(this::getUserFlux);

    }

    private Flux<User> getUserFlux(UserResponse.Result fluxResult) {
        return Flux.create((fluxSink) -> {
            User user = new User();
            String username = fluxResult.getName().getFirst();
            String lastName = fluxResult.getName().getLast();
            String nationality = fluxResult.getNat();
            String gender = fluxResult.getGender();
            AtomicBoolean isNatVerified = new AtomicBoolean(false);
            AtomicBoolean isGenderVerified = new AtomicBoolean(false);

            var fetchNat = fetchDetailsExecutor.fetchNationality(username).thenApplyAsync((natRes) -> {
                Objects.requireNonNull(natRes.getBody()).getCountry().forEach(
                        (country) -> {
                            if (country.getCountryId().equalsIgnoreCase(nationality)) {
                                user.setNationality(nationality);
                                isNatVerified.set(true);
                            }
                        }
                );
                return null;
            }, executorService);
            var fetchGen = fetchDetailsExecutor.fetchGender(username).thenApplyAsync((genRes) -> {
                String resGender = Objects.requireNonNull(genRes.getBody()).getGender();
                if (resGender.equalsIgnoreCase(gender)) {
                    user.setGender(gender);
                    isGenderVerified.set(true);
                }
                return null;
            }, executorService);
            fetchNat.thenCombine(fetchGen, (nat, gen) -> {
                if (isGenderVerified.get() && isNatVerified.get()) {
                    user.setVerificationStatus("VERIFIED");
                } else {
                    user.setVerificationStatus("TO_BE_VERIFIED");
                }
                user.setName(username.concat(" ").concat(lastName));
                user.setDOB(fluxResult.getDob().getDate());
                user.setAge(fluxResult.getDob().getAge());
                user.setCreatedDate(LocalDateTime.now());
                user.setModifiedDate(LocalDateTime.now());

                userRepository.save(user);
                fluxSink.next(user);
                return user;
            }).thenApply((completedUser) -> {
                fluxSink.complete();
                return completedUser;
            });
        });
    }
}
