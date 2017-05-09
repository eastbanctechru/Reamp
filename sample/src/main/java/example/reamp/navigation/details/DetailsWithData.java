package example.reamp.navigation.details;

import android.app.Activity;
import android.content.Intent;

import etr.android.reamp.navigation.Navigation;
import etr.android.reamp.navigation.NavigationUnit;

public class DetailsWithData extends NavigationUnit<String> {

    private static final String EXTRA_DATA = "EXTRA_DATA";
    public String data;

    public DetailsWithData withData(String data) {
        this.data = data;
        return this;
    }

    @Override
    protected void navigate(Navigation navigation) {
        Activity activity = navigation.getActivity();
        activity.startActivity(makeIntent(activity));
    }

    @Override
    protected String getNavigationData(Navigation navigation) {
        return navigation.getActivity().getIntent().getStringExtra(EXTRA_DATA);
    }

    private Intent makeIntent(Activity activity) {
        return new Intent(activity, DetailsActivity.class)
                .putExtra(EXTRA_DATA, data);
    }
}
