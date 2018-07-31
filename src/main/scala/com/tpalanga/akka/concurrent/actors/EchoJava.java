package com.tpalanga.akka.concurrent.actors;

public class EchoJava {
    private String value;

    public EchoJava(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EchoJava echoJava = (EchoJava) o;

        return value != null ? value.equals(echoJava.value) : echoJava.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
