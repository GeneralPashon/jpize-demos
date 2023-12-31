package gui.components;

import gui.text.Component;
import jpize.math.Maths;
import run.Session;
import options.SoundCategory;

public class VolumeSlider extends Slider{

    private final SoundCategory soundCategory;

    public VolumeSlider(Session session, SoundCategory soundCategory){
        super(session);

        this.soundCategory = soundCategory;

        float initialVolume = session.getOptions().getSoundVolume(soundCategory);
        setText(new gui.text.Component().translation(soundCategory.getTranslateKey(), new gui.text.Component().formattedText(String.valueOf(Maths.round(initialVolume * 100)))));
        setValue(initialVolume);
        setDivisions(100);
    }

    
    public void updateVolume(){
        if(!isChanged())
            return;

        float volume = getValue();
        setText(new gui.text.Component().translation(soundCategory.getTranslateKey(), new Component().formattedText(String.valueOf(Maths.round(volume * 100)))));

        if(soundCategory == SoundCategory.MUSIC)
            session.getMusicManager().setVolume(volume);

        session.getOptions().setSoundVolume(soundCategory, volume);
        session.getOptions().save();
    }

}
