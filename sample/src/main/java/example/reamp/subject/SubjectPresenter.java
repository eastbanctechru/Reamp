package example.reamp.subject;

import etr.android.reamp.mvp.MvpPresenter;

public class SubjectPresenter extends MvpPresenter<SubjectStateModel> {

    public void onNameChanged(String text) {
        getStateModel().nameSubject.onNext(text);
    }

    public void onPassChanged(String text) {
        getStateModel().passSubject.onNext(text);
    }
}
