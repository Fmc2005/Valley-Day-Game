package de.tum.cit.aet.valleyday.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.aet.valleyday.ValleyDayGame;
import de.tum.cit.aet.valleyday.audio.MusicTrack;
/**
 * Class of the screen in which you can change the settings.
 * includes settings for sounds & difficulty
 */
public class SettingsScreen implements Screen {
    private final Stage stage;
    /**
     * constructor for settings screen
     * @param game game that can show that screen
     */
    public SettingsScreen(ValleyDayGame game){
        var camera = new OrthographicCamera();
        

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        Label title = new Label("Settings", game.getSkin(), "title");
        table.add(title).colspan(3).expandX().padBottom(50).row();

      
        
    
        TextButton muteButton = new TextButton("Mute/Unmute Game Sound", game.getSkin());
        if(game.isMuted()){muteButton.setColor(Color.GREEN);}
       
        table.add(muteButton).colspan(3).expandX().width(500).padBottom(30).row();


        muteButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!game.isMuted()){
                    muteButton.setColor(Color.RED);
                    for (MusicTrack track : MusicTrack.values()){
                        track.mute();
                    }
                    game.setMuted(true);
                    return;
                }
                    muteButton.setColor(Color.GREEN);
                    game.setMuted(false);
                    MusicTrack.BACKGROUND.play();
                    MusicTrack.GAMETRACK.mute();           
                }
        });

        


        // set games difficulty
        table.row();
        table.add().colspan(3).expandX().padBottom(30).row();
        TextButton setEasy = new TextButton("easy", game.getSkin());
       if(game.difficulty == 0){setEasy.setColor(Color.GREEN);}
        TextButton setMedium = new TextButton("medium", game.getSkin());
        if(game.difficulty == 1){setMedium.setColor(Color.ORANGE);}
        TextButton setHard = new TextButton("hard", game.getSkin());
        if(game.difficulty == 2){setHard.setColor(Color.RED);}
        table.add(setEasy).width(200).pad(10).padBottom(50);
        table.add(setMedium).width(200).pad(10).padBottom(50);
        table.add(setHard).width(200).pad(10).padBottom(50);
        table.row();


        setEasy.addListener(new ChangeListener() {
            /**
             * implements change when set difficulty easy button clicked
             * @param event button pressed event
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.difficulty = 0;
         
                setEasy.setColor(Color.GREEN);
                setMedium.setColor(Color.LIGHT_GRAY);
                setHard.setColor(Color.LIGHT_GRAY);
            }
        });
        setMedium.addListener(new ChangeListener() {
            /**
             * implements change when set difficulty medium button clicked
             * @param event button pressed event
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.difficulty = 1;
     
                setEasy.setColor(Color.LIGHT_GRAY);
                setMedium.setColor(Color.ORANGE);
                setHard.setColor(Color.LIGHT_GRAY);
            }
        });
        setHard.addListener(new ChangeListener() {
            /**
             * implements change when set difficulty hard button clicked
             * @param event button pressed event
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.difficulty = 2;
  
                setEasy.setColor(Color.LIGHT_GRAY);
                setMedium.setColor(Color.LIGHT_GRAY);
                setHard.setColor(Color.RED);
            }
        });


        // Go to Menu Button
        TextButton goToMenuButton = new TextButton("Back to menu", game.getSkin());
        table.add(goToMenuButton).colspan(3).expandX().width(500).padBottom(30).row();
        goToMenuButton.addListener(new ChangeListener() {
            /**
             * implements scene change when goToMenu button clicked
             * @param event button pressed event
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });

       
    
    }


    /**
     * The render method is called every frame to render the SettingsScreen.
     * It clears the screen and draws the stage.
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.250f); // Cap frame time to 250ms to prevent spiral of death        ScreenUtils.clear(Color.BLACK);
        ScreenUtils.clear(Color.BLACK);
        stage.act(frameTime); // Update the stage
        stage.draw(); // Draw the stage
    
    }

    /**
     * Resize the stage when the screen is resized.
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
