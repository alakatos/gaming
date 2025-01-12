package hu.laki.lights.view;

import java.awt.Graphics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class View<T> {
    private final T model;
    
    public abstract void render(Graphics g);
    
    public T getModel() {
        return model;
    }
}
