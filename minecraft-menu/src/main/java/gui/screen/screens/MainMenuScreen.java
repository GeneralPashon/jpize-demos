package gui.screen.screens;

import gui.components.TextView;
import gui.constraints.GapConstraint;
import gui.text.Component;
import gui.text.formatting.StyleFormatting;
import jpize.Jpize;
import jpize.files.Resource;
import jpize.graphics.camera.PerspectiveCamera;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureRegion;
import jpize.graphics.util.SkyBox;
import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.ImmutableColor;
import jpize.gui.Align;
import jpize.gui.LayoutType;
import jpize.gui.constraint.Constraint;
import run.Session;
import gui.screen.Screen;

public class MainMenuScreen extends Screen{

    public static final ImmutableColor SPLASH_COLOR = new ImmutableColor(1, 1, 0, 1F);

    private final SkyBox skyBox;
    private final PerspectiveCamera camera;
    private final Texture panorama_overlay;

    private final gui.components.BaseLayout layout;
    private final gui.components.BaseLayout layoutTexts;

    private final Resource resSplashes;
    private final gui.components.TextView splashTextView;

    public MainMenuScreen(Session session){
        super(session);

        // Panorama
        skyBox = new SkyBox("vanilla/textures/gui/title/background/panorama_1.png", "vanilla/textures/gui/title/background/panorama_3.png", "vanilla/textures/gui/title/background/panorama_4.png", "vanilla/textures/gui/title/background/panorama_5.png", "vanilla/textures/gui/title/background/panorama_0.png", "vanilla/textures/gui/title/background/panorama_2.png"
        );
        camera = new PerspectiveCamera(0.1, 2, 79);
        camera.getRotation().set(90, -25);
        panorama_overlay = session.getResourceManager().getTexture("panorama_overlay").getTexture();

        // Main Layout
        layout = new gui.components.BaseLayout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);

        // <----------TITLE---------->
        // [   Mine|craft   ]
        // [  Java Edition  ]
        //         < SPLASH >

        // Title (MINECRAFT)
        TextureRegion titleLeftPartTexture = session.getResourceManager().getTexture("title_left_part");
        TextureRegion titleRightPartTexture = session.getResourceManager().getTexture("title_right_part");
        TextureRegion titleEditionTexture = session.getResourceManager().getTexture("title_edition");

        gui.components.Layout titleLayout = new gui.components.Layout();
        titleLayout.setLayoutType(LayoutType.HORIZONTAL);
        titleLayout.setY(gui.constraints.GapConstraint.gap(25));
        titleLayout.setSize(
            Constraint.aspect(titleLeftPartTexture.aspect() + titleRightPartTexture.aspect()),
            Constraint.pixel(45)
        );
        layout.put("title", titleLayout);

        gui.components.Image titleLeftPart = new gui.components.Image(titleLeftPartTexture);
        titleLeftPart.setSize(Constraint.aspect(titleLeftPartTexture.aspect()), Constraint.relative(1));
        titleLayout.put("title_left", titleLeftPart);
        
        gui.components.Image titleRightPart = new gui.components.Image(titleRightPartTexture);
        titleRightPart.setSize(Constraint.aspect(titleRightPartTexture.aspect()), Constraint.relative(1));
        titleLayout.put("title_right", titleRightPart);

        // Edition (Java Edition)
        gui.components.Image edition = new gui.components.Image(titleEditionTexture);
        edition.setSize(Constraint.aspect(7), Constraint.pixel(13));
        edition.setY(gui.constraints.GapConstraint.gap(-7));
        layout.put("edition", edition);

        // Splash
        resSplashes = new Resource("vanilla/texts/splashes.txt");

        splashTextView = new gui.components.TextView(session, new gui.text.Component().color(SPLASH_COLOR).style(StyleFormatting.ITALIC).formattedText("Splash!"));
        splashTextView.setSize(Constraint.pixel(10));
        splashTextView.setRotation(15);
        layout.put("splash", splashTextView);

        // <----------BUTTONS: 1-2 LINE---------->
        // [ Singleplayer ]
        // [  Multiplayer ]
        // [     Mods     ]

