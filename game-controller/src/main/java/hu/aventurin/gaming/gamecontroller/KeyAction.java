package hu.aventurin.gaming.gamecontroller;

import java.util.function.Consumer;

@FunctionalInterface
public interface KeyAction extends Consumer<Boolean> {
}
