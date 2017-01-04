package com.suli.learnning.store;

import com.suli.learnning.factory.annotations.Factory;

/**
 * Created by suli690 on 2017-01-01.
 * meal of margherita pizza
 */
@Factory(
        id = "Margherita",
        type = IMeal.class
)
public class MargheritaPizzaBean implements IMeal {

    public float getPrice() {
        return 108f;
    }
}
