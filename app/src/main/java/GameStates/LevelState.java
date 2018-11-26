package GameStates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;
import com.example.stefan.breakout_mini_game_nc.R;

import java.util.Random;

import CustomButtons.Buttom;
import CustomButtons.CircleButton;
import CustomButtons.RectangleButtom;
import CustomButtons.SoundToggleButtom;
import LevelObjects.Ball;
import LevelObjects.Brick;
import LevelObjects.Life;
import LevelObjects.Paddle;
import LevelObjects.PowerUp;

public final class LevelState extends GameState {

    private String LevelDataPath;
    public Brick[][] levelData;
     /*
     /ideally here app should take data from
     /some file, but this takes a
     /lot of time to implement
      so I introduce
     /random bricks placement
     */

     public static boolean isGameOver=false;


     private int PERCENTAGEOFMENIUBUTTOMX=2;
     private int PERCENTAGEOFMENIUBUTTOMY=2;
     private int PERCENTAGERADIUSOFMENIUBUTTOMTOWIDTH=5;

     private int PERCENTAGEOFMUSICBUTTOMX=88;
     private int PERCENTAGEOFMUSICBUTTOMY=2;
     private int PERCENTAGERADIUSOFSOUNDBUTTOMWIDTH=5;

     private int PERCENTAGEOFLIFEICONX=88;
     private int PERCENTAGEOFLIFEICONY=2;
     private int PERCENTAGEOFLIFEICONWIDTH=5;
     private int PERCENTAGEOFLIFEICONHEIGHT=5;


     private RectangleButtom invisibleScoreButtom;//using buttom for beneficts of built in positioning calculation
     private int SCOREXPERCENTAGE=50;
     private int SCOREYPERCENTAGE=2;
     private int SCOREWIDTHPERC=10;
     private int SCOREHEIGHTPERC=10;






     private CircleButton meniuButtom;
     private SoundToggleButtom soundButtom;

     public static int totalLevelBricksHitPoints=0;
     public static int score=0;
     public int PowerUpPerBricks = 2;
     public int twoHitBricksPerBricks = 10;
     public int threeHitBrickPerBricks = 5;//threeHitBricks overwrite 2 hit bricks if needed




    private int Customtimer=0;
    private boolean isWin=false;

    private Life lifeBar;
    private int LIFEBARPERCENTAGEX=15;
    private int LIFEBARPERCENTAGEY=2;
    private int LIFEBARPERCENTAGEWIDTH=10;
    private int LIFEBARPERCENTAGEHEIGHT=15;
    private int LIFEBARTEXTPERCX=13;
    private int LIFEBARTEXTPERCY=2;
    private int LIFEBARTEXTWIDTHPERC=10;
    private int LIFEBARTEXTHEIGHTPERC=10;


    public static int amountOfHitsLeft;
    private Ball ball;
    private int percentOfRadiusForBall=2;
    private Paddle paddle;

    //calculated from to left most brick
    public static final int GridWidth=16;
    public static final int GridHeight=4;



    private Bitmap winBitmap;


    public LevelState(GameStateManager gsm,
                      GameState.GameStateName gameStateName,
                      Bitmap oneHitBitmap,
                      Bitmap twoHitBitmap,
                      Bitmap threeHitBitmap){
        super(gsm, gameStateName);
        //init background bitmap
        Bitmap rawBackgroundBitmap = BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.level_wall_paper);
        this.Background = Bitmap.createScaledBitmap(rawBackgroundBitmap,
                (int)MainGameWindow.displayWidth,
                (int)MainGameWindow.displayheight,
                false);

