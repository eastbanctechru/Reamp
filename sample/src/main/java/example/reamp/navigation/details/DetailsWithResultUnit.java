package example.reamp.navigation.details;

import android.app.Activity;
import android.content.Intent;

import etr.android.reamp.navigation.Navigation;
import etr.android.reamp.navigation.NavigationUnit;

public class DetailsWithResultUnit extends NavigationUnit<String> {
    private static final String EXTRA_TEXT = "EXTRA_TEXT";
    private static final int REQUEST_CODE = 1;
    private String text;

    public DetailsWithResultUnit withTextResult(String text) {
        this.text = text;
        return this;
    }

    @Override
    protected void navigate(Navigation navigation) {
        Intent intent = new Intent(navigation.getActivity(), DetailsActivity.class);
        navigation.getActivity().startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void setNavigationResult(Navigation navigation) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TEXT, text);
        navigation.getActivity().setResult(Activity.RESULT_OK, intent);
        navigation.close();
    }

    @Override
    protected String getNavigationResult(Navigation navigation, int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            return data.getStringExtra(EXTRA_TEXT);
        }
        return null;
    }
}
