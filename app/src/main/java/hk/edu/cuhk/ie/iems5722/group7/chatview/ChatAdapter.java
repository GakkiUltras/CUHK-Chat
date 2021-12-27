package hk.edu.cuhk.ie.iems5722.group7.chatview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import hk.edu.cuhk.ie.iems5722.group7.R;
import hk.edu.cuhk.ie.iems5722.group7.chatview.model.ChatMessage;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private LayoutInflater mInflater;
    private int localID;

    // View lookup cache
    private static class SentMsgViewHolder {
        public TextView content;
        public TextView date;
        public TextView name;

        public SentMsgViewHolder(View view) {
            this.content = view.findViewById(R.id.right_content);
            this.date = view.findViewById(R.id.right_date);
            this.name=view.findViewById(R.id.right_name);
        }
    }

    private static class RecvMsgViewHolder {
        public TextView content;
        public TextView date;
        public TextView name;

        public RecvMsgViewHolder(View view) {
            this.content = view.findViewById(R.id.left_content);
            this.date = view.findViewById(R.id.left_date);
            this.name=view.findViewById(R.id.left_name);
        }
    }

    public ChatAdapter(Context context, ArrayList<ChatMessage> messages) {

        super(context, 0, messages);
        mInflater = LayoutInflater.from(context);
        this.localID = 115516;


    }

    public void setLocalID(int localID){
        this.localID = localID;
    }
    public int getlocalID(){
        return localID;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType(this.localID);
    }

    @Override
    public int getViewTypeCount() {
        return ChatMessage.TYPE_COUNT;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ChatMessage chatMessage = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        RecvMsgViewHolder recvMsgViewHolder;
        SentMsgViewHolder sentMsgViewHolder; // view lookup cache stored in tag
//        if (convertView==null){
//            viewHolder=new ViewHolder();
//            LayoutInflater inflater=LayoutInflater.from(getContext());
//            convertView=inflater.inflate(R.layout.item_right_message,parent,false);
//            viewHolder.content=(TextView) convertView.findViewById(R.id.right_content);
//            viewHolder.date=(TextView) convertView.findViewById(R.id.right_date);
//            convertView.setTag(viewHolder);
//        }
//        else {
//            // View is being recycled, retrieve the viewHolder object from tag
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.content.setText(chatMessage.content);
//        SimpleDateFormat ft = new SimpleDateFormat("HH:MM");
//        viewHolder.date.setText(ft.format(chatMessage.date));
//        return convertView;

        if (getItemViewType(position) == ChatMessage.SEND) {
            return handleGetSentMsgView(position, convertView, parent);
        } else if (getItemViewType(position) == ChatMessage.RECEIVE) {
            return handleGetRecvMsgView(position, convertView, parent);
        } else {
            return null;
        }
//        return handleGetRecvMsgView(position,convertView,parent);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @NonNull
    private View handleGetSentMsgView(int position, View convertView, ViewGroup parent) {
        ChatMessage item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        // view lookup cache stored in tag
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_right_message, parent, false);
            convertView.setTag(new SentMsgViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof SentMsgViewHolder) {
            SentMsgViewHolder sentMsgViewHolder = (SentMsgViewHolder) convertView.getTag();
            sentMsgViewHolder.content.setText(item.getContent());
            sentMsgViewHolder.date.setText(item.parseTimeToString("HH:mm"));
            sentMsgViewHolder.name.setText(item.getName());
        }
        return convertView;
    }

    @NonNull
    private View handleGetRecvMsgView(int position, View convertView, ViewGroup parent) {
        ChatMessage item = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_left_message, parent, false);
            convertView.setTag(new RecvMsgViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof RecvMsgViewHolder) {
            RecvMsgViewHolder recvMsgViewHolder = (RecvMsgViewHolder) convertView.getTag();
            recvMsgViewHolder.content.setText(item.getContent());
            recvMsgViewHolder.date.setText(item.parseTimeToString("HH:mm"));
            recvMsgViewHolder.name.setText(item.getName());
        }
        return convertView;
    }

//    private String transDate(Date date) {
//        SimpleDateFormat ft = new SimpleDateFormat("HH:mm");
//        return ft.format(date);
//    }
}
