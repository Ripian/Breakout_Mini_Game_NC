package com.example.stefan.breakout_mini_game_nc;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private MainGameWindow mainGameWindow;
    public static Canvas canvas;

    public static int FPS=60;
    private long targetTime = 1000/FPS;

    public GameThread(SurfaceHolder surfaceHolder, MainGameWindow mainGameWindow){
        super();
        this.surfaceHolder = surfaceHolder;
        this.mainGameWindow = mainGameWindow;




    }

    @Override
    public void run(){

        long start=0;
        long elapsed=0;
        long wait=0;

        while(isRunning){

            start = System.nanoTime();

            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder){
                    this.mainGameWindow.update();
                    this.mainGameWindow.draw(canvas);

                }
            }catch(Exception e){

            }finally {
                try {
                    if (canvas != null) {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }catch (Exception e){

                }
            }


            elapsed = System.nanoTime() -start;

            wait =targetTime -elapsed/1000000;

            try{
                Thread.sleep(wait);
            }catch (Exception e){

                e.printStackTrace();
            }

        }

    }


    //getters setters

    public void setRunning(boolean newIsRunningState){
        this.isRunning = newIsRunningState;

    }

}
