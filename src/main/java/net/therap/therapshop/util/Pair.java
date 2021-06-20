package net.therap.therapshop.util;

import java.io.Serializable;

/**
 * @author al.imran
 * @since 09/06/2021
 */
public class Pair<A, B> implements Serializable {

    private static final long serialVersionUID = 1L;

    private A a;
    private B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getFirst() {
        return a;
    }

    public B getSecond() {
        return b;
    }
}
