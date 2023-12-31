package entity;

import jpize.Jpize;
import jpize.glfw.key.Key;
import jpize.graphics.util.batch.TextureBatch;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2f;
import jpize.physic.axisaligned.rect.AARect;
import jpize.physic.axisaligned.rect.AARectBody;
import jpize.physic.axisaligned.rect.AARectCollider;
import map.MapTile;
import map.WorldMap;
import world.World;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity{

    private static final AARect TILE_BOUNDING_RECT = new AARect(0, 0, 1, 1);

    private final List<AARectBody> rectList;

    public Player(){
        super(new AARect(0, 0, 2, 3));
        rectList = new ArrayList<>();
        getVelocity().setMax(50);
    }


    public void render(TextureBatch batch){
        batch.drawQuad(1, 1, 1, 1,  getPosition().x, getPosition().y, rect().getWidth(), rect().getHeight());
        
        for(AARectBody r: rectList)
            batch.drawQuad(1, 0, 0, 0.5,  r.getMin().x, r.getMin().y, r.rect().getWidth(), r.rect().getHeight());
    }

    public void update(World world){
        WorldMap tileMap = world.getTileMap();

        // Getting the nearest tiles

        AARectBody[] rects = getRects(tileMap, new Vec2f(), 1);

        final boolean isCollideUp = isCollide(0, Float.MIN_VALUE, rects);
        final boolean isCollideDown = isCollide(0, -Float.MIN_VALUE, rects);
        final boolean isCollideLeft = isCollide(-Float.MIN_VALUE, 0, rects);
        final boolean isCollideRight = isCollide(Float.MIN_VALUE, 0, rects);

        // Moving

        float delta = Jpize.getDt();

        if(Key.A.isPressed())
            getVelocity().x -= 0.7;
        if(Key.D.isPressed())
            getVelocity().x += 0.7;

        // Auto jump

        if(isCollideDown && !isCollideUp){
            AARectBody rectBody = this.copy();
            rectBody.getPosition().y++;

            if(
                (getVelocity().x > 0 && isCollideRight
                && !AARectCollider.getCollidedMovement(new Vec2f(Float.MIN_VALUE, 0), rectBody, rects).isZero())
            ||
                (getVelocity().x < 0 && isCollideLeft
                && !AARectCollider.getCollidedMovement(new Vec2f(-Float.MIN_VALUE, 0), rectBody, rects).isZero()
            ))
                getVelocity().y = 21;
        }

        // Gravity & Jump

        getVelocity().y -= 2;

        if(Key.SPACE.isPressed() && isCollideDown)
            getVelocity().y = 50;

        // Process collisions

        Vec2f motion = getVelocity().copy().mul(delta);
        rects = getRects(tileMap, motion, 0);
        Vec2f collidedVel = AARectCollider.getCollidedMovement(motion, this, rects);

        getVelocity().reduce(0.5);
        getVelocity().collidedAxesToZero(collidedVel);
        getVelocity().clampToMax();

        getPosition().add(collidedVel);
    }

    public boolean isCollide(float x, float y, AARectBody[] rects){
        return AARectCollider.getCollidedMovement(new Vec2f(x, y), this, rects).isZero();
    }

    public AARectBody[] getRects(WorldMap map, Vec2f vel, float padding){
        rectList.clear();
        for(int i = Maths.floor(getMin().x + vel.x - padding); i < getMax().x + vel.x + padding; i++)
            for(int j = Maths.floor(getMin().y + vel.y - padding); j < getMax().y + vel.y + padding; j++){

                MapTile tile = map.getTile(i, j);
                if(tile != null && tile.getType().collidable){
                    AARectBody body = new AARectBody(TILE_BOUNDING_RECT);
                    body.getPosition().set(i, j);
                    rectList.add(body);
                }
            }

        return rectList.toArray(new AARectBody[0]);
    }

}
