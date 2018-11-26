package LevelObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;

public class Ball {




    private int radius;
    public int previous_x;
    public int previous_y;
    public boolean isGluedToPaddle=true;
    private int x;
    private int y;
    private double INCREASEOFSPEEDPERCENT=0.02;
    private double increaseOfSpeedInPixels;
    private double currentSpeed;
    private double XSpeed=0;
    private double YSpeed;
    public double INITIALSPEEDINPERCENT = 0.5;
    public double MAXSPEEDINPERCENT=1.5;
    private double maxSpeed;
    public int currentRestFrames=0;
    public int maxRestFrames=3;
    public Brick lastarget;
    public boolean isDead;


    public int hitPower=1;
    public boolean madeHit=false;
    public boolean logicallyInvincible= false;
    public boolean wasTouchedThisFrame =false;
    private Paddle paddle;

    private Bitmap oneHitBitmap;
    private Bitmap twoHitBitmap;
    private Bitmap threeHitBitmap;

    public Ball(int radius,
                int x,
                int y,
                Paddle paddle){
        this.radius = radius;
        this.x=x;
        this.y=y;
        this.paddle=paddle;
        this.currentSpeed = MainGameWindow.displayheight*INITIALSPEEDINPERCENT/100.0;
        this.YSpeed=currentSpeed;

        this.maxSpeed = MainGameWindow.displayheight*MAXSPEEDINPERCENT/100.0;
        this.increaseOfSpeedInPixels = MainGameWindow.displayheight*INCREASEOFSPEEDPERCENT/100.0;


    }



    public void setOneHitBitmap(Bitmap oneHitBitmap){
        this.oneHitBitmap = Bitmap.createScaledBitmap(oneHitBitmap, (int)radius*2, (int)radius*2, false);

    }

    public void setTwoHitBitmap(Bitmap twoHitBitmap){
        this.twoHitBitmap = Bitmap.createScaledBitmap(twoHitBitmap, (int)radius*2, (int)radius*2, false);
    }

    public void setThreeHitBitmap(Bitmap threeHitBitmap){
        this.threeHitBitmap = Bitmap.createScaledBitmap(threeHitBitmap, (int)radius*2, (int)radius*2, false);

    }

    public void update(){
        currentRestFrames--;
        if(currentRestFrames<0){
            lastarget=null;
            currentRestFrames=0;
        }
        if(isGluedToPaddle){
            x= paddle.x+paddle.r+paddle.h/2-radius;
            y= paddle.y-radius-radius;
        }

        if(!wasTouchedThisFrame) {
            previous_x = x;
            previous_y = y;
        }
        x+=XSpeed;
        y+=YSpeed;

        cheackWallsCollision();

    }


    public void cheackWallsCollision(){
        if(x<0){
            x=0;
            XSpeed*=-1;
        }

        if(y<0){
            y=0;
            YSpeed*=-1;
        }

        if((x+2*radius)>MainGameWindow.displayWidth){
            x=(int)MainGameWindow.displayWidth-2*radius;
            XSpeed*=-1;

        }


        //should loose here



        if((y+2*radius)>MainGameWindow.displayheight){
            Life.amountOfLifes--;
            isGluedToPaddle=true;

        }

    }

    public void draw(Canvas canvas){
        Bitmap bitmapToDraw=null;

        switch(hitPower){


            case 1:
                bitmapToDraw=oneHitBitmap;
                break;
            case 2:
                bitmapToDraw=twoHitBitmap;
                break;
            case 3:
                bitmapToDraw=threeHitBitmap;
                break;
            default:
                System.out.print("error in drawing bricks, no brick Bitmap found for drawing with current hitCount");
        }


        //i wont put try/catch for null bitmapToDraw, want to fail so can understand where problem
        canvas.drawBitmap(bitmapToDraw,x,y,null);


    }







    public void increaseSpeed(){
        currentSpeed+=increaseOfSpeedInPixels;
        normalizeSpeed(currentSpeed);

    }




    public void normalizeSpeed(double C){

        double ratio= XSpeed/YSpeed;
        double YSpeedRAT = 100/(ratio+1);
        double XSpeedRAT = 100-YSpeedRAT;

        if(XSpeed>0){
            XSpeed+=increaseOfSpeedInPixels*XSpeedRAT/100;

        }else{
            XSpeed-=increaseOfSpeedInPixels*XSpeedRAT/100;
        }


        if(YSpeed>0){
            YSpeed+=increaseOfSpeedInPixels*YSpeedRAT/100;

        }else{
            YSpeed-=increaseOfSpeedInPixels*YSpeedRAT/100;
        }


    }

    public void setAndNormalizeSpeed(double proportionX, double proportionY){
        double lenght = Math.sqrt(proportionX*proportionX+proportionY*proportionY);
        this.XSpeed = (proportionX/lenght)*currentSpeed;
        this.YSpeed = (proportionY/lenght)*currentSpeed;



    }


    //setters getters

    public double getBallXSpeed(){return XSpeed;}
    public double getBallYSpeed(){return YSpeed;}
    public void setBallXSpeed(double XSpeed){this.XSpeed = XSpeed;}
    public void setBallYSpeed(double YSpeed){this.YSpeed = YSpeed;}
    public void setX(int x){this.x=x;}
    public void setY(int y){this.y=y;}

    public void IncreaseHitPower(){
        hitPower++;
        if(hitPower>3)hitPower=3;
    }



    public int getRadius(){return radius;}
    public int getX(){return x;}
    public int getY(){return y;}

}
