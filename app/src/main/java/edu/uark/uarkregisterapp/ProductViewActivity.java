package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class ProductViewActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_view);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

		ActionBar actionBar = this.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		this.productTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_product));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:  // Respond to the action bar's Up/Home button
				this.finish();

				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!this.productTransition.getId().equals(new UUID(0, 0))) {
			this.getDeleteImageButton().setVisibility(View.VISIBLE);
		} else {
			this.getDeleteImageButton().setVisibility(View.INVISIBLE);
		}
		this.getProductLookupCodeEditText().setText(this.productTransition.getLookupCode());
		this.getProductCountEditText().setText(String.format(Locale.getDefault(), "%d", this.productTransition.getCount()));
		this.getProductCreatedOnEditText().setText(
			(new SimpleDateFormat("MM/dd/yyyy", Locale.US)).format(this.productTransition.getCreatedOn())
		);
	}

	public void saveButtonOnClick(View view) {
		if (!this.validateInput()) {
			return;
		}

		(new SaveProductTask()).execute();
	}

	public void deleteButtonOnClick(View view) {
		new AlertDialog.Builder(this).
			setMessage(R.string.alert_dialog_product_delete_confirm).
			setPositiveButton(
				R.string.button_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						(new DeleteProductTask()).execute();
					}
				}
			).
			setNegativeButton(
				R.string.button_cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				}
			).
			create().
			show();
	}

	private EditText getProductLookupCodeEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_lookup_code);
	}

	private EditText getProductCountEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_count);
	}

	private EditText getProductCreatedOnEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_created_on);
	}

	private ImageButton getDeleteImageButton() {
		return (ImageButton) this.findViewById(R.id.button_activity_edit_delete);
	}

	private boolean validateInput() {
		boolean inputIsValid = true;
		String validationMessage = StringUtils.EMPTY;

		if (StringUtils.isBlank(this.getProductLookupCodeEditText().getText().toString())) {
			validationMessage = this.getString(R.string.validation_product_lookup_code);
			inputIsValid = false;
		}

		if (!inputIsValid && StringUtils.isBlank(this.getProductCountEditText().getText().toString())) {
			validationMessage = this.getString(R.string.validation_product_count);
			inputIsValid = false;
		}

		try {
			if (Integer.parseInt(this.getProductCountEditText().getText().toString()) < 0) {
				validationMessage = this.getString(R.string.validation_product_count);
				inputIsValid = false;
			}
		} catch (NumberFormatException nfe) {
			validationMessage = this.getString(R.string.validation_product_count);
			inputIsValid = false;
		}

		if (!inputIsValid) {
			new AlertDialog.Builder(this).
				setMessage(validationMessage).
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

		return inputIsValid;
	}

	private class SaveProductTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			this.savingProductAlert.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Product product = (new Product()).
				setId(productTransition.getId()).
				setLookupCode(getProductLookupCodeEditText().getText().toString()).
				setCount(Integer.parseInt(getProductCountEditText().getText().toString()));

			ApiResponse<Product> apiResponse = (
				(product.getId().equals(new UUID(0, 0)))
					? (new ProductService()).createProduct(product)
					: (new ProductService()).updateProduct(product)
			);

			if (apiResponse.isValidResponse()) {
				productTransition.setCount(apiResponse.getData().getCount());
				productTransition.setLookupCode(apiResponse.getData().getLookupCode());
			}

			return apiResponse.isValidResponse();
		}

		@Override
		protected void onPostExecute(Boolean successfulSave) {
			String message;

			savingProductAlert.dismiss();

			if (successfulSave) {
				message = getString(R.string.alert_dialog_product_save_success);
			} else {
				message = getString(R.string.alert_dialog_product_save_failure);
			}

			new AlertDialog.Builder(ProductViewActivity.this).
				setMessage(message).
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

		private AlertDialog savingProductAlert;

		private SaveProductTask() {
			this.savingProductAlert = new AlertDialog.Builder(ProductViewActivity.this).
				setMessage(R.string.alert_dialog_product_save).
				create();
		}
	}

	private class DeleteProductTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			this.deletingProductAlert.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			return (new ProductService())
				.deleteProduct(productTransition.getId())
				.isValidResponse();
		}

		@Override
		protected void onPostExecute(final Boolean successfulSave) {
			String message;

			deletingProductAlert.dismiss();

			if (successfulSave) {
				message = getString(R.string.alert_dialog_product_delete_success);
			} else {
				message = getString(R.string.alert_dialog_product_delete_failure);
			}

			new AlertDialog.Builder(ProductViewActivity.this).
				setMessage(message).
				setPositiveButton(
					R.string.button_dismiss,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							if (successfulSave) {
								finish();
							}
						}
					}
				).
				create().
				show();
		}

		private AlertDialog deletingProductAlert;

		private DeleteProductTask() {
			this.deletingProductAlert = new AlertDialog.Builder(ProductViewActivity.this).
				setMessage(R.string.alert_dialog_product_delete).
				create();
		}
	}

	private ProductTransition productTransition;
}
