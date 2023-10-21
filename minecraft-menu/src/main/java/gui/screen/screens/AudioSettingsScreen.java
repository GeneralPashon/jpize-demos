package gui.screen.screens;

import gui.components.Button;
import jpize.audio.Audio;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.Align;
import jpize.gui.LayoutType;
import jpize.gui.constraint.Constraint;
import run.Session;
import gui.constraints.GapConstraint;
import gui.text.Component;
import options.SoundCategory;

import java.util.List;

public class AudioSettingsScreen extends IOptionsScreen{

    private final gui.components.BaseLayout layout;
    private final gui.components.VolumeSlider masterVolume, musicVolume, ambientVolume, playersVolume, blocksVolume, weatherVolume;
    
    public AudioSettingsScreen(Session session){
        super(session);

        // Main Layout
        layout = new gui.components.BaseLayout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);

        // <----------TEXTS---------->
        // < Title >
        gui.components.TextView titleTextView = new gui.components.TextView(session, new Component().translation("audioSettings.title"));
        titleTextView.setY(GapConstraint.gap(71));
        layout.put("title", titleTextView);
        // Title
        
        gui.components.ListView list = new gui.components.ListView();
        list.setLayoutType(LayoutType.VERTICAL);
        list.alignItems(Align.UP);
        list.setSize(Constraint.relative(0.8), Constraint.relative(0.3));
        list.setY(GapConstraint.gap(3));
        layout.put("list", list);
        
        
        // <----------LINE 1---------->
        // [ Master Volume ]
        masterVolume = new gui.components.VolumeSlider(session, SoundCategory.MASTER);
        masterVolume.setY(GapConstraint.gap(15));
        masterVolume.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("master", masterVolume);
        // Master Volume

        // <----------LINE 2---------->
        // [ Music ] [ Ambient ]
        
        // 2 Line Layout
        gui.components.Layout layoutLine2 = new gui.components.Layout();
        layoutLine2.setLayoutType(LayoutType.HORIZONTAL);
        layoutLine2.setY(GapConstraint.gap(4));
        layoutLine2.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("line2layout", layoutLine2);
        // Music
        musicVolume = new gui.components.VolumeSlider(session, SoundCategory.MUSIC);
        musicVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine2.put("music", musicVolume);
        // Ambient
        ambientVolume = new gui.components.VolumeSlider(session, SoundCategory.AMBIENT);
        ambientVolume.alignSelf(Align.RIGHT);
        ambientVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine2.put("ambient", ambientVolume);
        // <----------LINE 3---------->
        // [ Players ] [ Blocks ]

        // 3 Line Layout
        gui.components.Layout layoutLine3 = new gui.components.Layout();
        layoutLine3.setLayoutType(LayoutType.HORIZONTAL);
        layoutLine3.setY(GapConstraint.gap(4));
        layoutLine3.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("line3layout", layoutLine3);
        // Players
        playersVolume = new gui.components.VolumeSlider(session, SoundCategory.PLAYERS);
        playersVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine3.put("players", playersVolume);
        // Blocks
        blocksVolume = new gui.components.VolumeSlider(session, SoundCategory.BLOCKS);
        blocksVolume.alignSelf(Align.RIGHT);
        blocksVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine3.put("blocks", blocksVolume);
        // <----------LINE 4---------->
        // [ Wather ]

        // 4 Line Layout
        gui.components.Layout layoutLine4 = new gui.components.Layout();
        layoutLine4.setLayoutType(LayoutType.HORIZONTAL);
        layoutLine4.setY(GapConstraint.gap(4));
        layoutLine4.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("line4layout", layoutLine4);
        // Weather
        weatherVolume = new gui.components.VolumeSlider(session, SoundCategory.WEATHER);
        weatherVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine4.put("weather", weatherVolume);
        // <----------LINE 5---------->
        // [ Device ]

        // Device
        gui.components.Button deviceButton = new gui.components.Button(session, new Component().translation("audioSettings.device", new Component().formattedText(session.getOptions().getAudioDevice())));
        deviceButton.setY(GapConstraint.gap(4));
        deviceButton.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("device", deviceButton);

        // <----------DONE---------->
        // [ Done ]
        
        // Done
        gui.components.Button doneButton = new Button(session, new Component().translation("jpize.tests.minecraft.gui.done"));
        doneButton.setY(GapConstraint.gap(4));
        doneButton.setSize(Constraint.aspect(10), Constraint.pixel(20));
        doneButton.setClickListener(this::close);
        layout.put("done", doneButton);

        // <----------CALLBACKS---------->

        // Device
        deviceButton.setClickListener(new Runnable(){
            int deviceIndex = Audio.getAvailableDevices().indexOf(Audio.getDefaultInputDevice());

            @Override
            public void run(){
                session.getMusicManager().pause();
                final List<String> list = Audio.getAvailableDevices();
                if(list != null){
                    deviceIndex++;
                    if(deviceIndex >= list.size())
                        deviceIndex = 0;

                    final String nextDevice = list.get(deviceIndex);

                    deviceButton.setText(new Component()
                            .translation("audioSettings.device", new Component().text(nextDevice)));

                    session.getOptions().setAudioDevice(nextDevice);
                    session.getOptions().save();
                }
                session.getMusicManager().resume();
            }
        });
    }

    @Override
    public void render(TextureBatch batch){
        masterVolume.updateVolume();
        musicVolume.updateVolume();
        ambientVolume.updateVolume();
        playersVolume.updateVolume();
        blocksVolume.updateVolume();
        weatherVolume.updateVolume();
        
        layout.render(batch);
    }

    @Override
    public void resize(int width, int height){ }

    @Override
    public void onShow(){ }

    @Override
    public void close(){
        toScreen("options");
    }

}