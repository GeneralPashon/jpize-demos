import jpize.Jpize;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;

public class Window extends JpizeApplication{
    
    public static void main(String[] args){
        ContextBuilder.newContext("Hello, Window!")
                .size(1080, 640)
                .register()
                .setAdapter(new Window());

        Jpize.runContexts();
    }
    
    
    private TextureBatch batch;
    private Texture texture;
    
    @Override
    public void init(){
        batch = new TextureBatch();
        texture = new Texture("background.jpg");
    }
    
    
    @Override
    public void render(){
        Gl.clearColorBuffer();
        batch.begin();
        batch.draw(texture, 0, 0, Jpize.getWidth(), Jpize.getHeight());
        batch.end();
        
        if(Key.ESCAPE.isPressed())
            Jpize.exit();
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        texture.dispose();
    }
    
}
