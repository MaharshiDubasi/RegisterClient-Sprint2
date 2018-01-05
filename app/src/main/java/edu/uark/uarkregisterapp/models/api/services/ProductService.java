package edu.uark.uarkregisterapp.models.api.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.enums.ApiObject;
import edu.uark.uarkregisterapp.models.api.enums.ProductApiMethod;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public class ProductService extends BaseRemoteService {
	public ApiResponse<Product> getProduct(UUID productId) {
		return this.readProductDetailsFromResponse(
			this.<Product>performGetRequest(
				this.buildPath(productId)
			)
		);
	}

	public ApiResponse<Product> getProductByLookupCode(String productLookupCode) {
		return this.readProductDetailsFromResponse(
			this.<Product>performGetRequest(
				this.buildPath(
					(new PathElementInterface[] { ProductApiMethod.BY_LOOKUP_CODE })
					, productLookupCode
				)
			)
		);
	}

	public ApiResponse<List<Product>> getProducts() {
		ApiResponse<List<Product>> apiResponse = this.performGetRequest(
			this.buildPath()
		);

		JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
		if (rawJsonArray != null) {
			ArrayList<Product> products = new ArrayList<>(rawJsonArray.length());
			for (int i = 0; i < rawJsonArray.length(); i++) {
				try {
					products.add((new Product()).loadFromJson(rawJsonArray.getJSONObject(i)));
				} catch (JSONException e) {
					Log.d("GET PRODUCTS", e.getMessage());
				}
			}

			apiResponse.setData(products);
		} else {
			apiResponse.setData(new ArrayList<Product>(0));
		}

		return apiResponse;
	}

	public ApiResponse<Product> updateProduct(Product product) {
		return this.readProductDetailsFromResponse(
			this.<Product>performPutRequest(
				this.buildPath(product.getId())
				, product.convertToJson()
			)
		);
	}

	public ApiResponse<Product> createProduct(Product product) {
		return this.readProductDetailsFromResponse(
			this.<Product>performPostRequest(
				this.buildPath()
				, product.convertToJson()
			)
		);
	}

	public ApiResponse<String> deleteProduct(UUID productId) {
		return this.<String>performDeleteRequest(
			this.buildPath(productId)
		);
	}

	private ApiResponse<Product> readProductDetailsFromResponse(ApiResponse<Product> apiResponse) {
		JSONObject rawJsonObject = this.rawResponseToJSONObject(
			apiResponse.getRawResponse()
		);

		if (rawJsonObject != null) {
			apiResponse.setData(
				(new Product()).loadFromJson(rawJsonObject)
			);
		}

		return apiResponse;
	}

	public ProductService() { super(ApiObject.PRODUCT); }
}
