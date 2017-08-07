package example.reamp.subject;

import etr.android.reamp.mvp.ReampPresenter;

public class SubjectPresenter extends ReampPresenter<SubjectStateModel> {

    public void onNameChanged(String text) {
        getStateModel().nameSubject.onNext(text);
    }

    public void onPassChanged(String text) {
        getStateModel().passSubject.onNext(text);
    }
}
