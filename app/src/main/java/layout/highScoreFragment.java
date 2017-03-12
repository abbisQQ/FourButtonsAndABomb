package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abbisqq.FourButtons.MainActivity;
import com.abbisqq.FourButtons.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link highScoreFragment.OnHighscoreFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link highScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class highScoreFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView nameText1,nameText2,nameText3, scoreView1,scoreView2,scoreView3,newScoreView;
    private EditText editText;
    private int score1,score2,score3,newScore;
    private Button save;
    private String name1,name2,name3,newName;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private OnHighscoreFragmentInteractionListener mListener;

    public highScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment highScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static highScoreFragment newInstance(String param1, String param2) {
        highScoreFragment fragment = new highScoreFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_high_score, container, false);

        MainActivity mainActivity = (MainActivity)getActivity();
        save = (Button)view.findViewById(R.id.saveButton);
        save.setOnClickListener(this);
        save.setEnabled(false);
        newScoreView = (TextView) view.findViewById(R.id.textView6);
        nameText1 = (TextView)view.findViewById(R.id.nameText1);
        nameText2 = (TextView)view.findViewById(R.id.nameText2);
        nameText3 = (TextView)view.findViewById(R.id.nameText3);
        editText = (EditText)view.findViewById(R.id.editText);
        scoreView1 = (TextView)view.findViewById(R.id.scoreView);
        scoreView2 = (TextView)view.findViewById(R.id.scoreView2);
        scoreView3 = (TextView)view.findViewById(R.id.scoreView3);
        editText.setEnabled(false);
        sharedPreferences = getActivity().getPreferences(0);
        editor= sharedPreferences.edit();
        newScore=sharedPreferences.getInt("score",0);
        name1 = sharedPreferences.getString("name1", "user1");
        name2 = sharedPreferences.getString("name2", "user2");
        name3 = sharedPreferences.getString("name3", "user3");
        score1 = sharedPreferences.getInt("score1", 200);
        score2 = sharedPreferences.getInt("score2", 150);
        score3 = sharedPreferences.getInt("score3", 100);
        nameText1.setText(name1);
        nameText2.setText(name2);
        nameText3.setText(name3);
        scoreView1.setText(String.valueOf(score1));
        scoreView2.setText(String.valueOf(score2));
        scoreView3.setText(String.valueOf(score3));
        newScoreView.setText(String.valueOf(newScore));
        if(mainActivity.gamePlayed) {
            if (newScore > score3) {
                mainActivity.FinalSound(true,50);
                editText.setEnabled(true);
                save.setEnabled(true);
                editText.setHint("Enter your name");
            } else {
                mainActivity.FinalSound(false,50);
                editText.setHint("Game Over!");

            }
            mainActivity.gamePlayed=false;
        }
        return view;
    }



    @Override
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.startButton();
        newName = editText.getText().toString();



        if(newScore>score1){

            nameText1.setText(editText.getText());
            scoreView1.setText(String.valueOf(newScore));
            nameText2.setText(name1);
            scoreView2.setText(String.valueOf(score1));
            nameText3.setText(name2);
            scoreView3.setText(String.valueOf(score2));
            editor.putString("name3",name2);
            editor.putInt("score3",score2);
            editor.putString("name2",name1);
            editor.putInt("score2",score1);
            editor.putString("name1",newName);
            editor.putInt("score1",newScore);

        }else if(newScore>score2){

            nameText2.setText(editText.getText());
            scoreView2.setText(String.valueOf(newScore));
            nameText3.setText(name2);
            scoreView3.setText(String.valueOf(score2));
            editor.putString("name3",name2);
            editor.putInt("score3",score2);
            editor.putInt("score2",newScore);
            editor.putString("name2",newName);



        }else if(newScore>score3){

            nameText3.setText(newName);
            scoreView3.setText(String.valueOf(newScore));
            editor.putInt("score3",newScore);
            editor.putString("name3",newName);


        }
        save.setEnabled(false);
        editText.setEnabled(false);
        editor.putInt("score",0);
        editor.apply();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnHighscoreFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHighscoreFragmentInteractionListener) {
            mListener = (OnHighscoreFragmentInteractionListener) context;
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
    public interface OnHighscoreFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnHighscoreFragmentInteraction(Uri uri);
    }


}
