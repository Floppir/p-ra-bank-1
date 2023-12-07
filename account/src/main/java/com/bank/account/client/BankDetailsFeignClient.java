package com.bank.account.client;

import com.bank.account.dto.BankDetailsDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "public-info-app")
public interface BankDetailsFeignClient {

    @GetMapping("/api/public-info/bank/details/{id}")
    @Cacheable(value = "bankDetailsCache")
    BankDetailsDto readById(@PathVariable("id") Long bankDetailsId);
}
