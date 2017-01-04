package com.suli.learnning.store;

import com.suli.learnning.factory.annotations.Factory;

/**
 * Created by suli690 on 2017-01-01.
 * meal of calzone pizza
 */
@Factory(
        id = "Calzone",
        type = IMeal.class
)
public class CalzonePizzaBean implements IMeal {

    public float getPrice() {
        return 88f;
    }
}
