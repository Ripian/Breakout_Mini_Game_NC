package LevelObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;

import java.util.logging.Level;

import GameStates.LevelState;

public class Brick {

    public PowerUp powerup;
    public int hitCount=1;// how much ball needs to hit brick, before it is destroyed
    public static int score;
    public final static int awardForBrickKill=100;

    //constant recalculation of position by Grid position is not worth save of space -> introducing x, y
    public float x;
    public float y;

    //need maximum accuracy, all variables are "double"
    public final static double BRICKSIDEOFFSETINPERCENTAGE=5;//always succesfully assign space in horizontal space for bricks
    public final static double SPACEBEETWENBRICKSINPERCENT=2;
    public static double sizeOfSideOffsetInPixels;

    public final static double BRICKSTOPOFFSETINPERCENTAGE=20;
    public final static double BRICKHEIGHTPERCENTAGE=5; // dont always succesfully space bricks in vertical space, depends on brick's leveldata grid(in LevelState class)
    public static double sizeOfTopOffsetInPixels;

    public static double BrickWidth;//width of each brick in pixels
    public static double BrickHeight;//height of each brick in pixels

    public static Bitmap threeHitBitmap;
    public static Bitmap twoHitBitmap;
    public static Bitmap oneHitBitmap;



    public Brick(){


    }

    public boolean managePhysics(Ball ball){ //returns true if collision occurs

        if(hitCount==0 || ball.madeHit) return false; //fool protection



        //no axis early out algorithm is used, no time to implement

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

        if((xOfBall+radiusOfBall)>(this.x+BrickWidth)){
            closestPointX=(int)(this.x+BrickWidth);
        }


        //clamp y axis


        if((yOfBall+radiusOfBall)<this.y){
            closestPointY = (int)this.y;
        }

        if((yOfBall+radiusOfBall)>(this.y+BrickHeight)){
            closestPointY = (int)(this.y+BrickHeight);
        }

        //closest point found by now


        int SquaredDistanceFromBallCenterAndClosestPoint = findSquaredDistanceBeetwen2Points
                (xOfBall+radiusOfBall,
                yOfBall+radiusOfBall,
                        closestPointX,
                        closestPointY);


        if((radiusOfBall*radiusOfBall)>SquaredDistanceFromBallCenterAndClosestPoint){

            //collision !!!
            //need to manage it

            if(ball.lastarget==this){
                return false;
            }else{
                ball.lastarget = this;
                ball.currentRestFrames=ball.maxRestFrames;
            }
            /*
            if(ball.logicallyInvincible) {
                ball.wasTouchedThisFrame = true;
                return true;//doesnt mean anything

            }
            */
            ball.logicallyInvincible=true;
            ball.wasTouchedThisFrame = true;

            LevelState.totalLevelBricksHitPoints--;
            hitCount--;
            if(hitCount==0){
                LevelState.score+=awardForBrickKill;
               // powerup.frozen=false;
            }

            ball.madeHit=true;
            //calculate interpenetration
            int interpenetration = ball.getRadius() - (int)Math.sqrt(findSquaredDistanceBeetwen2Points(ball.getX()+ball.getRadius(),ball.getY()+ball.getRadius(),closestPointX,closestPointY));




            interpenetration = 0;// needs too accurate(science like/8 ball pool collision physics), so 1)  make
            // recursive like solution or 2) make spaces beetwen bricks, dont use interpenetration, add some special rules to code

            //find if it sideCollision
            if((ball.previous_y+radiusOfBall)>y && (ball.previous_y+radiusOfBall)<(y+BrickWidth)){

                ball.setBallXSpeed(ball.getBallXSpeed()*(-1));

                if(ball.getBallXSpeed()>0) {
                    ball.setX(ball.getX() - interpenetration);
                }else{
                    ball.setX(ball.getX() + interpenetration);
                }
            } else{
              //it is not side collision
              //  ball.setBallXSpeed(ball.getBallXSpeed()*(-1));
                ball.setBallYSpeed(ball.getBallYSpeed()*(-1));



                if(ball.getBallYSpeed()>0) {
                    ball.setY(ball.getY() - interpenetration);
                }else{
                    ball.setY(ball.getY() + interpenetration);
                }


            }
            ball.increaseSpeed();






            return true;
        }


        //if we here, then we have no collision
        return false;

    }

    private boolean isLineIntersects(int ax,int ay, int bx, int by, int cx, int cy, int r){

        // parameters: ax ay bx by cx cy r
        ax -= cx;
        ay -= cy;
        bx -= cx;
        by -= cy;
        int a = (int)Math.pow(ax,2) + (int)Math.pow(ay,2) - (int)Math.pow(r,2);
        int b = 2*(ax*(bx - ax) + ay*(by - ay));
        int c = (int)Math.pow(bx - ax,2) + (int)Math.pow(by - ay,2);
        int disc = (int)Math.pow(b,2) - 4*a*c;
        if(disc <= 0) return false;
        double sqrtdisc = Math.sqrt(disc);
        double t1 = (-b + sqrtdisc)/(2*a);
        double t2 = (-b - sqrtdisc)/(2*a);
        if((0 < t1 && t1 < 1) || (0 < t2 && t2 < 1)) return true;
        return false;
    }


    public static int findSquaredDistanceBeetwen2Points(int x1, int y1,int x2, int y2){
        return (int)(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));


    }

    public void draw(Canvas canvas){
        //canvas.drawRect(x,y,(float)(x+BrickWidth),(float)(y+BrickHeight),bluePaint);

        Bitmap bitmapToDraw=null;

        switch(hitCount){
            case 0:
                return;

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

       // if(powerup!=null)powerup.draw(canvas);


        //i wont put try/catch for null bitmapToDraw, want to fail so can understand where problem
        canvas.drawBitmap(bitmapToDraw,x,y,null);




    }





    //getters setters

    public PowerUp getPowerUp(){return powerup;}

    public int getAmountOfHitsLeft(){return hitCount;}
    public void decreaseHealth(){hitCount--;}
    public double getSideOffset(){return sizeOfSideOffsetInPixels;}
    public double getTopOffset(){return sizeOfTopOffsetInPixels;}








    /*
    public static void set3HitBitmap(Bitmap bitmap){


    }

    public static void set2HitBitmap(Bitmap bitmap){


    }

    public static void set1HitBitmap(Bitmap bitmap){


    }
    */

}
