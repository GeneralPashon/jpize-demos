import jpize.Jpize;
import jpize.glfw.key.Key;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;
import jpize.math.Maths;

public class Noise extends JpizeApplication{
    
    public static void main(String[] args){
        ContextBuilder.newContext(720, 720, "Noise")
                .register()
                .setAdapter(new Noise());

        Jpize.runContexts();
    }
    
    
    private final TextureBatch batch;
    private final Texture mapTexture;
    
    public Noise(){
        batch = new TextureBatch();

        final PixmapRGBA pixmap = new PixmapRGBA(2048, 2048);
        for(int x = 0; x < pixmap.getWidth(); x++){
            for(int y = 0; y < pixmap.getHeight(); y++){
                float grayscale = OpenSimplex2S.noise2_ImproveX(22854, x / 256F, y / 256F);
                grayscale = Maths.map(grayscale, -1, 1, 0, 1);
                pixmap.setPixel(x, y, grayscale, grayscale, grayscale, 1);
            }
        }
        
        mapTexture = new Texture(pixmap);
    }
    
    public void render(){
        batch.begin();
        batch.draw(mapTexture, 0, 0, Jpize.getWidth(), Jpize.getHeight());
        batch.end();
        
        if(Key.ESCAPE.isDown())
            Jpize.exit();
    }
    
}