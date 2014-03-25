package pl.surecase.eu.greendaoexample.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import pl.surecase.co.greendaoexample.daoexample.R;
import pl.surecase.eu.greendaoexample.backend.repositories.BoxRepository;
import pl.surecase.eu.greendaoexample.ui.adapter.DbItemsAdapter;

public class BoxListActivity extends Activity {

    private ListView lvItemList;
    private DbItemsAdapter boxAdapter;
    private Button btnAddItem;
    private Button btnClearItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_list);

        lvItemList = (ListView) this.findViewById(R.id.lvItemList);
        btnAddItem = (Button) this.findViewById(R.id.btnAddItem);
        btnClearItems = (Button) this.findViewById(R.id.btnClearItems);

        setupListView();
        setupButtons();
    }

    private void setupListView() {
        boxAdapter = new DbItemsAdapter(BoxListActivity.this);
        lvItemList.setAdapter(boxAdapter);
    }

    private void setupButtons() {
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBoxActivityIntent = new Intent(BoxListActivity.this, AddBoxActivity.class);
                startActivity(addBoxActivityIntent);
            }
        });

        btnClearItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoxRepository.clearBoxes(BoxListActivity.this);
                boxAdapter.addDataAndRefresh(BoxRepository.getAllBoxes(BoxListActivity.this));
            }
        });

        boxAdapter.setOnDeleteItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag(R.id.tag_item_id) != null) {
                    long itemId = (Long) v.getTag(R.id.tag_item_id);
                    BoxRepository.deleteBoxWithId(BoxListActivity.this, itemId);
                    boxAdapter.addDataAndRefresh(BoxRepository.getAllBoxes(BoxListActivity.this));
                }
            }
        });

        boxAdapter.setOnEditItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editItemIntent = new Intent(BoxListActivity.this, EditBoxActivity.class);

                if (v.getTag(R.id.tag_item_id) != null) {
                    long itemId = (Long) v.getTag(R.id.tag_item_id);
                    editItemIntent.putExtra("itemId", itemId);
                }
                if (v.getTag(R.id.tag_box_name) != null) {
                    String boxName = (String) v.getTag(R.id.tag_box_name);
                    editItemIntent.putExtra("boxName", boxName);
                }
                if (v.getTag(R.id.tag_box_size) != null) {
                    int boxSize = (Integer) v.getTag(R.id.tag_box_size);
                    editItemIntent.putExtra("boxSize", boxSize);
                }
                if (v.getTag(R.id.tag_box_description) != null) {
                    String boxDescription = (String) v.getTag(R.id.tag_box_description);
                    editItemIntent.putExtra("boxDescription", boxDescription);
                }

                startActivity(editItemIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        boxAdapter.addDataAndRefresh(BoxRepository.getAllBoxes(BoxListActivity.this));
    }

}
