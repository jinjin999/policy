package com.aqua.anroid.policynoticeapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.CustomViewHolder> {

    private ArrayList<FavoriteData> mList = null;
    private Activity context = null;


    public FavoriteAdapter(Activity context, ArrayList<FavoriteData> list) {
        this.context = context;
        this.mList = list;
    }

    interface OnItemClickListener {
        void onItemClick(View v, int position);
        void onDeleteClick(View v, int positon);//삭제
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView name;
        protected TextView content;
        protected Button deletebutton;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textView_list_id);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.content = (TextView) view.findViewById(R.id.textView_list_content);
            this.deletebutton = (Button) view.findViewById(R.id.deletebutton);

            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, ""+deletebutton+"", Toast.LENGTH_SHORT).show();
                }
            });

            deletebutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View view){
                    Toast.makeText(view.getContext() , "click" , Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition ();
                    if (position!=RecyclerView.NO_POSITION){
                        if (mListener!=null){
                            mListener.onDeleteClick(view,position);
                        }
                    }
                }
            });
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setText(mList.get(position).getMember_id());
        viewholder.name.setText(mList.get(position).getMember_name());
        viewholder.content.setText(mList.get(position).getMember_content());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}

/*public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // adapter에 들어갈 list 입니다.
    private ArrayList<FavoriteData> listData = new ArrayList<FavoriteData>();
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder holder;
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_header, parent, false);
            holder = new HeaderViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_footer, parent, false);
            holder = new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
            holder = new ItemViewHolder(view);
        }
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        } else {
            // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.onBind(listData.get(position - 1), position);
        }
    }
    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size() + 2;
    }
    void addItem(FavoriteData favoriteData) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(favoriteData);
    }
    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;
        //private Button button;
        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.favorite_list_1);
            textView2 = itemView.findViewById(R.id.favorite_list_text);
        }
        void onBind(FavoriteData detailData, int position) {
            textView1.setText(detailData.getTitle());
            textView2.setText(detailData.getContent());
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else if (position == listData.size() + 1)
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }
}*/