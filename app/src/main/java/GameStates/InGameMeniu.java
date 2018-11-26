package GameStates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.stefan.breakout_mini_game_nc.MainGameWindow;
import com.example.stefan.breakout_mini_game_nc.R;

import CustomButtons.CircleButton;
import CustomButtons.RectangleButtom;

public class InGameMeniu extends GameState {

    public static boolean isGameOver=false;

    public static RectangleButtom upperButtom;
    private RectangleButtom lowerButtom;


    private final static int UPPERXPERCENT =30;
    private final static int UPPERYPERCENT =30;
    private final static int UPPERWIDTHPERCENT =40;
    private final static int UPPERHEIGHTPERCENT =10;

    private final static int LOWERXPERCENT =35;
    private final static int LOWERYPERCENT =60;
    private final static int LOWERWIDTHPERCENT =30;
    private final static int LOWERHEIGHTPERCENT =10;



    private final static int MAINPANEXPER = 25;
    private final static int MAINPANEYPER = 15;
    private final static int MAINPANEWIDTHPER = 50;
    private final static int MAINPANEHEIGHTPER = 60;
    private  static int tableXInPixels;
    private  static int tableYInPixels;



    private Paint defaultPaintForINGameMeniu;
    private LevelState level;
    private Paint textPaint;


    public InGameMeniu(GameStateManager gsm, GameState.GameStateName gameStateName){
        super(gsm, gameStateName);
        textPaint = new Paint();
        textPaint.setColor(0xFF965200);


        tableXInPixels = (int)(MainGameWindow.displayWidth*MAINPANEXPER/100);
        tableYInPixels = (int)(MainGameWindow.displayheight*MAINPANEYPER/100);

        //init bacground bitmap


        Bitmap rawBackgroundBitmap = BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.in_game_meniu_table);
        this.Background = Bitmap.createScaledBitmap(rawBackgroundBitmap,
                (int)MainGameWindow.displayWidth*MAINPANEWIDTHPER/100,
                (int)(MainGameWindow.displayheight*MAINPANEHEIGHTPER/100),
                false);

        //init buttoms
        //init back buttom


        upperButtom= new RectangleButtom(gsm,
                GameStateName.PLAYABLELEVEL,
                this,
                UPPERXPERCENT,
                UPPERYPERCENT,
                UPPERWIDTHPERCENT,
                UPPERHEIGHTPERCENT,
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.empty_box_borderless_off),
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.empty_box_borderless_pressed),
                "CONTINUE",
                textPaint
        );
        //init level choice buttoms



        lowerButtom= new RectangleButtom(gsm,
                GameStateName.MENIU,
                this,
                LOWERXPERCENT,
                LOWERYPERCENT,
                LOWERWIDTHPERCENT,
                LOWERHEIGHTPERCENT,
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.empty_box_borderless_off),
                BitmapFactory.decodeResource(MainGameWindow.context.getResources(), R.drawable.empty_box_borderless_pressed),
                "EXIT",
                textPaint
        );




        //1 fix here
        upperButtom.textPaint.setTextSize(60);

        buttomsOfState.add(upperButtom);
        buttomsOfState.add(lowerButtom);

    }

    @Override
    public void init(){
        isGameOver=LevelState.isGameOver;




        if(isGameOver){
            isGameOver=false;
            upperButtom.setButtomText(" REPLAY");
        }else{
            upperButtom.setButtomText("CONTINUE");
        }
        for(int i=0;i<gsm.gameStates.size();i++){
            if(gsm.gameStates.get(i).getStateName().nameOfState==GameStateName.PLAYABLELEVEL.nameOfState){
                level=(LevelState)(gsm.gameStates.get(i));
            }
        }
    }
    @Override
    public void update(){}
    @Override
    public void draw(Canvas canvas){
        level.draw(canvas);//second background
        canvas.drawBitmap(Background,tableXInPixels,tableYInPixels,null);






        for(int i=0;i<buttomsOfState.size();i++){
            buttomsOfState.get(i).draw(canvas);

        }

    }
    @Override
    public void cleanUp(){}




}
