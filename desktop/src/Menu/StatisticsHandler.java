package Menu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Gameplay.ICar;

import java.util.ArrayList;
import java.util.Timer;

public class StatisticsHandler implements ApplicationListener {

    /**
     * This class handles the logging of when cars pass the finish line.
     * Everytime a car passes the finish line a log is created and stored in an arraylist of strings
     * that can be requested by a menu to display the car run times through the track.
     */
    private ArrayList<ICar> cars;
    private float raceTimer;

    public ArrayList<String> getFinishLogList() {
        return finishLogList;
    }

    private ArrayList<String> finishLogList;

    /**
     * The constructor for the StatisticsHandler.
     * This function takes as parameter an array list of cars, starts a timer and
     * an arraylist of strings to log whenever a car passes the finish line
     * @param cars
     */
    public StatisticsHandler(ArrayList<ICar> cars) {
        this.cars = cars;
        finishLogList = new ArrayList<String>();
        raceTimer = 0;
    }


    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        finishLineCheck();
        raceTimer += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void finishLineCheck() {
        for (ICar car : cars
                ) {
            if (car.getIsOnFinishLine()) {
                logFinishCarStatistics((Car) car);

            }

        }
    }

    private void logFinishCarStatistics(Car car){

        StringBuilder strbuild = new StringBuilder();
        String finishLog;

        finishLog = strbuild.append(car.getName() + " Finished at " + Math.round((double)raceTimer) + " seconds.").toString();
        finishLogList.add(finishLog);
    }
}
