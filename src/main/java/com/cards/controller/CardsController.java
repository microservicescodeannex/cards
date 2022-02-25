package com.cards.controller;

import com.cards.config.CardsServiceConfig;
import com.cards.model.Cards;
import com.cards.model.Customer;
import com.cards.model.Properties;
import com.cards.repository.CardsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

  private final CardsRepository cardsRepository;
  private final CardsServiceConfig cardsServiceConfig;

  public CardsController(CardsRepository cardsRepository, CardsServiceConfig cardsServiceConfig) {

    this.cardsRepository = cardsRepository;
    this.cardsServiceConfig = cardsServiceConfig;
  }

  @PostMapping("/cards")
  public List<Cards> getCardDetails(@RequestBody Customer customer) {
    return cardsRepository.findByCustomerId(customer.getCustomerId());
  }

  @GetMapping("/card/properties")
  public String getPropertyDetails() throws JsonProcessingException {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    Properties properties = new Properties(
        cardsServiceConfig.getMsg(),
        cardsServiceConfig.getBuildVersion(),
        cardsServiceConfig.getMailDetails(),
        cardsServiceConfig.getActiveBranches()
    );

    return ow.writeValueAsString(properties);
  }
}
