package us.ait.rozmanm.shoppingcarthw;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.ait.rozmanm.shoppingcarthw.data.Product;

public class AddProductActivity extends AppCompatActivity {

    public static final String KEY_PRODUCT = "KEY_PRODUCT";

    private Product productToEdit = null;

    @BindView(R.id.btnAddItem)
    Button btnAddItem;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etPrice)
    EditText etPrice;

    @BindView(R.id.etDescription)
    EditText etDescription;

    @BindView(R.id.cbSetPurchased)
    CheckBox cbSetPurchased;

    @BindView(R.id.spinCategory)
    Spinner spinCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setupUI();
    }

    private void setupUI() {
        ButterKnife.bind(this);
        setupSpinner();
        // check if you are editing
        if (getIntent() != null
                && getIntent().hasExtra(MainActivity.KEY_PRODUCT_TO_EDIT)) {
            setupToEdit();
        }
    }

    private void setupToEdit() {
        setTitle("Edit Product");
        btnAddItem.setText("Save Changes");
        productToEdit = (Product) getIntent().getSerializableExtra(
                MainActivity.KEY_PRODUCT_TO_EDIT);

        etName.setText(productToEdit.getName());

        // I GET AN ERROR THAT IT CAN'T FIND THE etPrice RESOURCE??

        etPrice.setText("" + productToEdit.getPrice());
        etDescription.setText(productToEdit.getDescription());
        cbSetPurchased.setChecked(productToEdit.isPurchased());
        String[] catArray = getResources().getStringArray(R.array.category_array);
        int cat = Arrays.asList(catArray).indexOf(productToEdit.getCategory());
        spinCategory.setSelection(cat);
    }

    private void setupSpinner() {
        // make the spinner work
        //Spinner categorySpinner = (Spinner) findViewById(R.id.spinCategory);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(categoryAdapter);
    }

    @OnClick(R.id.btnAddItem)
    public void AddItemOnClickListener(Button btnAddItem) {
        Intent result = new Intent();

        boolean purchased = cbSetPurchased.isChecked();
        Editable name = etName.getText();
        Editable description = etDescription.getText();
        Editable price = etPrice.getText();
        String category = spinCategory.getItemAtPosition(
                spinCategory.getSelectedItemPosition()).toString();

        if(TextUtils.isEmpty(name)) {
            etName.setError("Enter a name.");
        }
        else if (TextUtils.isEmpty(description)) {
            etDescription.setError("Enter a description");
        }
        else if (TextUtils.isEmpty(price)) {
            etPrice.setError("Enter a price.");
        } else {setProductUp(result, name, description, price, category, purchased);}


    }

    private void setProductUp(Intent result, Editable name, Editable description,
                              Editable price, String category, boolean purchased) {
        Product newProduct = productToEdit;
        if (newProduct == null) {
            newProduct = new Product(name.toString(), Integer.parseInt(price.toString()),
                    purchased, category, description.toString());
        } else {
            newProduct.setName(name.toString());
            newProduct.setPrice(Integer.parseInt(price.toString()));
            newProduct.setPurchased(cbSetPurchased.isChecked());
            newProduct.setDescription(description.toString());
            newProduct.setCategory(category);
        }

        result.putExtra(KEY_PRODUCT, newProduct);

        setResult(RESULT_OK, result);
        finish();
    }
}
