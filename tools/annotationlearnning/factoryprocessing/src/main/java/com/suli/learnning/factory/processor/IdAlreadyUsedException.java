package com.suli.learnning.factory.processor;

/**
 * Created by suli690 on 2017-01-01.
 */
public class IdAlreadyUsedException extends Exception {
    private FactoryAnnotatedClass annotatedClass;

    public IdAlreadyUsedException(FactoryAnnotatedClass annotatedClass) {
        this.annotatedClass = annotatedClass;
    }

    public FactoryAnnotatedClass getExisting() {
        return annotatedClass;
    }
}