        //init buttoms
        meniuButtom= new CircleButton(gsm,
                GameState.GameStateName.INGAMEMENIU,
                this,
                PERCENTAGEOFMENIUBUTTOMX,
                PERCENTAGEOFMENIUBUTTOMY,
                PERCENTAGERADIUSOFMENIUBUTTOMTOWIDTH,
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.pff_game_meniu_buttom),
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.pressed_game_meniu_buttom),
                null,
                null);


        soundButtom = new SoundToggleButtom(gsm,
                null,
                this,
                PERCENTAGEOFMUSICBUTTOMX,
                PERCENTAGEOFMUSICBUTTOMY,
                PERCENTAGERADIUSOFSOUNDBUTTOMWIDTH,
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.on_sound),
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.off_sound),
                null,
                null);






        /////init other things

        this.winBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.win),
                (int)MainGameWindow.displayWidth,
                (int)MainGameWindow.displayheight,
                false);




        //init lifebar and score

        Paint paintForLifeBarText = new Paint();
        paintForLifeBarText.setColor(Color.BLACK);

        lifeBar= new Life(LIFEBARPERCENTAGEX*(int)MainGameWindow.displayWidth/100,
                LIFEBARPERCENTAGEY*(int)MainGameWindow.displayheight/100,
                LIFEBARPERCENTAGEWIDTH*(int)MainGameWindow.displayWidth/100,
                LIFEBARPERCENTAGEHEIGHT*(int)MainGameWindow.displayheight/100,
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.hearth),
                gsm,
                null,
                this,
                LIFEBARTEXTPERCX,
                LIFEBARTEXTPERCY,
                LIFEBARTEXTWIDTHPERC,
                LIFEBARTEXTHEIGHTPERC,
                null,
                null,
                "1",
                paintForLifeBarText);


        invisibleScoreButtom = new RectangleButtom(gsm,
                null,
                this,
                SCOREXPERCENTAGE,
                SCOREYPERCENTAGE,
                SCOREWIDTHPERC,
                SCOREHEIGHTPERC,
                null,
                null,
                "0",
                paintForLifeBarText);


        //init paddle
        paddle = new Paddle();
        paddle.setLeftBallBitmap(BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.paddle_left));
        paddle.setRightBallBitmap(BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.paddle_right));
        paddle.setPaddleBitmap(BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.paddle_main));

        //init ball
        int radiusOfBallInPixels= (int)(MainGameWindow.displayWidth*percentOfRadiusForBall/100);

        ball = new Ball(radiusOfBallInPixels,
                (int)(MainGameWindow.displayWidth/2-radiusOfBallInPixels*2),
                (int)(MainGameWindow.displayheight/2-radiusOfBallInPixels*2),
                paddle);
        paddle.ball=ball;
        ball.setOneHitBitmap(BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.ball_one));
        ball.setTwoHitBitmap(BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.ball_two));
        ball.setThreeHitBitmap(BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.ball_three));






        //init bricks
        Brick.sizeOfSideOffsetInPixels=(int)(MainGameWindow.displayWidth*Brick.BRICKSIDEOFFSETINPERCENTAGE/100);
        Brick.sizeOfTopOffsetInPixels=(int)(MainGameWindow.displayheight*Brick.BRICKSTOPOFFSETINPERCENTAGE/100);
        double totalAmountOfPixelsX = MainGameWindow.displayWidth-Brick.sizeOfSideOffsetInPixels*2.0-((GridWidth-1)*MainGameWindow.displayWidth*Brick.SPACEBEETWENBRICKSINPERCENT/100);
        levelData = new Brick[LevelState.GridHeight][LevelState.GridWidth];

        float SpaceInWidth = (float)totalAmountOfPixelsX/(float)GridWidth;
        Brick.BrickWidth=(int)SpaceInWidth;
        Brick.BrickHeight=MainGameWindow.displayheight*Brick.BRICKHEIGHTPERCENTAGE/100;

        Random rand = new Random();
        int randomNum=0;

        for(int i=0;i<GridHeight;i++){
            for(int j=0;j<GridWidth;j++){

                    levelData[i][j] = new Brick();
                    levelData[i][j].x = SpaceInWidth * j + (float) Brick.sizeOfSideOffsetInPixels + (int) (MainGameWindow.displayWidth * Brick.SPACEBEETWENBRICKSINPERCENT / 100)*j;
                    levelData[i][j].y = i * (float) Brick.BrickHeight + (float) Brick.sizeOfTopOffsetInPixels + (int) (MainGameWindow.displayheight * Brick.SPACEBEETWENBRICKSINPERCENT / 100)*i;




                if(rand.nextInt()%PowerUpPerBricks==0){
                    if(rand.nextInt()%2==0){ // 50%
                        //Hearth
                        levelData[i][j].powerup = new PowerUp((int)levelData[i][j].x,
                                (int)levelData[i][j].y,
                                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.hearth),
                                PowerUp.PowerUpName.HEALTH
                                );

                    }else{
                        //power
                        levelData[i][j].powerup = new PowerUp((int)levelData[i][j].x,
                                (int)levelData[i][j].y,
                                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.strengh_power_up),
                                PowerUp.PowerUpName.BALLPOWER
                        );
                    }

                }
                if(rand.nextInt()%twoHitBricksPerBricks==0){
                    levelData[i][j].hitCount=2;

                }
                if(rand.nextInt()%threeHitBrickPerBricks==0){
                    levelData[i][j].hitCount=3;

                }


            }

        }


        //compute total brickhitpoints(if this value =0, level will be changed)

        for(int i=0;i<GridHeight;i++) {
            for (int j = 0; j < GridWidth; j++) {

                totalLevelBricksHitPoints+=levelData[i][j].hitCount;
            }
        }

        //init bitmaps for Bricks

        Brick.oneHitBitmap = Bitmap.createScaledBitmap(oneHitBitmap, (int)Brick.BrickWidth, (int)Brick.BrickHeight, false);
        Brick.twoHitBitmap = Bitmap.createScaledBitmap(twoHitBitmap, (int)Brick.BrickWidth, (int)Brick.BrickHeight, false);
        Brick.threeHitBitmap = Bitmap.createScaledBitmap(threeHitBitmap, (int)Brick.BrickWidth, (int)Brick.BrickHeight, false);




        //place for Sweep and Prune Grid to implemet

        //orc peon: we need more gold(time)!!!

        buttomsOfState.add(meniuButtom);
        buttomsOfState.add(soundButtom);

    }














    @Override
    public void init(){
       // gsm.useNotInitStateChanger=true;
        isGameOver=false;
        InGameMeniu.isGameOver=false;
        lifeBar.amountOfLifes=3;
        score=0;
        ball.isGluedToPaddle=true;
        totalLevelBricksHitPoints=0;
        Random rand = new Random();
        int randomNum=0;

        for(int i=0;i<GridHeight;i++){
            for(int j=0;j<GridWidth;j++){

                levelData[i][j].hitCount=1;

                if(rand.nextInt()%PowerUpPerBricks==0){
                    if(rand.nextInt()%2==0){ // 50%
                        //Hearth
                        levelData[i][j].powerup = new PowerUp((int)levelData[i][j].x,
                                (int)levelData[i][j].y,
                                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.hearth),
                                PowerUp.PowerUpName.HEALTH
                        );

                    }else{
                        //power
                        levelData[i][j].powerup = new PowerUp((int)levelData[i][j].x,
                                (int)levelData[i][j].y,
                                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.strengh_power_up),
                                PowerUp.PowerUpName.BALLPOWER
                        );
                    }

                }
                if(rand.nextInt()%twoHitBricksPerBricks==0){
                    levelData[i][j].hitCount=2;

                }
                if(rand.nextInt()%threeHitBrickPerBricks==0){
                    levelData[i][j].hitCount=3;

                }


            }

        }


        //compute total brickhitpoints(if this value =0, level will be changed)

        for(int i=0;i<GridHeight;i++) {
            for (int j = 0; j < GridWidth; j++) {

                totalLevelBricksHitPoints+=levelData[i][j].hitCount;
            }
        }




    }
    @Override
    public void update(){


        ball.update();
        paddle.update();




        for(int i=0;i<GridHeight;i++){
            for(int j=0;j<GridWidth;j++){
                levelData[i][j].managePhysics(ball);

            }

        }
        ball.madeHit=false;
        if(ball.logicallyInvincible && !ball.wasTouchedThisFrame){
            ball.logicallyInvincible=false;
        }
        ball.wasTouchedThisFrame=false;


        /*
        //manage power ups
        for(int i=0;i<GridHeight;i++){
            for(int j=0;j<GridWidth;j++){
                if(levelData[i][j].powerup!=null){
                    levelData[i][j].powerup.update();
                    if(paddle.manageCollisionWithPowerUp(levelData[i][j].powerup)){
                        if(levelData[i][j].powerup.typeOfPower == PowerUp.PowerUpName.HEALTH){
                            lifeBar.increaseHP();
                        }

                        if(levelData[i][j].powerup.typeOfPower == PowerUp.PowerUpName.BALLPOWER){
                            ball.IncreaseHitPower();
                        }

                    }

                }


            }

        }

        */
        //


        if(totalLevelBricksHitPoints<=0 && score!=0){
            isWin=true;
            totalLevelBricksHitPoints=5;
            Customtimer=0;
        }




        if(Life.amountOfLifes<=0){
            isGameOver=true;
            Life.amountOfLifes=3;

            initNewLevel();
            gsm.changeGameState(GameStateName.INGAMEMENIU,false);
            InGameMeniu.isGameOver=true;
            InGameMeniu.upperButtom.setButtomText("Replay");
        }else{

            isGameOver=false;
            InGameMeniu.isGameOver=false;
        }



    }
    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(Background,0,0,null);

        ball.draw(canvas);
        lifeBar.draw(canvas);
        invisibleScoreButtom.setButtomText(score+"");
        invisibleScoreButtom.draw(canvas);
        for(int i=0;i<buttomsOfState.size();i++){
            buttomsOfState.get(i).draw(canvas);

        }




        for(int i=0;i<GridHeight;i++){
            for(int j=0;j<GridWidth;j++){
                levelData[i][j].draw(canvas);
            }

        }


        paddle.draw(canvas);
        if(isWin){
            Customtimer++;

            canvas.drawBitmap(winBitmap,0,0,null);
            if(Customtimer==60){
                isWin=false;
                initNewLevel();
            }
        }

    }




    public void initNewLevel(){
        isGameOver=false;
        lifeBar.amountOfLifes=3;

        ball.isGluedToPaddle=true;
        totalLevelBricksHitPoints=0;
        Random rand = new Random();
        int randomNum=0;

        for(int i=0;i<GridHeight;i++){
            for(int j=0;j<GridWidth;j++){

                levelData[i][j].hitCount=1;

                if(rand.nextInt()%PowerUpPerBricks==0){
                    if(rand.nextInt()%2==0){ // 50%
                        //Hearth
                        levelData[i][j].powerup = new PowerUp((int)levelData[i][j].x,
                                (int)levelData[i][j].y,
                                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.hearth),
                                PowerUp.PowerUpName.HEALTH
                        );

                    }else{
                        //power
                        levelData[i][j].powerup = new PowerUp((int)levelData[i][j].x,
                                (int)levelData[i][j].y,
                                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.strengh_power_up),
                                PowerUp.PowerUpName.BALLPOWER
                        );
                    }

                }
                if(rand.nextInt()%twoHitBricksPerBricks==0){
                    levelData[i][j].hitCount=2;

                }
                if(rand.nextInt()%threeHitBrickPerBricks==0){
                    levelData[i][j].hitCount=3;

                }


            }

        }


        //compute total brickhitpoints(if this value =0, level will be changed)

        for(int i=0;i<GridHeight;i++) {
            for (int j = 0; j < GridWidth; j++) {

                totalLevelBricksHitPoints+=levelData[i][j].hitCount;
            }
        }


    }

    @Override
    public void touchingEvent(MotionEvent event){
        super.touchingEvent(event);
        paddle.OnTouchEvent(event);
    }


    @Override
    public void cleanUp(){}


}
