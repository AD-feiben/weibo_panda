package com.panda.pweibo.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.panda.pweibo.R;
import com.panda.pweibo.models.UserItem;
import com.panda.pweibo.utils.ToastUtils;

import java.util.List;

public class UserItemAdapter extends BaseAdapter {

    private Context         mContext;
    private List<UserItem>  mlistUserItem;

    public UserItemAdapter(Context context, List<UserItem> datas) {
        mContext = context;
        mlistUserItem = datas;
    }

    @Override
    public int getCount() {
		return mlistUserItem.size();
	}

    @Override
    public UserItem getItem(int position) {
		return mlistUserItem.get(position);
	}

    @Override
    public long getItemId(int position) {
		return position;
	}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_user, null);
            holder.v_divider = convertView.findViewById(R.id.v_divider);
            holder.ll_content = convertView.findViewById(R.id.ll_content);
            holder.iv_left = (ImageView) convertView.findViewById(R.id.iv_left);
            holder.tv_subhead = (TextView) convertView.findViewById(R.id.tv_subhead);
            holder.tv_caption = (TextView) convertView.findViewById(R.id.tv_caption);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set data
        UserItem item = getItem(position);
        holder.v_divider.setVisibility(item.isShowTopDivider() ? View.VISIBLE : View.GONE);
        holder.iv_left.setImageResource(item.getLeftImg());
        holder.tv_subhead.setText(item.getSubhead());
        holder.tv_caption.setText(item.getCaption());

        holder.ll_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(mContext, "点击了Item" + position, Toast.LENGTH_SHORT);
            }
        });

        return convertView;
    }

    public static class ViewHolder{
        public View v_divider;
        public View ll_content;
        public ImageView iv_left;
        public TextView tv_subhead;
        public TextView tv_caption;
    }

}
