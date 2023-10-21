package gui.text;

import gui.text.formatting.Style;
import jpize.graphics.font.FontCharset;
import jpize.math.Maths;

public class TextComponent extends Component{

    private String text;
    private gui.text.formatting.Style style;

    protected TextComponent(Component parent, String text, gui.text.formatting.Style style){
        this.text = text;

        if(parent != null && (style == gui.text.formatting.Style.DEFAULT || style == null))
            this.style = new gui.text.formatting.Style(parent.color, parent.style);
        else
            this.style = style;
    }


    public String getText(){
        if(style.obfuscated){
            StringBuilder obfuscatedBuilder = new StringBuilder();
            for(int i = 0; i < text.length(); i++)
                obfuscatedBuilder.append(FontCharset.DEFAULT.charAt(Maths.random(0, FontCharset.DEFAULT.size() - 1)));

            return obfuscatedBuilder.toString();
        }else if(style.obfuscated_numbers){
            StringBuilder obfuscatedBuilder = new StringBuilder();
            for(int i = 0; i < text.length(); i++)
                obfuscatedBuilder.append(FontCharset.NUMBERS.charAt(Maths.random(0, FontCharset.NUMBERS.size() - 1)));

            return obfuscatedBuilder.toString();
        }

        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public gui.text.formatting.Style getStyle(){
        return style;
    }

    public void setStyle(Style style){
        this.style = style;
    }

}
