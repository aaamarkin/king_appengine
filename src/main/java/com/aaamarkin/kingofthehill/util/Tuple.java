package com.aaamarkin.kingofthehill.util;

import java.util.Objects;

public class Tuple<T, U> {

    public final T _1;
    public final U _2;

    public Tuple(T arg1, U arg2) {
        super();
        this._1 = arg1;
        this._2 = arg2;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", _1, _2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(_1, tuple._1) &&
                Objects.equals(_2, tuple._2);
    }

    @Override
    public int hashCode() {

        return Objects.hash(_1, _2);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
//        if (!a.equals(tuple.a)) return false;
//        return b.equals(tuple.b);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = a.hashCode();
//        result = 31 * result + b.hashCode();
//        return result;
//    }
}
