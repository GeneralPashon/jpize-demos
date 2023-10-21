package gui.text.formatting;

import jpize.graphics.util.color.ImmutableColor;

public enum TextFormatting{

    BLACK              ('0', new gui.text.formatting.ColorFormatting(0  , 0  , 0  )),
    DARK_BLUE          ('1', new gui.text.formatting.ColorFormatting(0  , 0  , 170)),
    DARK_GREEN         ('2', new gui.text.formatting.ColorFormatting(0  , 170, 0  )),
    DARK_AQUA          ('3', new gui.text.formatting.ColorFormatting(0  , 170, 170)),
    DARK_RED           ('4', new gui.text.formatting.ColorFormatting(170, 0  , 0  )),
    DARK_PURPLE        ('5', new gui.text.formatting.ColorFormatting(170, 0  , 170)),
    GOLD               ('6', new gui.text.formatting.ColorFormatting(255, 170, 0  )),
    GRAY               ('7', new gui.text.formatting.ColorFormatting(170, 170, 170)),
    DARK_GRAY          ('8', new gui.text.formatting.ColorFormatting(85 , 85 , 85 )),
    BLUE               ('9', new gui.text.formatting.ColorFormatting(85 , 85 , 255)),
    GREEN              ('a', new gui.text.formatting.ColorFormatting(85 , 255, 85 )),
    AQUA               ('b', new gui.text.formatting.ColorFormatting(85 , 255, 255)),
    RED                ('c', new gui.text.formatting.ColorFormatting(255, 85 , 85 )),
    LIGHT_PURPLE       ('d', new gui.text.formatting.ColorFormatting(255, 85 , 255)),
    YELLOW             ('e', new gui.text.formatting.ColorFormatting(255, 255, 85 )),
    WHITE              ('f', new gui.text.formatting.ColorFormatting(255, 255, 255)),

    //                  BISON XDDD
    BOLD               ('b', gui.text.formatting.StyleFormatting.BOLD              ),
    ITALIC             ('i', gui.text.formatting.StyleFormatting.ITALIC            ),
    UNDERLINE          ('u', gui.text.formatting.StyleFormatting.UNDERLINE         ),
    STRIKETHROUGH      ('s', gui.text.formatting.StyleFormatting.STRIKETHROUGH     ),
    OBFUSCATED         ('o', gui.text.formatting.StyleFormatting.OBFUSCATED        ),
    OBFUSCATED_NUMBERS ('n', gui.text.formatting.StyleFormatting.OBFUSCATED_NUMBERS),

    RESET              ('r', null);


    public static final char FORMATTING_SYMBOL = '§';


    public final char code;
    public final gui.text.formatting.ITextFormatting formatting;

    TextFormatting(char code, ITextFormatting formatting){
        this.code = code;
        this.formatting = formatting;
    }

    public ImmutableColor color(){
        return ((gui.text.formatting.ColorFormatting) formatting).getColor();
    }

    public gui.text.formatting.StyleFormatting style(){
        return (gui.text.formatting.StyleFormatting) formatting;
    }


    public boolean isStyle(){
        return formatting.getClass().equals(StyleFormatting.class);
    }

    public boolean isColor(){
        return formatting.getClass().equals(ColorFormatting.class);
    }


    public static TextFormatting fromCode(char code){
        for(TextFormatting textFormatting: TextFormatting.values())
            if(textFormatting.code == code)
                return textFormatting;

        return null;
    }

}
