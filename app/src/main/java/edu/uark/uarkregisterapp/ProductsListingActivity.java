package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class ProductsListingActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products_listing);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

		ActionBar actionBar = this.getSupportActionBar();
		if (actionBar != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		this.products = new ArrayList<>();
		this.productListAdapter = new ProductListAdapter(this, this.products);

		this.getProductsListView().setAdapter(this.productListAdapter);
		this.getProductsListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);

				intent.putExtra(
					getString(R.string.intent_extra_product),
					new ProductTransition((Product) getProductsListView().getItemAtPosition(position))
				);

				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		(new RetrieveProductsTask()).execute();
	}

	private ListView getProductsListView() {
		return (ListView) this.findViewById(R.id.list_view_products);
	}

	private class RetrieveProductsTask extends AsyncTask<Void, Void, ApiResponse<List<Product>>> {
		@Override
		protected void onPreExecute() {
			this.loadingProductsAlert.show();
		}

		@Override
		protected ApiResponse<List<Product>> doInBackground(Void... params) {
			ApiResponse<List<Product>> apiResponse = (new ProductService()).getProducts();

			if (apiResponse.isValidResponse()) {
				products.clear();
				products.addAll(apiResponse.getData());
			}

			return apiResponse;
		}

		@Override
		protected void onPostExecute(ApiResponse<List<Product>> apiResponse) {
			if (apiResponse.isValidResponse()) {
				productListAdapter.notifyDataSetChanged();
			}

			this.loadingProductsAlert.dismiss();

			if (!apiResponse.isValidResponse()) {
				new AlertDialog.Builder(ProductsListingActivity.this).
					setMessage(R.string.alert_dialog_products_load_failure).
					setPositiveButton(
						R.string.button_dismiss,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}
					).
					create().
					show();
			}
		}

		private AlertDialog loadingProductsAlert;

		private RetrieveProductsTask() {
			this.loadingProductsAlert = new AlertDialog.Builder(ProductsListingActivity.this).
				setMessage(R.string.alert_dialog_products_loading).
				create();
		}
	}

	private List<Product> products;
	private ProductListAdapter productListAdapter;
}
