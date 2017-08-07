package example.reamp.navigation;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import etr.android.reamp.mvp.ReampFragment;
import example.reamp.R;

public class NavigationFragment extends ReampFragment<NavigationFragmentPresenter, NavigationFragmentState> {

    private EditText mainEditText;
    private TextView textFromDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainEditText = (EditText) view.findViewById(R.id.main_edit_text);
        mainEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getPresenter().onMainTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        textFromDetails = (TextView) view.findViewById(R.id.text_from_details);
        view.findViewById(R.id.open_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().openBrowser();
            }
        });
        view.findViewById(R.id.open_for_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().openForResult();
            }
        });
        view.findViewById(R.id.open_with_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().openWithData();
            }
        });
    }

    @Override
    public NavigationFragmentState onCreateStateModel() {
        NavigationFragmentState mainFragmentStateModel = new NavigationFragmentState();
        mainFragmentStateModel.resultText = "No result yet";
        mainFragmentStateModel.mainText = "Send me to another screen";
        return mainFragmentStateModel;
    }

    @Override
    public NavigationFragmentPresenter onCreatePresenter() {
        return new NavigationFragmentPresenter();
    }

    @Override
    public void onStateChanged(NavigationFragmentState stateModel) {
        mainEditText.setText(stateModel.mainText);
        textFromDetails.setText(stateModel.resultText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boolean finishing = getActivity().isFinishing();
        Log.d("^^^^", "onDestroy: " + finishing);
    }
}
