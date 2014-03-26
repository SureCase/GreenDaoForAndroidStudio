package pl.surecase.eu.greendaoexample.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import greendao.Box;
import pl.surecase.co.greendaoexample.daoexample.R;
import pl.surecase.eu.greendaoexample.backend.repositories.BoxRepository;
import pl.surecase.eu.greendaoexample.ui.adapter.DbItemsAdapter;

public class BoxListActivity extends Activity {

    private ListView lvItemList;
    private DbItemsAdapter boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_list);

        lvItemList = (ListView) this.findViewById(R.id.lvItemList);
        boxAdapter = new DbItemsAdapter(BoxListActivity.this);
        lvItemList.setAdapter(boxAdapter);

        setupButtons();
    }

    private void setupButtons() {
        lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editItemIntent = new Intent(BoxListActivity.this, EditBoxActivity.class);

                Box clickedBox = boxAdapter.getItem(position);
                editItemIntent.putExtra("boxId", clickedBox.getId());

                startActivity(editItemIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        boxAdapter.updateData(BoxRepository.getAllBoxes(BoxListActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.box_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                createItem();
                return true;

            case R.id.delete_items:
                clearAllItems();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createItem() {
        Intent addBoxActivityIntent = new Intent(BoxListActivity.this, EditBoxActivity.class);
        startActivity(addBoxActivityIntent);
    }

    private void clearAllItems() {
        if (boxAdapter.getCount() == 0) {
            Toast.makeText(BoxListActivity.this, getString(R.string.toast_no_items_to_delete), Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(BoxListActivity.this)
                    .setTitle(getString(R.string.dialog_delete_items_title))
                    .setMessage(R.string.dialog_delete_items_content)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_delete_items_confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            BoxRepository.clearBoxes(BoxListActivity.this);
                            boxAdapter.updateData(BoxRepository.getAllBoxes(BoxListActivity.this));
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton(R.string.dialog_delete_items_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).create().show();
        }
    }
}
