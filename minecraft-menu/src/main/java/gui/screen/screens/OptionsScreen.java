package gui.screen.screens;

import gui.components.BaseLayout;
import gui.components.Button;
import gui.components.Layout;
import gui.text.Component;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.Align;
import jpize.gui.LayoutType;
import jpize.gui.constraint.Constraint;
import run.Session;
import gui.components.TextView;
import gui.constraints.GapConstraint;
import lang.LanguageInfo;

import java.util.List;

public class OptionsScreen extends IOptionsScreen{

    private final gui.components.BaseLayout layout;

    public OptionsScreen(Session session){
        super(session);

        // [Main Layout]
        layout = new BaseLayout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);

        // <----------TEXTS---------->
        // < Title >

        // Title (Options)
        TextView titleTextView = new TextView(session, new gui.text.Component().translation("jpize.tests.minecraft.options.title"));
        titleTextView.setY(GapConstraint.gap(108));
        layout.put("title", titleTextView);
        
        // <----------LINE 1---------->
        // [ Video Settings ] [ Music & Sounds ]

        // 1 Line Layout
        gui.components.Layout layoutLine1 = new gui.components.Layout();
        layoutLine1.setY(GapConstraint.gap(15));
        layoutLine1.setSize(Constraint.aspect(16), Constraint.pixel(20));
        layoutLine1.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("layout_line_1", layoutLine1);

        // Video Settings
        gui.components.Button videoSettingsButton = new gui.components.Button(session, new gui.text.Component().translation("jpize.tests.minecraft.options.videoSettings"));
        videoSettingsButton.setClickListener(()->toScreen("video_settings"));
        videoSettingsButton.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine1.put("video_settings", videoSettingsButton);

        // Music & Sounds
        gui.components.Button audioSettingsButton = new gui.components.Button(session, new gui.text.Component().translation("jpize.tests.minecraft.options.audioSettings"));
        audioSettingsButton.setClickListener(()->toScreen("audio_settings"));
        audioSettingsButton.setSize(Constraint.pixel(155), Constraint.relative(1));
        audioSettingsButton.alignSelf(Align.RIGHT);
        layoutLine1.put("audio_settings", audioSettingsButton);

        // <----------LINE 2---------->
        // [ Language ] [ Controls ]

        // 2 Line Layout
        gui.components.Layout layoutLine2 = new gui.components.Layout();
        layoutLine2.setY(GapConstraint.gap(4));
        layoutLine2.setSize(Constraint.aspect(16), Constraint.pixel(20));
        layoutLine2.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("layout_line_2", layoutLine2);

        // Language
        gui.components.Button languageButton = new gui.components.Button(session, new gui.text.Component().translation("jpize.tests.minecraft.options.language"));
        languageButton.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine2.put("language", languageButton);

        // Controls
        gui.components.Button controlsButton = new gui.components.Button(session, new gui.text.Component().translation("jpize.tests.minecraft.options.controls"));
        controlsButton.setClickListener(()->System.out.println("Controls"));
        controlsButton.setSize(Constraint.pixel(155), Constraint.relative(1));
        controlsButton.alignSelf(Align.RIGHT);
        layoutLine2.put("controls", controlsButton);

        // <----------LINE 3---------->
        // [ Resource Packs ]

        // 3 Line Layout
        gui.components.Layout layoutLine3 = new Layout();
        layoutLine3.setY(GapConstraint.gap(4));
        layoutLine3.setSize(Constraint.aspect(16), Constraint.pixel(20));
        layoutLine3.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("layout_line_3", layoutLine3);

        // Resource Packs
        gui.components.Button resourcePacksButton = new gui.components.Button(session, new gui.text.Component().translation("jpize.tests.minecraft.options.resourcePacks"));
        resourcePacksButton.setClickListener(()->System.out.println("Resource Packs"));
        resourcePacksButton.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine3.put("resource_packs", resourcePacksButton);

        // <----------DONE---------->
        // [ Done ]

        // Done
        gui.components.Button doneButton = new Button(session, new Component().translation("jpize.tests.minecraft.gui.done"));
        doneButton.setClickListener(this::close);
        doneButton.setY(GapConstraint.gap(15));
        doneButton.setSize(Constraint.aspect(10), Constraint.pixel(20));
        layout.put("done", doneButton);

        // <----------CALLBACKS---------->

        // Language
        languageButton.setClickListener(new Runnable(){
            int langIndex = 0;

            @Override
            public void run(){
                List<LanguageInfo> availableLanguages = session.getLanguageManager().getAvailableLanguages();
                if(!availableLanguages.isEmpty()){
                    langIndex++;
                    if(langIndex >= availableLanguages.size())
                        langIndex = 0;

                    String languageCode = availableLanguages.get(langIndex).getCode();
                    System.out.println("Selected Lang: " + languageCode);
                    session.getLanguageManager().selectLanguage(languageCode);

                    session.getOptions().setLanguage(languageCode);
                    session.getOptions().save();
                }
            }
        });
    }

    @Override
    public void render(TextureBatch batch){
        layout.render(batch);
    }

    @Override
    public void resize(int width, int height){ }

    @Override
    public void onShow(){ }

    @Override
    public void close(){
        toScreen("main_menu");
        session.getOptions().save();
    }

}