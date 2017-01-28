package us.ait.rozmanm.shoppingcarthw;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.ait.rozmanm.shoppingcarthw.adapter.ProductRecyclerAdapter;
import us.ait.rozmanm.shoppingcarthw.adapter.ProductTouchHelperCallback;
import us.ait.rozmanm.shoppingcarthw.data.Product;

public class MainActivity extends AppCompatActivity implements EditInterface {

    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;

    public static final int REQUEST_CODE_ADD = 100;
    public static final String KEY_PRODUCT_TO_EDIT = "KEY_PRODUCT_TO_EDIT";
    public static final int REQUEST_CODE_EDIT = 101;

    private ProductRecyclerAdapter productRecyclerAdapter;
    private RecyclerView recyclerShoppingCart;

    private int positionToEdit = -1;
    private Long idToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        ButterKnife.bind(this);
        displaySplash();
        setupRecyclerView();
        setupFAB();
        setupToolbar();

        updateTotalPrice();
    }

    public void updateTotalPrice() {
        int tP = productRecyclerAdapter.getTotalPrice();
        tvTotalPrice.setText("$" + tP);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddProduct = new Intent();
                intentAddProduct.setClass(MainActivity.this, AddProductActivity.class);

                startActivityForResult(intentAddProduct, REQUEST_CODE_ADD);
            }
        });
    }

    private void setupRecyclerView() {
        // set up Recycler View:
        recyclerShoppingCart = (RecyclerView) findViewById(R.id.recyclerStopwatch);
        recyclerShoppingCart.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerShoppingCart.setLayoutManager(mLayoutManager);
        productRecyclerAdapter = new ProductRecyclerAdapter(this, this);

        ItemTouchHelper.Callback callback = new ProductTouchHelperCallback(
                productRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerShoppingCart);

        recyclerShoppingCart.setAdapter(productRecyclerAdapter);
    }

    private void displaySplash() {
        // Display splash Activity
        Intent intentSplash = new Intent();
        intentSplash.setClass(MainActivity.this, SplashActivity.class);
        startActivity(intentSplash);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                productRecyclerAdapter.clearList();
                break;
            default:
                break;
        }
//        updateTotalPrice();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD) {
                Product newProduct = (Product) data.getSerializableExtra(AddProductActivity.KEY_PRODUCT);
                productRecyclerAdapter.addProduct(newProduct);
                recyclerShoppingCart.scrollToPosition(0);
            }
            else if( requestCode == REQUEST_CODE_EDIT) {
                Product changedProduct = (Product) data.getSerializableExtra(
                        AddProductActivity.KEY_PRODUCT);
                changedProduct.setId(idToEdit);

                productRecyclerAdapter.edit(changedProduct, positionToEdit);
            }
        }
//        updateTotalPrice();
    }

    @Override
    public void showEditDialog(Product productToEdit, int position) {
        // start activity, sending product to edit
        positionToEdit = position;
        idToEdit = productToEdit.getId();
        Intent intentShowEdit = new Intent();
        intentShowEdit.setClass(MainActivity.this, AddProductActivity.class);
        intentShowEdit.putExtra(KEY_PRODUCT_TO_EDIT, productToEdit);
        startActivityForResult(intentShowEdit, REQUEST_CODE_EDIT);
//        updateTotalPrice();
    }

}
