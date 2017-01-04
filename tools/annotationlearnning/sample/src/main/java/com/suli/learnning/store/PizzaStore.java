package com.suli.learnning.store;

/**
 * Created by suli690 on 2017-01-01.
 */
public class PizzaStore {

    private IMealFactory factory = new IMealFactory();

    public IMeal order(String mealName) {
//        switch (mealName) {
//            case "Margherita":
//                return new MargheritaPizzaBean();
//            case "Calzone":
//                return new CalzonePizzaBean();
//            case "Tiramisu":
//                return new TiramisuBean();
//            default:
//                throw new IllegalArgumentException("Unknown meal:" + mealName);
//        }
        return factory.create(mealName);
    }
}
