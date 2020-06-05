package com.paxx.app.ui.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.paxx.app.R;
import com.paxx.app.ui.activity.CoachmarkActivity;
import com.paxx.app.ui.activity.GetStartedActivity;
import com.paxx.app.ui.util.EvangalistItem;
import com.paxx.app.ui.util.PrefUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by hong on 2017/6/20.
 */

public class CoachmarkItemFragment extends BaseFragment {

    @Bind(R.id.coachmark_image)
    ImageView coachmarkImage;
    @Bind(R.id.but_get_started)
    Button butGetStarted;
    @Bind(R.id.coachmark_headerText)
    TextView coachmarkHeaderText;
    @Bind(R.id.coachmark_descriptionText)
    TextView coachmarkDescriptionText;
    @Bind(R.id.container)
    CoordinatorLayout container;



    public static CoachmarkItemFragment newInstance(Bundle paramBundle) {
        CoachmarkItemFragment localCoachmarkItemFragment = new CoachmarkItemFragment();
        localCoachmarkItemFragment.setArguments(paramBundle);
        return localCoachmarkItemFragment;
    }

    public CoachmarkItemFragment() {

    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected View initLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coachmark_item, container, false);
    }

    @Override
    protected void iniComp(Bundle savedInstanceState) {
        EvangalistItem localEvangalistItem = EvangalistItem.fromBundle(getArguments());
        coachmarkImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), localEvangalistItem.getImage()));
        coachmarkHeaderText.setText(localEvangalistItem.getHeader());
        coachmarkDescriptionText.setText(localEvangalistItem.getDescription());
        coachmarkImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @OnClick({R.id.but_get_started})
    public void onGetStartedClick(View paramView){
        Intent intent = new Intent(mContext, GetStartedActivity.class);
        startActivity(intent);
        getActivity().finish();
        PrefUtils.setBoolean(getActivity(),
                "is_user_guide_showed", true);
    }



    @Override
    protected void refreshView(Bundle savedInstanceState) {

    }

    @Override
    protected void restoreData(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
