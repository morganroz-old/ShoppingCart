package us.ait.rozmanm.shoppingcarthw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.ait.rozmanm.shoppingcarthw.adapter.ProductRecyclerAdapter;
import us.ait.rozmanm.shoppingcarthw.data.Product;

public class DescriptionActivity extends AppCompatActivity {

    @BindView(R.id.btnOK)
    Button btnOK;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    private Product productToShow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        setupUI();
    }

    private void setupUI() {
        ButterKnife.bind(this);

        // pull the Product object as an extra
        if (getIntent() != null
                && getIntent().hasExtra(ProductRecyclerAdapter.KEY_PRODUCT_DESCRIPTION)) {
            productToShow = (Product) getIntent().getSerializableExtra(
                    ProductRecyclerAdapter.KEY_PRODUCT_DESCRIPTION);
            tvDescription.setText(productToShow.getDescription());
        }
    }

    @OnClick(R.id.btnOK)
    public void OKOnClickListener(Button btnOK) {
        // finish activity
        finish();
    }
}
