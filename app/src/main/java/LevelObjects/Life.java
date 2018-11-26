package LevelObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import CustomButtons.RectangleButtom;
import GameStates.GameState;
import GameStates.GameStateManager;

public class Life {

    public static int amountOfLifes=3;
    private static final int maximumPossibleHP=7;
    private Bitmap hearth;
    private int x;//used for drawing, inaccurate
    private int y;//used for drawing, inaccurate
    private int width;
    private int height;






    private RectangleButtom stringRepresentatationButtom;

    public Life(int x, int y, int width, int height, Bitmap hearhBitmap,

                GameStateManager gsm,
                GameState.GameStateName nextGameStateOnClick,
                GameState ownerState,
                double percentageOfXToScreen,
                double percentageOfYToScreen,
                double percentageofWithToScreen,
                double percentageOfHeightToScreen,
                Bitmap rawBitmapNormal,
                Bitmap rawBitmapPressed,
                String stringToDraw,
                Paint textPaint   ){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height = height;
        hearth = Bitmap.createScaledBitmap(hearhBitmap, width, height, false);


        stringRepresentatationButtom = new RectangleButtom( gsm,
                 nextGameStateOnClick,
                 ownerState,
                 percentageOfXToScreen,
                 percentageOfYToScreen,
                 percentageofWithToScreen,
                 percentageOfHeightToScreen,
                 rawBitmapNormal,
                 rawBitmapPressed,
                 stringToDraw,
                 textPaint);




    }

    public void increaseHP(){
        amountOfLifes++;
        if(amountOfLifes>maximumPossibleHP){
            amountOfLifes=maximumPossibleHP;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(hearth,x+(int)stringRepresentatationButtom.getWidth(),y,null);

        stringRepresentatationButtom.setButtomText(amountOfLifes+"X");
        stringRepresentatationButtom.draw(canvas);
    }


}
