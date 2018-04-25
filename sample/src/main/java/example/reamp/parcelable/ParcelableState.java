package example.reamp.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import etr.android.reamp.mvp.ReampStateModel;

public class ParcelableState extends ReampStateModel implements Parcelable {

    public int counter;

    public ParcelableState() {

    }

    protected ParcelableState(Parcel in) {
        counter = in.readInt();
    }

    public static final Creator<ParcelableState> CREATOR = new Creator<ParcelableState>() {
        @Override
        public ParcelableState createFromParcel(Parcel in) {
            return new ParcelableState(in);
        }

        @Override
        public ParcelableState[] newArray(int size) {
            return new ParcelableState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(counter);
    }
}
