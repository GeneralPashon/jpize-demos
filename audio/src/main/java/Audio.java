import jpize.Jpize;
import jpize.audio.io.WavFile;
import jpize.audio.sound.Sound;
import jpize.audio.util.SoundGenerator;
import jpize.files.Resource;
import jpize.glfw.key.Key;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;

public class Audio extends JpizeApplication{

    public static void main(String[] args){
        ContextBuilder.newContext(700, 150, "Audio")
                .icon("icon.png").register()
                .setAdapter(new Audio());
        Jpize.runContexts();
    }


    private Sound sound;
    private AudioPlayerUI AudioPlayerUI;
    
    @Override
    public void init(){
        Resource resource = new Resource("Generated.wav", true);
        resource.create();

        // Generate WAV File and Save
        SoundGenerator generator = new SoundGenerator();
        WavFile wavFile = new WavFile(resource.getFile(), generator.getSampleRate(), generator.getChannels());
        wavFile.setData(generator.sinDown(880, 0.15));
        wavFile.save();

        // Load Generated WAV
        sound = new Sound(resource);
        System.out.println("Generated.wav duration: " + sound.getDuration() + " sec");
        sound.setVolume(0.25F);
        sound.play();
        while(sound.isPlaying());
        sound.dispose();

        // Load OGG
        sound = new Sound("MyMusic.ogg");
        System.out.println("MyMusic.ogg duration: " + sound.getDuration() + " sec");

        // Play jpize.tests.minecraft.audio with GUI
        AudioPlayerUI = new AudioPlayerUI();
        AudioPlayerUI.setSound(sound);
        AudioPlayerUI.play();
    }

    @Override
    public void render(){
        if(Key.ESCAPE.isDown())
            Jpize.exit();

        AudioPlayerUI.update();
    }
    
    @Override
    public void dispose(){
        sound.dispose();

        AudioPlayerUI.dispose();
    }

}
