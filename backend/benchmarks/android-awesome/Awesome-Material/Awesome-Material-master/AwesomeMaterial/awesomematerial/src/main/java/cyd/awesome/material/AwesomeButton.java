package cyd.awesome.material;

import java.util.Random;

/**
 * Created by daniellevass on 12/04/15.
 */
public class AwesomeButton {

    private String iconLeft;
    private String textMiddle;
    private String iconMiddle;
    private String iconRight;

    private boolean useRounded;

    private int textSize;


    private void setMiddleItem()
    {
        Random rand = new Random();
		if (rand.nextBoolean()){
        } else {

            if (rand.nextBoolean()) {
            }
        }
    }




    public String getUnicodeCharacterFromInt(int unicode){
        return new String(Character.toChars(unicode));
    }

    /*
    public void setFontAwesomeIcon(FontCharacterMaps.FontAwesome icon) {
        this.setText(icon.toString());
        this.setTypeface(FontUtil.getFontAwesome(getContext()));
    }

    public void setMaterialDesignIcon(FontCharacterMaps.MaterialDesign icon) {
        this.setText(icon.toString());
        this.setTypeface(FontUtil.getMaterialDesignFont(getContext()));
    }

    public void setPixedenStrokeIcon(FontCharacterMaps.Pixeden icon) {
        this.setText(icon.toString());
        this.setTypeface(FontUtil.getPixedenFont(getContext()));
    }*/

}
