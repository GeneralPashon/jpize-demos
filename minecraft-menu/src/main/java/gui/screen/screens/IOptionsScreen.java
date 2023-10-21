package gui.screen.screens;

import run.Session;
import gui.screen.Screen;
import options.Options;

public abstract class IOptionsScreen extends Screen{

    public IOptionsScreen(Session session){
        super(session);
    }


    @Override
    public boolean shouldCloseOnEsc(){
        return true;
    }

    @Override
    public boolean renderDirtBackground(){
        return true;
    }

    @Override
    public void dispose(){ }

    public void saveOptions(){
        session.getOptions().save();
    }
    
    public Options getOptions(){
        return session.getOptions();
    }

}
