package com.suli.learnning.store;

import com.suli.learnning.factory.annotations.Factory;

/**
 * Created by suli690 on 2017-01-01.
 * meal of tiramisu
 */
@Factory(
        id = "Tiramisu",
        type = IMeal.class
)
public class TiramisuBean implements IMeal{
    public float getPrice() {
        return 10f;
    }
}
