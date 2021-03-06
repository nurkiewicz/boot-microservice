package com.ofg.infrastructure.correlationid

import groovy.transform.TypeChecked

import java.util.concurrent.Callable

@TypeChecked
class CorrelationCallable<T> implements Callable<T> {
    private String correlationId
    private Callable<T> callable

    CorrelationCallable(Callable<T> targetCallable) {
        correlationId = CorrelationIdHolder.get()
        callable = targetCallable
    }

    @Override
    T call() throws Exception {
        CorrelationIdHolder.set(correlationId)
        return callable.call()
    }

    static Callable<String> withCorrelationId(Closure closure) {
        return new CorrelationCallable(closure)
    }
}