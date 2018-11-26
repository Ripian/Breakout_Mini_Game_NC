package LevelObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;

public class PowerUp { // no time to finish

    public int x;
    public int y;
    public int radius;//simplified circle boundingbox
    public boolean frozen=true;
    public boolean waitingForCleaning=false;
    private Paddle paddle;
    private Bitmap image;

    private final static int FALLSPEEDPERCENT=1;
    private static int fallSpeedPixels;


    public enum PowerUpName {
        HEALTH("Health"),
        BALLPOWER("BallPower");

        public final String nameOfPowerUp;

        PowerUpName(String nameOfState){
            this.nameOfPowerUp = nameOfState;

        }
    }


    public PowerUpName typeOfPower;

    private int redutionOfWidthFromBrick=30;


    public PowerUp(int x, int y, Bitmap powerUpImage,PowerUpName typeOfPower){
        this.x=x+(int)(MainGameWindow.displayWidth/(redutionOfWidthFromBrick/2)*100);
        this.y=y;
        this.typeOfPower = typeOfPower;
        this.image = Bitmap.createScaledBitmap(powerUpImage, (int)(Brick.BrickWidth-MainGameWindow.displayWidth/(redutionOfWidthFromBrick)*100), (int)Brick.BrickHeight, false);;


    }


    public void draw(Canvas canvas){
        if(!frozen && !waitingForCleaning)
        canvas.drawBitmap(image,x,y,null);


    }


    public void update(){
        if(frozen) return;
        y+=fallSpeedPixels;



        if(y+radius+radius>MainGameWindow.displayheight){
            waitingForCleaning=true;
        }

        //cheack if found ground



    }


    public static void initStatics(){
        fallSpeedPixels = (int)(MainGameWindow.displayheight*FALLSPEEDPERCENT/100);


    }


}
