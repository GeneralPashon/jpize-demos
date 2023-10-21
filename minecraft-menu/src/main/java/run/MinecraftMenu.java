package run;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.glenum.GlDepthFunc;
import jpize.gl.glenum.GlTarget;
import jpize.gl.tesselation.GlFace;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.glfw.key.Key;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;
import log.Logger;

public class MinecraftMenu extends JpizeApplication{

    public static void main(String[] args){
        Jpize.execSync(() ->
            ContextBuilder.newContext(925, 640, "Minecraft Menu")
                .icon("icon.png")
                .register().setAdapter(new MinecraftMenu())
        );

        Jpize.runContexts();
    }

    private Session session;

    @Override
    public void init(){
        Gl.clearColor(1, 1, 1);

        Gl.enable(GlTarget.DEPTH_TEST);
        Gl.depthFunc(GlDepthFunc.LEQUAL);
        Thread.currentThread().setName("Render Thread");

        this.session = new Session();

        Jpize.closeOtherWindows();
    }

    @Override
    public void render(){
        if(Key.NUM_1.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.FILL);
        if(Key.NUM_2.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.LINE);
        if(Key.NUM_3.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.POINT);

        Gl.clearColorDepthBuffers();
        session.render();
    }

    @Override
    public void resize(int width, int height){
        session.resize(width, height);
    }

    @Override
    public void dispose(){
        session.dispose();

        Logger.instance().info("EXIT.");
    }

}
