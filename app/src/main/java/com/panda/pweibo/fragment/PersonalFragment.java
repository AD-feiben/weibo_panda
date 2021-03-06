package com.panda.pweibo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.panda.pweibo.MyFragment;
import com.panda.pweibo.R;
import com.panda.pweibo.activity.MainActivity;
import com.panda.pweibo.activity.UserInfoActivity;
import com.panda.pweibo.adapter.UserAdapter;
import com.panda.pweibo.adapter.UserItemAdapter;
import com.panda.pweibo.constants.AccessTokenKeeper;
import com.panda.pweibo.constants.Uri;
import com.panda.pweibo.models.User;
import com.panda.pweibo.models.UserItem;
import com.panda.pweibo.utils.TitlebarUtils;
import com.panda.pweibo.utils.ToastUtils;
import com.panda.pweibo.widget.WrapHeightListView;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class PersonalFragment extends MyFragment implements OnClickListener {

    private     MainActivity            mActivity;
    private     View                    mView;
    private     UserItemAdapter         mAdapter;
    private     Oauth2AccessToken       mAccessToken;
    private     User                    mUser;

    private     ImageView               pwb_imageview_item_status_avatar;
    private     TextView                pwb_textview_sender;
    private     TextView                pwb_textview_item_status_from_and_when;

    private     LinearLayout            include_personal_info;
    private     LinearLayout            pwb_ll_status_count;
    private     TextView                pwb_status_count;
    private     LinearLayout            pwb_ll_follows_count;
    private     TextView                pwb_follows_count;
    private     LinearLayout            pwb_ll_followed_count;
    private     TextView                pwb_followed_count;

    private     WrapHeightListView      pwb_lv_user;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        mActivity = (MainActivity) getActivity();

        mAccessToken = AccessTokenKeeper.readAccessToken(mActivity);

        List<UserItem> mUserItemList = personalItem();

        mAdapter = new UserItemAdapter(mActivity, mUserItemList);
    }

    public PersonalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView();
        loadData();

        return mView;
    }

    /** 初始化view */
    public void initView() {
        mView = View.inflate(mActivity, R.layout.fragment_personal, null);
        pwb_lv_user = (WrapHeightListView) mView.findViewById(R.id.pwb_lv_user);

        pwb_imageview_item_status_avatar = (ImageView) mView.findViewById(
                R.id.pwb_imageview_item_status_avatar);
        pwb_textview_sender = (TextView) mView.findViewById(
                R.id.pwb_textview_sender);
        pwb_textview_item_status_from_and_when = (TextView) mView.findViewById(
                R.id.pwb_textview_item_status_from_and_when);

        include_personal_info = (LinearLayout) mView.findViewById(R.id.include_personal_info);
        pwb_ll_status_count = (LinearLayout) mView.findViewById(R.id.pwb_ll_status_count);
        pwb_status_count = (TextView) include_personal_info.findViewById(
                R.id.pwb_status_count);
        pwb_ll_status_count.setOnClickListener(this);
        pwb_ll_follows_count = (LinearLayout) mView.findViewById(R.id.pwb_ll_follows_count);
        pwb_follows_count = (TextView) include_personal_info.findViewById(
                R.id.pwb_follows_count);
        pwb_ll_follows_count.setOnClickListener(this);
        pwb_ll_followed_count = (LinearLayout) mView.findViewById(R.id.pwb_ll_followed_count);
        pwb_followed_count = (TextView) include_personal_info.findViewById(
                R.id.pwb_followed_count);
        pwb_ll_followed_count.setOnClickListener(this);

        new TitlebarUtils(mView)
                .setTitleContent("我")
                .setTitlebarTvRight("设置")
                .setRightOnClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(mActivity, "设置", Toast.LENGTH_SHORT);
                    }
                });

        pwb_lv_user.setAdapter(mAdapter);
    }

    private void loadData() {
        String uri = Uri.USER_SHOW;
        uri += "?access_token=" + mAccessToken.getToken();
        uri += "&uid=" + mAccessToken.getUid();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("error")) {
                                int errorCode = response.getInt("error_code");
                                String error = response.getString("error");
                                ToastUtils.showToast(mActivity,
                                        "错误码：" + errorCode + "-" + error,
                                        Toast.LENGTH_SHORT);
                            } else {
                                mUser = new User().parseJson(response);
                                setData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToast(mActivity, "网络发生异常", Toast.LENGTH_SHORT);
            }
        });

        mActivity.mRequestQueue.add(jsonObjectRequest);
    }

    private void setData() {
        ImageListener listener = ImageLoader.getImageListener(pwb_imageview_item_status_avatar,
                R.drawable.ic_com_sina_weibo_sdk_logo,
                R.drawable.ic_com_sina_weibo_sdk_logo);
        mActivity.mImageLoader.get(mUser.getProfile_image_url(), listener);

        pwb_textview_sender.setText(mUser.getName());
        pwb_textview_item_status_from_and_when.setText("简介:" + mUser.getDescription());
        pwb_status_count.setText(mUser.getStatuses_count()+"");
        pwb_follows_count.setText(mUser.getFriends_count()+"");
        pwb_followed_count.setText(mUser.getFollowers_count() + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pwb_ll_status_count:
                if (mUser != null) {
                    Intent intent = new Intent(mActivity, UserInfoActivity.class);
                    intent.putExtra("userName", mUser.getName());
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(mActivity, "网络连接错误,请稍后重试", Toast.LENGTH_SHORT);
                }
                break;

            case R.id.pwb_ll_follows_count:
                ToastUtils.showToast(mActivity, "关注:" + ((mUser != null) ? mUser.getFriends_count() : 0) + "", Toast.LENGTH_SHORT);
                break;

            case R.id.pwb_ll_followed_count:
                ToastUtils.showToast(mActivity, "粉丝:" + ((mUser != null) ? mUser.getFollowers_count() : 0) + "", Toast.LENGTH_SHORT);
                break;

            default:
                break;
        }
    }

    protected List<UserItem> personalItem (){
        List<UserItem> data = new ArrayList<>();

        data.add(new UserItem(true, R.drawable.pwb_push_icon_app_small_1, "新的朋友", ""));
        data.add(new UserItem(true, R.drawable.pwb_push_icon_app_small_2, "微博等级", "Lv13"));
        data.add(new UserItem(false, R.drawable.pwb_push_icon_app_small_3, "我的相册", "(17)"));
        data.add(new UserItem(true, R.drawable.pwb_push_icon_app_small_4, "微博支付", ""));
        data.add(new UserItem(true, R.drawable.pwb_push_icon_app_small_5, "更多", "收藏、名片"));

        return data;
    }

}
