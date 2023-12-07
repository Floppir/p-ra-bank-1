package com.bank.account.client;

import com.bank.account.dto.ProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-app")
public interface ProfileFeignClient {

    @GetMapping("/api/profile/profile/read/{id}")
    ProfileDto readById(@PathVariable("id") Long profileId);
}
