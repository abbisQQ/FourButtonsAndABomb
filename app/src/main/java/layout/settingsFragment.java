package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.abbisqq.FourButtons.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link settingsFragment.OnSettingsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link settingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settingsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RadioButton button1,button2,button3,button4;
    CheckBox checkBox1,checkBox2;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String muteB,muteM;
    SharedPreferences pref;
    SharedPreferences.Editor edi;
    private OnSettingsFragmentInteractionListener mListener;

    public settingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static settingsFragment newInstance(String param1, String param2) {
        settingsFragment fragment = new settingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        button1 = (RadioButton) view.findViewById(R.id.radioButton1);
        button2 = (RadioButton) view.findViewById(R.id.radioButton2);
        button3 = (RadioButton) view.findViewById(R.id.radioButton3);
        button4 = (RadioButton) view.findViewById(R.id.radioButton4);
        checkBox1 = (CheckBox)view.findViewById(R.id.checkBox);
        checkBox2 = (CheckBox)view.findViewById(R.id.checkBox2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        pref = getActivity().getPreferences(0);

        int music = pref.getInt("music_num",1);
        muteB = pref.getString("muteButtons","dontMute");
        muteM = pref.getString("muteMusic","dontMute");
        if (music==2){
            button2.setChecked(true);
            button1.setChecked(false);
            button3.setChecked(false);
            button4.setChecked(false);

        }else if (music==3){
            button3.setChecked(true);
            button1.setChecked(false);
            button2.setChecked(false);
            button4.setChecked(false);
        }else if (music==4) {
            button4.setChecked(true);
            button1.setChecked(false);
            button3.setChecked(false);
            button2.setChecked(false);
        }else if (music==1){
            button1.setChecked(true);
            button2.setChecked(false);
            button3.setChecked(false);
            button4.setChecked(false);


        }

        if(muteB=="dontMute"){
            checkBox1.setChecked(false);
        }else if(muteB=="mute"){
            checkBox1.setChecked(true);

        }

        if(muteM=="dontMute"){
            checkBox2.setChecked(false);

        }else if(muteM=="mute"){
            checkBox2.setChecked(true);

        }



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnSettingsFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingsFragmentInteractionListener) {
            mListener = (OnSettingsFragmentInteractionListener) context;
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

    @Override
    public void onClick(View view) {

        prefEditor= pref.edit();
        if(checkBox1.isChecked()){
            prefEditor.putString("muteButtons","mute");

        }else if(!checkBox1.isChecked()){
            prefEditor.putString("muteButtons","dontMute");
        }
        if(checkBox2.isChecked()){
            prefEditor.putString("muteMusic","mute");

        }else if(!checkBox2.isChecked()){
        prefEditor.putString("muteMusic","dontMute");
    }
        if (view==button2){
            prefEditor.putInt("music_num",2);
            button1.setChecked(false);
            button3.setChecked(false);
            button4.setChecked(false);

        }else if (view==button3){
            prefEditor.putInt("music_num",3);
            button1.setChecked(false);
            button2.setChecked(false);
            button4.setChecked(false);
        }else if (view==button4) {
            prefEditor.putInt("music_num",4);
            button1.setChecked(false);
            button3.setChecked(false);
            button2.setChecked(false);
        }else if (view==button1){
            prefEditor.putInt("music_num",1);
            button2.setChecked(false);
            button3.setChecked(false);
            button4.setChecked(false);


        }
            prefEditor.apply();
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
    public interface OnSettingsFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnSettingsFragmentInteraction(Uri uri);
    }
}
