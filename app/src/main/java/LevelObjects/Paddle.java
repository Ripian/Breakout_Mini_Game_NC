package LevelObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;

public class Paddle {

    //coordinates show top left corner
    public int x;
    public int y;
    public int r;
    public int h;
    public Ball ball;
    private Bitmap paddleBitmap;
    private Bitmap leftBall;
    private Bitmap rightBall;
    public final int RADIUSPERCENTALTOSCREENWIDTH=5;
    public final int PADDLEWIDTHTOSCREENWIDTH=10;

    public final int OFFSETFROMBOOTOMINPERCENT=5;





    public Paddle(){

        this.r = (int)(MainGameWindow.displayWidth*RADIUSPERCENTALTOSCREENWIDTH/100);
        this.h = (int)(MainGameWindow.displayWidth*PADDLEWIDTHTOSCREENWIDTH/100);



        this.x=(int)(MainGameWindow.displayWidth/2 - r - h/2);
        this.y=(int)(MainGameWindow.displayheight-MainGameWindow.displayheight*OFFSETFROMBOOTOMINPERCENT/100);
        this.y= this.y ;
    }


    public void setLeftBallBitmap(Bitmap bitmap){
        this.leftBall = Bitmap.createScaledBitmap(bitmap, r, r*2, false);
    }

    public void setRightBallBitmap(Bitmap bitmap){
        this.rightBall = Bitmap.createScaledBitmap(bitmap, r, r*2, false);

    }

    public void setPaddleBitmap(Bitmap bitmap){
        this.paddleBitmap = Bitmap.createScaledBitmap(bitmap, h, r*2, false);

    }



    public void update(){
        managePhysics(ball);



    }


    public void draw(Canvas canvas){
        canvas.drawBitmap(leftBall,x,y,null);
        canvas.drawBitmap(rightBall,x+r+h,y,null);
        canvas.drawBitmap(paddleBitmap,x+r,y,null);

    }


    public void managePhysics(Ball ball){

        manageBallCollision(ball);

    }



    private void manageBallCollision(Ball ball){
        //////manage collision with rectangle
        /////
        /////

        int centerOfPaddleX = x+r+h/2;
        int centerOfPaddleY =y-r;


       if(manageCollisionWithRectangle(centerOfPaddleX, centerOfPaddleY)){


           return;
       }

    }





    public boolean manageCollisionWithPowerUp(PowerUp power){
        //find closes point
        int xOfBall = power.x;
        int yOfBall = power.y;
        int radiusOfBall = power.radius;


        int closestPointX= xOfBall+radiusOfBall;//center of ballX
        int closestPointY= yOfBall+radiusOfBall;//center of bally
        //clamp X axis

        if((xOfBall+radiusOfBall)<this.x){
            closestPointX=(int)this.x;
        }

        if((xOfBall+radiusOfBall)>(this.x+r+r+h)){
            closestPointX=(int)(this.x+r+r+h);
        }


        //clamp y axis


        if((yOfBall+radiusOfBall)<this.y){
            closestPointY = (int)this.y;
        }

        if((yOfBall+radiusOfBall)>(this.y+r+r)){
            closestPointY = (int)(this.y+r+r);
        }

        //closest point found by now


        int SquaredDistanceFromBallCenterAndClosestPoint = findSquaredDistanceBeetwen2Points
                (xOfBall+radiusOfBall,
                        yOfBall+radiusOfBall,
                        closestPointX,
                        closestPointY);

        if((radiusOfBall*radiusOfBall)>SquaredDistanceFromBallCenterAndClosestPoint) {
            //collision !!!
            //need to manage it
            //ball.setY(y-ball.getRadius()-ball.getRadius());
            //ball.setBallYSpeed(ball.getBallYSpeed()*-1);
            int forceVectorX = (xOfBall+radiusOfBall)-(x+r+h/2);
            int forceVectorY = (yOfBall+radiusOfBall)-(y+r);
            ball.setAndNormalizeSpeed(forceVectorX,forceVectorY);


            return true;
        }


        return false;
    }

    private boolean manageCollisionWithRectangle(int centerOfPaddleX, int centerOfPaddleY){
        //find closes point
        int xOfBall = ball.getX();
        int yOfBall = ball.getY();
        int radiusOfBall = ball.getRadius();


        int closestPointX= xOfBall+radiusOfBall;//center of ballX
        int closestPointY= yOfBall+radiusOfBall;//center of bally
        //clamp X axis

        if((xOfBall+radiusOfBall)<this.x){
            closestPointX=(int)this.x;
        }

        if((xOfBall+radiusOfBall)>(this.x+r+r+h)){
            closestPointX=(int)(this.x+r+r+h);
        }


        //clamp y axis


        if((yOfBall+radiusOfBall)<this.y){
            closestPointY = (int)this.y;
        }

        if((yOfBall+radiusOfBall)>(this.y+r+r)){
            closestPointY = (int)(this.y+r+r);
        }

        //closest point found by now


        int SquaredDistanceFromBallCenterAndClosestPoint = findSquaredDistanceBeetwen2Points
                (xOfBall+radiusOfBall,
                        yOfBall+radiusOfBall,
                        closestPointX,
                        closestPointY);

        if((radiusOfBall*radiusOfBall)>SquaredDistanceFromBallCenterAndClosestPoint) {
            //collision !!!
            //need to manage it
            //ball.setY(y-ball.getRadius()-ball.getRadius());
            //ball.setBallYSpeed(ball.getBallYSpeed()*-1);
            int forceVectorX = (xOfBall+radiusOfBall)-(x+r+h/2);
            int forceVectorY = (yOfBall+radiusOfBall)-(y+r);
            ball.setAndNormalizeSpeed(forceVectorX,forceVectorY);


            return true;
        }


        return false;
    }




    public static int findSquaredDistanceBeetwen2Points(int x1, int y1,int x2, int y2){
        return (int)(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));


    }



    public void OnTouchEvent(MotionEvent event){


        if(event.getAction() == MotionEvent.ACTION_UP){

            manageClick(event);
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){


        }


        manageMovementByTouch(event);


    }

    private void manageClick(MotionEvent event){
        ball.isGluedToPaddle=false;

    }

    private void manageMovementByTouch(MotionEvent event){
        //set coords

        this.x = (int)(event.getX()-r-h/2);


        //normalize


        if(x<0) x=0;

        if(x+r+r+h>MainGameWindow.displayWidth)x=(int)(MainGameWindow.displayWidth-r-r-h);

    }



}
