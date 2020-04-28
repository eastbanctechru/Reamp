package example.reamp.navigation.details;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import etr.android.reamp.functional.ConsumerNonNull;
import etr.android.reamp.mvp.ReampFragment;
import example.reamp.R;


public class DetailsFragment extends ReampFragment<DetailsFragmentPresenter, DetailsFragmentStateModel> {

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = (EditText) view.findViewById(R.id.details_edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getPresenter().onTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        view.findViewById(R.id.close_details_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().sendResultText();
            }
        });
    }

    @Override
    public DetailsFragmentStateModel onCreateStateModel() {
        return new DetailsFragmentStateModel();
    }

    @Override
    public DetailsFragmentPresenter onCreatePresenter() {
        return new DetailsFragmentPresenter();
    }

    @Override
    public void onStateChanged(DetailsFragmentStateModel stateModel) {
        editText.setText(stateModel.text);
        stateModel.showDataAction.consume(new ConsumerNonNull<String>() {
            @Override
            public void consume(@NonNull String s) {
                showAlert(s);
            }
        });
    }

    private void showAlert(String msg) {
        new AlertDialog.Builder(getContext())
                .setMessage(msg)
                .show();
    }
}
