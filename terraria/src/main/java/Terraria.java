import entity.Player;
import graphics.GameRenderer;
import jpize.Jpize;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2f;
import map.MapTile;
import world.World;

import static tile.TileType.AIR;
import static tile.TileType.DIRT;

public class Terraria extends JpizeApplication{

    public static void main(String[] args){
        ContextBuilder.newContext(1280, 720, "Terraria")
                .icon("icon.png")
                .register()
                .setAdapter(new Terraria());

        Jpize.runContexts();
    }


    private World world;
    private GameRenderer gameRenderer;

    private TextureBatch batch;
    private BitmapFont font;
    private Player player;
    
    @Override
    public void init(){
        gameRenderer = new GameRenderer();
        world = new World(300, 100);

        player = new Player();
        player.getPosition().set(world.getTileMap().getWidth() / 2F - 1.5F, world.getTileMap().getHeight());
        world.getEntities().add(player);

        batch = new TextureBatch();
        font = FontLoader.loadTrueType("font/andy_bold.ttf", 32);
    }

    @Override
    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.14, 0.43, 0.8);
        ctrl();

        batch.begin();
        gameRenderer.update();
        gameRenderer.renderMap(world.getTileMap());
        gameRenderer.renderEntities(world.getEntities());
        player.update(world);

        font.drawText(batch, "fps: " + Jpize.getFPS(), 10, 10);
        batch.end();

        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreen();
    }

    private void ctrl(){
        final float scroll = Jpize.mouse().getScroll();
        gameRenderer.getRenderInfo().mulScale(
            scroll < 0 ?
                1 / Math.pow(1.1, Maths.abs(scroll)) : scroll > 0 ?
                Math.pow(1.1, Maths.abs(scroll))
                : 1
        );

        gameRenderer.getCamera().getPosition().set( player.getPosition().copy().add(player.rect().getCenter()) );

        Vec2f touch = new Vec2f(Jpize.getX(), Jpize.getY())
            .sub(gameRenderer.getCamera().getWidth() / 2F, gameRenderer.getCamera().getHeight() / 2F)
            .div(gameRenderer.getRenderInfo().getCellSize() * gameRenderer.getRenderInfo().getScale())
            .add(gameRenderer.getCamera().getPosition());

        MapTile tile = world.getTileMap().getTile(touch.xf(), touch.yf());
        if(tile != null && Jpize.isTouched())
            tile.setType(Jpize.mouse().isLeftPressed() ? AIR : DIRT);

        if(player.getPosition().y < -100)
            player.getPosition().y = world.getTileMap().getHeight();
    }

    @Override
    public void resize(int width, int height){
        gameRenderer.getCamera().resize(width, height);
    }

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }

}
