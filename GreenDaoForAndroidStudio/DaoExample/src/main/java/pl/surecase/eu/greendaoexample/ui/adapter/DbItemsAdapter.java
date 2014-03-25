package pl.surecase.eu.greendaoexample.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import greendao.Box;
import pl.surecase.co.greendaoexample.daoexample.R;

/**
 * Created by surecase on 19/03/14.
 */
public class DbItemsAdapter extends ArrayAdapter<Box> {

    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onDeleteItemButtonClick;
    private View.OnClickListener onEditItemButtonClick;

    public DbItemsAdapter(Context context) {
        super(context, 0);
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addDataAndRefresh(List<Box> boxList) {
        this.clear();
        for (int i=0; i<boxList.size(); i++) {
            add(boxList.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_box,null);
            viewHolder = new ViewHolder();
            viewHolder.root = (RelativeLayout) convertView.findViewById(R.id.boxItem);
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.tvItemId);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvSize = (TextView) convertView.findViewById(R.id.tvSize);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            viewHolder.ibDeleteItem = (ImageButton) convertView.findViewById(R.id.ibDeleteItem);
            viewHolder.ibEdit = (ImageButton) convertView.findViewById(R.id.ibEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        editBackground(position, viewHolder);
        fillViewWithData(position, viewHolder);
        setupButtons(position, viewHolder);

        return convertView;
    }

    private void editBackground(int position, ViewHolder viewHolder) {
        if (position % 2 == 0) {
            viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.lightGray));
        }
    }

    private void fillViewWithData(int position, ViewHolder viewHolder) {
        viewHolder.tvId.setText(Long.toString(getItem(position).getId()));
        viewHolder.tvName.setText(getItem(position).getName());
        viewHolder.tvSize.setText(Integer.toString(getItem(position).getSlots()));
        viewHolder.tvDescription.setText(getItem(position).getDescription());
    }

    private void setupButtons(int position, ViewHolder viewHolder) {
        viewHolder.ibDeleteItem.setOnClickListener(onDeleteItemButtonClick);
        viewHolder.ibEdit.setOnClickListener(onEditItemButtonClick);

        viewHolder.ibDeleteItem.setTag(R.id.tag_item_id, getItem(position).getId());
        viewHolder.ibEdit.setTag(R.id.tag_item_id, getItem(position).getId());
        viewHolder.ibEdit.setTag(R.id.tag_box_name, getItem(position).getName());
        viewHolder.ibEdit.setTag(R.id.tag_box_size, getItem(position).getSlots());
        viewHolder.ibEdit.setTag(R.id.tag_box_description, getItem(position).getDescription());
    }

    static class ViewHolder {
        RelativeLayout root;
        TextView tvId;
        TextView tvName;
        TextView tvSize;
        TextView tvDescription;
        ImageButton ibDeleteItem;
        ImageButton ibEdit;
    }

    public void setOnDeleteItemClickListener(View.OnClickListener listener) {
        this.onDeleteItemButtonClick = listener;
    }

    public void setOnEditItemClickListener(View.OnClickListener listener) {
        this.onEditItemButtonClick = listener;
    }
}
