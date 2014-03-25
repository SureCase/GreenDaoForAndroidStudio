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


public class AddBoxActivity extends Activity {

    private Button btnAdd;
    private Button btnCancel;
    private EditText etBoxName;
    private EditText etBoxSize;
    private EditText etBoxDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_box);

        btnAdd = (Button) this.findViewById(R.id.btnAdd);
        btnCancel = (Button) this.findViewById(R.id.btnCancel);
        etBoxName = (EditText) this.findViewById(R.id.etBoxName);
        etBoxSize = (EditText) this.findViewById(R.id.etBoxSize);
        etBoxDescription = (EditText) this.findViewById(R.id.etBoxDescription);

        setupButtons();
    }

    private void setupButtons() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etBoxSize.getText() != null && !etBoxSize.getText().toString().equals("")) {
                    BoxRepository.insertOrUpdate(AddBoxActivity.this, createBox(etBoxName.getText().toString(),
                            Integer.parseInt(etBoxSize.getText().toString()), etBoxDescription.getText().toString()));
                    finish();
                } else {
                    Toast.makeText(AddBoxActivity.this, "Please enter valid box size!", Toast.LENGTH_SHORT).show();
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

    private Box createBox(String name, int slots, String description) {
        Box tempBox = new Box();
        tempBox.setName(name);
        tempBox.setSlots(slots);
        tempBox.setDescription(description);
        return tempBox;
    }

}
