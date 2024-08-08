package com.cardanoj.api.AddressController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cardanoj.api.dto.CardanoJAddressData;
import com.cardanoj.api.service.CardanoJAddressService;
import com.cardanoj.api.util.CardanoJRandomNameGenerator;

/**
 * Controller for building Cardano addresses.
 */
@RestController
@RequestMapping("/api")
public class CardanoJBuildAddressController {

    @Autowired
    private CardanoJAddressService cardanoJAddressService;

    @Autowired
    private CardanoJRandomNameGenerator randomNameGenerator;

    /**
     * Generates a new Cardano address.
     *
     * @return the generated address data
     */
    @GetMapping("/address")
    public CardanoJAddressData generateAddress() {
        String randomName = randomNameGenerator.generate();
        return cardanoJAddressService.createAndReadAddressData(randomName);
    }
}
