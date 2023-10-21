package gui.screen;

import gui.components.DirtBackground;
import gui.text.Component;
import jpize.glfw.key.Key;
import jpize.graphics.util.batch.TextureBatch;
import run.Session;
import jpize.util.Disposable;
import jpize.util.Resizable;

public abstract class Screen implements Resizable, Disposable{

    public static final float BUTTON_HEIGHT = 0.055F;
    public static final float TEXT_SCALING = 8 / 20F;
    public static final float TEXT_HEIGHT = BUTTON_HEIGHT * TEXT_SCALING;

    public final Session session;
    private final gui.components.DirtBackground options_background;

    public Screen(Session session){
        this.session = session;

        options_background = new DirtBackground(session);
    }

    public void update(TextureBatch batch){
        if(shouldCloseOnEsc() && Key.ESCAPE.isDown())
            close();

        if(renderDirtBackground()){
            options_background.render(batch);
        }
    }

    public void toScreen(String id){
        session.getScreenManager().setCurrentScreen(id);
    }


    public abstract void render(TextureBatch batch);

    public abstract void onShow();

    public abstract boolean shouldCloseOnEsc();

    public abstract boolean renderDirtBackground();

    public abstract void close();


    public gui.text.Component boolToText(boolean bool){
        return new Component().translation(bool ? "text.on" : "text.off");
    }

}
