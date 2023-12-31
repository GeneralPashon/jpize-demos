package resources;

import jpize.audio.sound.AudioBuffer;
import log.Logger;

public class SoundResource extends Resource<AudioBuffer>{

    private AudioBuffer sound;
    private boolean loaded;

    public SoundResource(String location){
        super(location);
    }

    @Override
    public void loadResource(){
        try{
            sound = new AudioBuffer(getLocation());
            loaded = true;
        }catch(RuntimeException e){
            loaded = false;
            Logger.instance().warn(e.getLocalizedMessage());
        }
    }

    @Override
    public void reloadResource(){
        AudioBuffer oldSound = sound;
        loadResource();
        oldSound.dispose();
    }

    @Override
    public boolean isLoaded(){
        return loaded;
    }

    @Override
    public AudioBuffer getResource(){
        return sound;
    }

    @Override
    public void dispose(){
        sound.dispose();
    }

}
