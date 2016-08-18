package example.reamp.number;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import etr.android.reamp.mvp.MvpFragment;
import example.reamp.R;


public class NumberFragment extends MvpFragment<NumberFragmentPresenter, NumberFragmentStateModel> {

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_number, container, false);
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
                getPresenter().onSendBackClicked();
            }
        });
    }

    @Override
    public NumberFragmentStateModel onCreateStateModel() {
        return new NumberFragmentStateModel();
    }

    @Override
    public NumberFragmentPresenter onCreatePresenter() {
        return new NumberFragmentPresenter();
    }

    @Override
    public void onStateChanged(NumberFragmentStateModel stateModel) {
        editText.setText(stateModel.numberText);

        String toastError = stateModel.showErrorToast.get();
        if (!TextUtils.isEmpty(toastError)) {
            Toast.makeText(getContext(), toastError, Toast.LENGTH_SHORT).show();
        }
    }
}
