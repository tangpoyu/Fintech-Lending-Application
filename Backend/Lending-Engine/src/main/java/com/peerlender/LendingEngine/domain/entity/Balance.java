package com.peerlender.LendingEngine.domain.entity;


import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@NoArgsConstructor
public class Balance {

    @Id
    @GeneratedValue
    private long id;

    @ElementCollection
    @MapKeyClass(Currency.class)
    @OneToMany(targetEntity = Money.class, cascade = CascadeType.ALL)
    private Map<Currency,Money> moneyMap = new HashMap<>();

    public Map<Currency, Money> getMoneyMap() {
        return moneyMap;
    }

    public void TopUp(final Money money){
        if(moneyMap.get(money.getCurrency()) == null){
             moneyMap.put(money.getCurrency(),money);
        }else {
            Money currentMoney = moneyMap.get(money.getCurrency());
            Money updateMoney = currentMoney.Add(money);
            moneyMap.put(money.getCurrency(), updateMoney);
        }
    }

    public void Withdraw(final Money money){
        final Money moneyInBalance = moneyMap.get(money.getCurrency());
        if(moneyInBalance == null){
            throw new IllegalStateException();
        }else {
            Money updateMoney = moneyInBalance.Minus(money);
            moneyMap.put(money.getCurrency(),updateMoney);
        }
    }


}
