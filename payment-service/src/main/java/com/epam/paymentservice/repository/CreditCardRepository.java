package com.epam.paymentservice.repository;

import com.epam.paymentservice.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
    Optional<CreditCard> findByCardNumber(String cardNumber);
}
