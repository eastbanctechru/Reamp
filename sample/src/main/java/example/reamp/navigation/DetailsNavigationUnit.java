package example.reamp.navigation;

import android.app.Activity;
import android.content.Intent;

import etr.android.reamp.navigation.Navigation;
import etr.android.reamp.navigation.NavigationUnit;
import example.reamp.details.DetailsActivity;
import example.reamp.tablet.TabletActivity;

public class DetailsNavigationUnit extends NavigationUnit<String, String> {
    private static final String EXTRA_TEXT = "EXTRA_TEXT";
    private static final int REQUEST_CODE = 1;
    private final String text;
    private static String sharedText;

    public DetailsNavigationUnit(String text) {
        this.text = text;
    }

    public DetailsNavigationUnit() {
        text = null;
    }

    @Override
    protected void navigate(Navigation navigation) {
        if (navigation.getActivity() instanceof TabletActivity) {
            TabletActivity activity = (TabletActivity) navigation.getActivity();
            activity.getPresenter().openDetails();
            sharedText = text;
        } else {
            Intent intent = new Intent(navigation.getActivity(), DetailsActivity.class);
            intent.putExtra(EXTRA_TEXT, text);
            navigation.getActivity().startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected String getNavigationData(Navigation navigation) {
        if (navigation.getActivity() instanceof TabletActivity) {
            return sharedText;
        }
        return navigation.getActivity().getIntent().getStringExtra(EXTRA_TEXT);
    }

    @Override
    protected void setNavigationResult(Navigation navigation) {
        if (navigation.getActivity() instanceof TabletActivity) {
            sharedText = text;
            TabletActivity activity = (TabletActivity) navigation.getActivity();
            activity.getPresenter().onSendDetailsBack();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TEXT, text);
        navigation.getActivity().setResult(Activity.RESULT_OK, intent);
        navigation.close();
    }

    @Override
    protected String getNavigationResult(Navigation navigation, int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (navigation.getActivity() instanceof TabletActivity) {
                return sharedText;
            }
            return data.getStringExtra(EXTRA_TEXT);
        }
        return null;
    }
}
