package de.tum.cit.aet.valleyday.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.tum.cit.aet.valleyday.ValleyDayGame;
import de.tum.cit.aet.valleyday.audio.MusicTrack;
import de.tum.cit.aet.valleyday.texture.Textures;

public class MenuScreen implements Screen {

   
private Texture currentBg;



    private final Stage stage;
    private final ValleyDayGame game;

    public MenuScreen(ValleyDayGame game) {
        this.game = game;

       
var camera = new OrthographicCamera();
        
        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        buildMenu(table);
        

    }

    private void buildMenu(Table table) {
        boolean lost = game.getMap().lost;
        boolean won = game.getMap().won;

        String titleText;
        Runnable startAction;

        if (!lost && !won) {
            if (!game.isMuted()){
            MusicTrack.BACKGROUND.play();}
            else MusicTrack.BACKGROUND.mute();
            MusicTrack.FIXYOU.mute();
            MusicTrack.GAMETRACK.mute();
            MusicTrack.HEARTOFCOURAGE.mute();
            titleText = "Menu";
            startAction = game::goToGame;
            currentBg = Textures.RUNNINGSCREEN;
        } else if (lost) {
            titleText = "You have Lost (NOOB)";
            startAction = game::goToNewGame;
            if (!game.isMuted()){
            MusicTrack.FIXYOU.play();}
            else MusicTrack.FIXYOU.mute();
            MusicTrack.HEARTOFCOURAGE.mute();
            MusicTrack.GAMETRACK.mute();
            MusicTrack.BACKGROUND.mute();
            currentBg = Textures.LOSINGSCREEN;
        } else {
            titleText = "You have Won (LIKE A BOSS)";
            startAction = game::goToNewGame;
            if (!game.isMuted()){
            MusicTrack.HEARTOFCOURAGE.play();}
            else MusicTrack.HEARTOFCOURAGE.mute();
            MusicTrack.FIXYOU.mute();
            MusicTrack.GAMETRACK.mute();
            MusicTrack.BACKGROUND.mute();
            currentBg = Textures.WINNINGSCREEN;
        }

        Label title = new Label(titleText, game.getSkin(), "title");
        if (lost) title.setColor(Color.RED);
        if (won) title.setColor(Color.GREEN);

        table.add(title).padBottom(80).row();

        TextButton goButton = new TextButton(
                (!lost && !won) ? "Go To Game" :
                lost ? "Try Again" : "Continue Winning Streak",
                game.getSkin());

        TextButton goToSettingsButton = new TextButton("Settings", game.getSkin());
       


        TextButton selectMapButton = new TextButton("Select Map File", game.getSkin());

        TextButton quitButton = new TextButton("Quit Game", game.getSkin());

        table.add(goButton).width(500).row();
        table.add(selectMapButton).width(500).row();
        table.add(goToSettingsButton).width(500).row();
        
        table.add(quitButton).width(500).row();

        goButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startAction.run();
            }
        });

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        

        selectMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openMapchooser();
            }
        });


        goToSettingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               game.goToSettings(); 
            }
        });
    }

    @Override
public void render(float deltaTime) {
    float frameTime = Math.min(deltaTime, 0.250f);

    ScreenUtils.clear(Color.BLACK);

    SpriteBatch batch = game.getSpriteBatch();
    batch.setProjectionMatrix(
        new com.badlogic.gdx.math.Matrix4().setToOrtho2D(
                0, 0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        )
);

    batch.begin();
    batch.draw(
            currentBg,
            0,
            0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
    );
    batch.end();

    stage.act(frameTime);
    stage.draw();
}




    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
public void dispose() {
    stage.dispose();
    
}


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
