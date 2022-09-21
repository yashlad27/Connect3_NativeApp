package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.acl.Group;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 0; // 0 for X and 1 for O and 2 is empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    }; // by using tags assigned to image view in XML
    boolean gameActive = true;

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        Log.i("tags", counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive){
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);
            if(activePlayer==0){
                counter.setImageResource(R.drawable.x);

                activePlayer = 1;
            }else {
                counter.setImageResource(R.drawable.o);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(1000);

            for(int[] winningPos : winningPositions){
                if(gameState[winningPos[0]] == gameState[winningPos[1]] && gameState[winningPos[1]] == gameState[winningPos[2]] && gameState[winningPos[0]] != 2){
                    gameActive = false;
                    String winner = "";

                    // here taking opposite of activePlayer
                    if(activePlayer == 1){
                        winner = "X";
                    }else{
                        winner = "O";
                    }

                    Toast.makeText(this,  winner+" Has WON!", Toast.LENGTH_LONG).show();

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView WinnerTextView = (TextView) findViewById(R.id.WinnertextView);

                    WinnerTextView.setText(winner+" Has WON!");

                    playAgainButton.setVisibility(View.VISIBLE);
                    WinnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view){
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView WinnerTextView = (TextView) findViewById(R.id.WinnertextView);

        playAgainButton.setVisibility(View.INVISIBLE);
        WinnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        // Updating values of our game:
        for(int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        activePlayer = 0;
        // WRONG => gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

        gameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}