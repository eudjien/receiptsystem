package ru.clevertec.checksystem.core;

public abstract class ProxyIdentifier {

    private static boolean isProxied;

    public static boolean isProxied() {
        return isProxied;
    }

    public static void setProxied(boolean isProxied) {
        ProxyIdentifier.isProxied = isProxied;
    }
}