        // Singleplayer
        gui.components.Button singleplayerButton = new gui.components.Button(session, new gui.text.Component().translation("menu.singleplayer"));
        singleplayerButton.setClickListener(()->toScreen("world_selection"));
        singleplayerButton.setY(gui.constraints.GapConstraint.gap(45));
        singleplayerButton.setSize(Constraint.pixel(200), Constraint.pixel(20)); //////////
        layout.put("singleplayer", singleplayerButton);

        // Multiplayer
        gui.components.Button multiplayerButton = new gui.components.Button(session, new gui.text.Component().translation("menu.multiplayer"));
        multiplayerButton.setClickListener(()->System.out.println("Multiplayer"));
        multiplayerButton.setY(gui.constraints.GapConstraint.gap(4));
        multiplayerButton.setSize(Constraint.pixel(200), Constraint.pixel(20)); ////////////
        layout.put("multiplayer", multiplayerButton);

        // Mods
        gui.components.Button modsButton = new gui.components.Button(session, new gui.text.Component().translation("menu.mods"));
        modsButton.setClickListener(()->System.out.println("Mods"));
        modsButton.setY(gui.constraints.GapConstraint.gap(4));
        modsButton.setSize(Constraint.pixel(200), Constraint.pixel(20)); /////////////
        layout.put("mods", modsButton);

        // <----------BUTTONS: 3 LINE---------->
        // [ Options... ] [ Quit Game ]

        // Horizontal Layout
        gui.components.Layout horizontalLayout = new gui.components.Layout();
        horizontalLayout.setY(GapConstraint.gap(15));
        horizontalLayout.setSize(Constraint.pixel(200), Constraint.pixel(20)); ///////////
        horizontalLayout.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("horizontal_layout", horizontalLayout);

        // Options...
        gui.components.Button optionsButton = new gui.components.Button(session, new gui.text.Component().translation("menu.jpize.tests.minecraft.options"));
        optionsButton.setClickListener(()->toScreen("options"));
        optionsButton.setSize(Constraint.pixel(97), Constraint.relative(1));
        horizontalLayout.put("options", optionsButton);

        // Quit Game
        gui.components.Button quitButton = new gui.components.Button(session, new gui.text.Component().translation("menu.quit"));
        quitButton.setClickListener(this::close);
        quitButton.alignSelf(Align.RIGHT);
        quitButton.setSize(Constraint.pixel(97), Constraint.relative(1));
        horizontalLayout.put("quit", quitButton);

        // <----------TEXTS---------->
        // < Version > < Copyright >

        // Texts Layout
        layoutTexts = new gui.components.BaseLayout();

        // Version (Minecraft 1.0.0)
        gui.components.TextView versionTextView = new gui.components.TextView(session, new gui.text.Component().formattedText("Minecraft §n1§r.§n01§r.§n0"));
        versionTextView.setPosition(Constraint.pixel(2));
        layoutTexts.put("version", versionTextView);

        // Copyright (Pashok AB)
        gui.components.TextView copyrightTextView = new TextView(session, new Component().formattedText("Copyright §oПашок§r AB. Do not distribute!"));
        copyrightTextView.setPosition(Constraint.pixel(1));
        copyrightTextView.alignSelf(Align.RIGHT_DOWN);
        layoutTexts.put("copyright", copyrightTextView);

    }

    @Override
    public void render(TextureBatch batch){
        // Panorama
        camera.getRotation().yaw -= Jpize.getDt() * 2;
        camera.update();
        skyBox.render(camera);

        // Panorama Overlay
        batch.setAlpha(0.8);
        batch.draw(panorama_overlay, 0, 0, Jpize.getWidth(), Jpize.getHeight());
        batch.setAlpha(1);
        
        // UI
        layout.render(batch);
        layoutTexts.render(batch);

        // Splash
        // ..Code
    }


    @Override
    public void onShow(){
        camera.getRotation().set(90, -25);
    }

    @Override
    public void resize(int width, int height){

    }


    @Override
    public void close(){
        Jpize.exit();
    }

    @Override
    public void dispose(){
        skyBox.dispose();
    }

    @Override
    public boolean shouldCloseOnEsc(){
        return false;
    }

    @Override
    public boolean renderDirtBackground(){
        return false;
    }

}
