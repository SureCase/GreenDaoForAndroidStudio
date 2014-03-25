package pl.surecase.eu.greendaoexample.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import greendao.Box;
import pl.surecase.co.greendaoexample.daoexample.R;
import pl.surecase.eu.greendaoexample.backend.repositories.BoxRepository;


public class EditBoxActivity extends Activity {

    private Button btnEdit;
    private Button btnCancel;
    private EditText etBoxName;
    private EditText etBoxSize;
    private EditText etBoxDescription;

    private long itemId;
    private String boxName;
    private int boxSize;
    private String boxDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreData(savedInstanceState);
        setContentView(R.layout.activity_edit_box);

        btnEdit = (Button) this.findViewById(R.id.btnEdit);
        btnCancel = (Button) this.findViewById(R.id.btnCancel);
        etBoxName = (EditText) this.findViewById(R.id.etBoxName);
        etBoxSize = (EditText) this.findViewById(R.id.etBoxSize);
        etBoxDescription = (EditText) this.findViewById(R.id.etBoxDescription);

        setupButtons();
        fillViewWithData();
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            itemId = getIntent().getExtras().getLong("itemId");
            boxName = getIntent().getExtras().getString("boxName");
            boxSize = getIntent().getExtras().getInt("boxSize", 0);
            boxDescription = getIntent().getExtras().getString("boxDescription");
        }
    }

    private void setupButtons() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etBoxSize.getText() != null && !etBoxSize.getText().toString().equals("")) {
                    Box editedBox = BoxRepository.getBoxForId(EditBoxActivity.this, itemId);
                    editedBox.setName(etBoxName.getText().toString());
                    editedBox.setSlots(Integer.parseInt(etBoxSize.getText().toString()));
                    editedBox.setDescription(etBoxDescription.getText().toString());
                    BoxRepository.insertOrUpdate(EditBoxActivity.this, editedBox);
                    finish();
                } else {
                    Toast.makeText(EditBoxActivity.this, "Please enter valid box size!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillViewWithData() {
        etBoxName.setText(boxName);
        etBoxSize.setText(Long.toString(boxSize));
        etBoxDescription.setText(boxDescription);
    }

    private Box createBox(String name, int slots, String description) {
        Box tempBox = new Box();
        tempBox.setName(name);
        tempBox.setSlots(slots);
        tempBox.setDescription(description);
        return tempBox;
    }

}
