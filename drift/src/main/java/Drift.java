import jpize.Jpize;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.graphics.camera.CenteredOrthographicCamera;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;

public class Drift extends JpizeApplication{

    public static void main(String[] args){
        ContextBuilder.newContext(1280, 720, "Drift")
                .register()
                .setAdapter(new Drift());

        Jpize.runContexts();
    }


    private final Car car;
    private final TextureBatch batch;
    private final CenteredOrthographicCamera camera;
    private final Texture roadTexture;

    public Drift(){
        car = new Car();
        batch = new TextureBatch();
        camera = new CenteredOrthographicCamera();
        roadTexture = new Texture("road.png");
    }

    public void update(){
        if(Key.ESCAPE.isDown())
            Jpize.exit();

        car.update();

        camera.getPosition().set(car.getPosition());//.rotDeg(-car.getRotation()).add(0, 130);
        //camera.setRotation(-car.getRotation());
        camera.setScale(0.4F);
        camera.update();
    }

    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.3, 1);
        batch.begin(camera);

        batch.draw(roadTexture, -200, -2000, 400, 4000);
        car.render(batch);

        batch.end();
    }

    public void resize(int width, int height){
        camera.resize(width, height);
    }

    public void dispose(){
        car.dispose();
        batch.dispose();
        roadTexture.dispose();
    }

}
