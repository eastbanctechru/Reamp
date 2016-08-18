package example.reamp.navigation;

import android.app.Activity;
import android.content.Intent;

import etr.android.reamp.navigation.Navigation;
import etr.android.reamp.navigation.NavigationUnit;
import example.reamp.number.NumberActivity;

public class NumberNavigationUnit extends NavigationUnit<String, Integer> {
    private static final String EXTRA_TEXT = "EXTRA_TEXT";
    private static final String EXTRA_NUMBER = "EXTRA_NUMBER";
    private static final int REQUEST_CODE = 2;
    private String text;
    private Integer number;

    public void setText(String text) {
        this.text = text;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    protected void navigate(Navigation navigation) {
        Intent intent = new Intent(navigation.getActivity(), NumberActivity.class);
        intent.putExtra(EXTRA_TEXT, text);
        navigation.getActivity().startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected String getNavigationData(Navigation navigation) {
        return navigation.getActivity().getIntent().getStringExtra(EXTRA_TEXT);
    }

    @Override
    protected void setNavigationResult(Navigation navigation) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NUMBER, number);
        navigation.getActivity().setResult(Activity.RESULT_OK, intent);
        navigation.close();
    }

    @Override
    protected Integer getNavigationResult(Navigation navigation, int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            return data.getIntExtra(EXTRA_NUMBER, 0);
        }
        return null;
    }
}
