package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abbisqq.FourButtons.MainActivity;
import com.abbisqq.FourButtons.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link gameFragment.OnGameFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link gameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    gameFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ImageButton aButton,bButton,cButton,dButton, middleButton;

    private TextView timeText,scoreText,helpText1,helpText2;

    private int score,random_value,timesCorrect=0,secPass=60,sec=800;

    private RelativeLayout mainLayout;

    private char current_button,button_pressed,previous_button;
    private int volume=75;
    protected Random random;
    MainActivity mainActivity;
    Timer timer;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnGameFragmentInteractionListener mListener;

    public gameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gameFragment newInstance(String param1, String param2) {
        gameFragment fragment = new gameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        aButton = (ImageButton)view.findViewById(R.id.aButton);
        bButton = (ImageButton)view.findViewById(R.id.bButton);
        cButton = (ImageButton)view.findViewById(R.id.cButton);
        dButton = (ImageButton)view.findViewById(R.id.dButton);
        middleButton = (ImageButton)view.findViewById(R.id.middleButton);

        mainActivity = (MainActivity)getActivity();

        random = new Random();

        helpText1 = (TextView)view.findViewById(R.id.helpText1);
        helpText2 = (TextView)view.findViewById(R.id.helpText2);
        scoreText = (TextView)view.findViewById(R.id.scoreText);
        timeText = (TextView)view.findViewById(R.id.timeText);

        mainLayout = (RelativeLayout)view.findViewById(R.id.mainLayout);

        aButton.setOnClickListener(this);
        bButton.setOnClickListener(this);
        cButton.setOnClickListener(this);
        dButton.setOnClickListener(this);
        middleButton.setOnClickListener(this);


        // Inflate the layout for this fragment

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      gameCore();
                    }
                });

            }
        }, 0, sec);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }
    private void gameCore(){
        if(secPass>0) {
            aButton.setImageResource(R.drawable.anotpressed);
            bButton.setImageResource(R.drawable.bnotpressed);
            cButton.setImageResource(R.drawable.cnotpressed);
            dButton.setImageResource(R.drawable.dnotpressed);
            mainLayout.setBackgroundResource(R.drawable.defaultgamebackground);
            if(previous_button=='@'){
                score=score-20;
                timesCorrect=0;
                mainActivity.explode(100);
            }
            if(previous_button!='?'&&previous_button!='@') {
                randomButton(5);
            }
            else {
                randomButton(4);
            }
            currentAndPreviousButtons(current_button, previous_button);
            previous_button = current_button;

            //calculates and prints the seconds in the timeText(TextView) and the score in scoreText
            secPass--;
            if(timesCorrect>=10){
                secPass=secPass+5;
                timesCorrect=0;
            }
            timeText.setText("time:"+String.valueOf(secPass) );
            scoreText.setText( "points:"+String.valueOf(score));

        }else {
            mainActivity.stopMainsong();
            saveInfo(score);
            timer.cancel();
            mainActivity.gamePlayed = true;
            mainActivity.loadHighScoreScreen();


        }
    }


    public void saveInfo(int score){
        // 0  means private mode;
        SharedPreferences sharedPreferences = getActivity().getPreferences(0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score",score);
        editor.apply();

    }






    @Override
    public void onClick(View view) {
        if (view == aButton) {
            //change the button images and background when d is pressed
            aButton.setImageResource(R.drawable.apressed);
            mainLayout.setBackgroundResource(R.drawable.a_pressed);

            button_pressed = 'a';
            if (current_button == button_pressed) {
                mainActivity.correctButton(volume);
                score = score + 10;
                timesCorrect++;
            } else {
                mainActivity.wrongButton(volume);
                score -= 20;
                timesCorrect=0;
            }

        } else if (view == bButton) {
            //change the button images and background when d is pressed
            bButton.setImageResource(R.drawable.bpressed);
            mainLayout.setBackgroundResource(R.drawable.b_pressed);

            button_pressed = 'b';


            if (current_button == button_pressed) {
                mainActivity.correctButton(volume);
                score = score + 10;
                timesCorrect++;
            } else {
                mainActivity.wrongButton(volume);
                score -= 20;
                timesCorrect=0;
            }

        } else if (view == cButton) {
            //change the button images and background when c is pressed
            cButton.setImageResource(R.drawable.cpressed);
            mainLayout.setBackgroundResource(R.drawable.c_pressed);

            button_pressed = 'c';

            if (current_button == button_pressed) {
                mainActivity.correctButton(volume);
                score = score + 10;
                timesCorrect++;
            } else {
                mainActivity.wrongButton(volume);
                score -= 20;
                timesCorrect=0;
            }

        } else if (view == dButton) {
            //change the button images and background when d is pressed
            dButton.setImageResource(R.drawable.dpressed);
            mainLayout.setBackgroundResource(R.drawable.d_pressed);

            button_pressed = 'd';


            if (current_button == button_pressed) {
                mainActivity.correctButton(volume);
                score = score + 10;
                timesCorrect++;
            } else {
                mainActivity.wrongButton(volume);
                score -= 20;
                timesCorrect=0;
            }

        }else if(view==middleButton) {
            button_pressed = '@';
            if (current_button == button_pressed) {
                mainActivity.correctButton(volume);
                score = score + 10;
                timesCorrect++;
                previous_button = '?';
            }
        }
    }


        //a method that returns a random number
    private void randomButton(int ran){
        random_value=random.nextInt(ran);
        middleButton.setEnabled(false);

        switch(random_value){
            case 0:
                middleButton.setImageResource(R.drawable.dnotpressed);
                current_button='d';
                break;
            case 1:
                current_button='a';
                middleButton.setImageResource(R.drawable.anotpressed);
                break;
            case 2:
                middleButton.setImageResource(R.drawable.bnotpressed);
                current_button='b';
                break;
            case 3:
                middleButton.setImageResource(R.drawable.cnotpressed);
                current_button='c';
                break;
            case 4:
                middleButton.setImageResource(R.drawable.bomb);
                current_button='@';
                mainActivity.bomb(volume);
                middleButton.setEnabled(true);
                break;
        }


    }
    private  void currentAndPreviousButtons(char current_button , char previous_button){
        //help text in case a letter comes 2 times or more
        if(current_button==previous_button||current_button=='@'&&previous_button=='?'){
            helpText1.setTextColor(getResources().getColor(R.color.help_text_yellow));
            helpText1.setTextColor(getResources().getColor(R.color.help_text_yellow));

            helpText1.setText("Tap");
            helpText2.setText("again!");
        }else{
            helpText1.setText("");
            helpText2.setText("");
        }
    }








    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnGameFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameFragmentInteractionListener) {
            mListener = (OnGameFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGameFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnGameFragmentInteraction(Uri uri);
    }
}
