package katsapov.heroes.presentaition.mvp;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.mvp.HeroContract.HeroView;
import katsapov.heroes.presentaition.ui.MainActivity;

public class Presenter implements HeroContract.Presenter {

    HeroContract.Presenter mPresenter;
    HeroContract.HeroView mHeroView;
    private boolean isHasInternetConnection = false;


    @Override
    public void attachView(HeroView view) {
    }

    @Override
    public void detachView() {
    }

    @Override
    public void setList(List<Hero> list) {
        List<Hero> heroList = list;
    }

    @Override
    public boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void getDataOnAdapter(Activity activity, MainActivity activityMain) {
        mPresenter = new Presenter();
        isHasInternetConnection = mPresenter.isOnline(activity);
        if (isHasInternetConnection) {
            mPresenter = new Presenter();
            new NetworkManager.getDataStringFromApi(activityMain).execute();
        } else {
            mHeroView = new katsapov.heroes.presentaition.mvp.HeroView();
            mHeroView.showError(activity, R.string.error_connection);
        }
    }
}
