package com.peerlender.LendingEngine.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@NoArgsConstructor
public final class Money {

    @Id
    @GeneratedValue
    private long id;
    private Currency currency;
    private BigDecimal amount;

    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = BigDecimal.valueOf(amount).setScale(0, RoundingMode.DOWN);
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount.doubleValue();
    }

    public Money Add(final Money money){
        if(currency != money.currency){
            throw new IllegalArgumentException();
        }
        return new Money( currency,amount.doubleValue() + money.getAmount());
    }

    public Money Minus(final Money money){
        if(currency != money.getCurrency() || amount.doubleValue() < money.getAmount()){
            throw new IllegalArgumentException();
        }
        return new Money(currency, amount.doubleValue() - money.getAmount());
    }


}
