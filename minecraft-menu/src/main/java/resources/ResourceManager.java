package resources;

import jpize.audio.sound.AudioBuffer;
import jpize.audio.sound.Sound;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontCharset;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureRegion;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import log.Logger;
import jpize.util.Disposable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager implements Disposable{

    private String location;
    private final Map<String, resources.Resource<?>> resources;
    private final TextureRegion unknownTexture;


    public ResourceManager(){
        resources = new HashMap<>();

        PixmapRGBA unknownPixmap = new PixmapRGBA(2, 2);
        unknownPixmap.clear(1, 0, 1, 1F);
        unknownPixmap.setPixel(0, 0, 0, 0, 0, 1F);
        unknownPixmap.setPixel(1, 1, 0, 0, 0, 1F);
        unknownTexture = new TextureRegion(new Texture(unknownPixmap));
    }


    public TextureRegion getTexture(String id){
        resources.Resource<?> resource = resources.get(id);
        if(resource == null)
            return unknownTexture;
        return (TextureRegion) (resource.isLoaded() ? resource.getResource() : unknownTexture);
    }

    public void putTexture(String id, String location, Rectangle region){
        resources.put(id, new TextureResource(this.location + location, region));
    }

    public void putTexture(String id, String location){
        putTexture(id, location, null);
    }


    public AudioBuffer getSound(String id){
        return (AudioBuffer) resources.get(id).getResource();
    }

    public void putSound(String id, String location){
        resources.put(id, new SoundResource(this.location + location));
    }


    public Sound getMusic(String id){
        return (Sound) resources.get(id).getResource();
    }

    public void putMusic(String id, String location){
        resources.put(id, new MusicResource(this.location + location));
    }


    public BitmapFont getFont(String id){
        return (BitmapFont) resources.get(id).getResource();
    }

    public void putFontFnt(String id, String location){
        resources.put(id, new FontResourceFnt(this.location + location));
    }

    public void putFontTtf(String id, String location, int size){
        resources.put(id, new resources.FontResourceTtf(this.location + location, size));
    }

    public void putFontTtf(String id, String location, int size, FontCharset charset){
        resources.put(id, new FontResourceTtf(this.location + location, size, charset));
    }


    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }


    public void load(){
        for(resources.Resource<?> resource: resources.values()){
            Logger.instance().info("Load " + resource.getClass().getSimpleName());
            resource.loadResource();
        }
    }

    public void reload(){
        for(resources.Resource<?> resource: resources.values())
            if(resource.isLoaded())
                resource.reloadResource();
            else
                resource.loadResource();
    }
    

    @Override
    public void dispose(){
        for(Resource<?> resource: resources.values())
            if(resource.isLoaded())
                resource.dispose();

        unknownTexture.getTexture().dispose();
    }

}
