package example.reamp.main;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import etr.android.reamp.mvp.MvpFragment;
import example.reamp.R;

public class MainFragment extends MvpFragment<MainFragmentPresenter, MainFragmentStateModel> {

    private EditText mainEditText;
    private TextView textFromDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
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
        view.findViewById(R.id.open_details_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onOpenDetailsClicked();
            }
        });
    }

    @Override
    public MainFragmentStateModel onCreateStateModel() {
        MainFragmentStateModel mainFragmentStateModel = new MainFragmentStateModel();
        mainFragmentStateModel.textFromDetails = "No text yet";
        mainFragmentStateModel.mainText = "Send me to another screen";
        return mainFragmentStateModel;
    }

    @Override
    public MainFragmentPresenter onCreatePresenter() {
        return new MainFragmentPresenter();
    }

    @Override
    public void onStateChanged(MainFragmentStateModel stateModel) {
        mainEditText.setText(stateModel.mainText);
        textFromDetails.setText(stateModel.textFromDetails);
    }
}
